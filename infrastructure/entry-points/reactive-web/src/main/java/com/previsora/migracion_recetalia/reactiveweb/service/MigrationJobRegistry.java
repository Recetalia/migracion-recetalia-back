package com.previsora.migracion_recetalia.reactiveweb.service;

import com.previsora.migracion_recetalia.model.dto.JobState;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.JobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * In-memory registry of migration jobs. Each job runs detached on
 * {@link Schedulers#boundedElastic()} so the HTTP response can return immediately.
 * <p>
 * Job state is lost on application restart; in-progress migrations are orphaned.
 */
@Component
public class MigrationJobRegistry {

    private static final Logger log = LoggerFactory.getLogger(MigrationJobRegistry.class);

    private final Map<String, JobEntry> jobs = new ConcurrentHashMap<>();

    /** Start a job whose work produces a single {@link MigrationSummary}. */
    public String submitSingle(String migrationName, Supplier<Mono<MigrationSummary>> work) {
        return submit(migrationName, () -> work.get().map(List::of));
    }

    /** Start a job whose work produces a list of {@link MigrationSummary}. */
    public String submitBatch(String migrationName, Supplier<Mono<List<MigrationSummary>>> work) {
        return submit(migrationName, work);
    }

    private String submit(String migrationName, Supplier<Mono<List<MigrationSummary>>> work) {
        String jobId = UUID.randomUUID().toString();
        JobEntry entry = new JobEntry(jobId, migrationName, Instant.now());
        jobs.put(jobId, entry);

        log.info("[job {}] Submitted migration '{}'", jobId, migrationName);

        work.get()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(
                        summaries -> entry.complete(summaries),
                        ex -> {
                            log.error("[job {}] Migration '{}' failed: {}", jobId, migrationName, ex.getMessage(), ex);
                            entry.fail(ex);
                        });
        return jobId;
    }

    public JobState get(String jobId) {
        JobEntry entry = jobs.get(jobId);
        return entry == null ? null : entry.snapshot();
    }

    public List<JobState> list() {
        return jobs.values().stream()
                .sorted((a, b) -> b.startedAt.compareTo(a.startedAt))
                .map(JobEntry::snapshot)
                .toList();
    }

    private static final class JobEntry {
        final String jobId;
        final String migrationName;
        final Instant startedAt;
        volatile JobStatus status = JobStatus.RUNNING;
        volatile Instant finishedAt;
        volatile List<MigrationSummary> summaries = List.of();
        volatile String error;

        JobEntry(String jobId, String migrationName, Instant startedAt) {
            this.jobId = jobId;
            this.migrationName = migrationName;
            this.startedAt = startedAt;
        }

        void complete(List<MigrationSummary> result) {
            this.summaries = result == null ? List.of() : result;
            this.finishedAt = Instant.now();
            this.status = JobStatus.COMPLETED;
        }

        void fail(Throwable ex) {
            this.error = ex.getClass().getSimpleName() + ": " + ex.getMessage();
            this.finishedAt = Instant.now();
            this.status = JobStatus.FAILED;
        }

        JobState snapshot() {
            return new JobState(jobId, migrationName, status, startedAt, finishedAt, summaries, error);
        }
    }
}
