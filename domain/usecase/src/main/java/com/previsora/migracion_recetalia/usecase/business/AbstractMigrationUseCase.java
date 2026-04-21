package com.previsora.migracion_recetalia.usecase.business;

import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * Abstract orchestration skeleton for table-to-table migration.
 * <p>
 * Concrete use cases extend this and provide:
 * <ul>
 *   <li>{@link #sourceReader()} — the prod SourceReader adapter</li>
 *   <li>{@link #targetWriter()} — the dev TargetWriter adapter</li>
 *   <li>{@link #recordId(Object)} — string identifier for error reporting</li>
 * </ul>
 *
 * The orchestration logic (batch read, duplicate check, dry-run, error accumulation)
 * is fully handled here and should NOT be duplicated in subclasses.
 *
 * @param <S> the domain model type (source and/or target)
 */
public abstract class AbstractMigrationUseCase<S> implements MigrationOrchestrator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Return the source reader (prod adapter). */
    protected abstract SourceReader<S> sourceReader();

    /** Return the target writer (dev adapter). */
    protected abstract TargetWriter<S> targetWriter();

    /** Extract a human-readable record identifier for error reporting. */
    protected abstract String recordId(S record);

    @Override
    public Mono<MigrationSummary> execute(MigrationRequest request) {
        MigrationAccumulator acc = new MigrationAccumulator(migrationName(), request.dryRun());

        log.info("[{}] Starting migration — dryRun={}, batchSize={}, policy={}, insertDelayMs={}",
                migrationName(), request.dryRun(), request.batchSize(), request.duplicatePolicy(),
                request.insertDelayMs());

        reactor.core.publisher.Flux<S> source = readFromSource(request)
                .doOnNext(record -> acc.incrementRead());
        if (request.insertDelayMs() > 0) {
            source = source.delayElements(Duration.ofMillis(request.insertDelayMs()));
        }

        return source
                .concatMap(record -> processRecord(record, request, acc))
                .then(Mono.fromSupplier(acc::toSummary))
                .doOnSuccess(summary -> log.info(
                        "[{}] Migration finished — read={}, inserted={}, skipped={}, failed={}, dryRun={}, duration={}ms",
                        migrationName(), summary.totalRead(), summary.totalInserted(),
                        summary.totalSkipped(), summary.totalFailed(), summary.dryRun(), summary.durationMs()
                ))
                .onErrorResume(ex -> {
                    log.error("[{}] Migration failed with exception: {}", migrationName(), ex.getMessage(), ex);
                    acc.addError(new MigrationError("GLOBAL", ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.just(acc.toSummary());
                });
    }

    private reactor.core.publisher.Flux<S> readFromSource(MigrationRequest request) {
        if (Objects.nonNull(request.sourceRecordId()) && !request.sourceRecordId().isBlank()) {
            return sourceReader().readById(request.sourceRecordId(), request.batchSize());
        }
        return sourceReader().readAll(request.batchSize());
    }

    private Mono<Void> processRecord(S record, MigrationRequest request, MigrationAccumulator acc) {
        return targetWriter().exists(record)
                .flatMap(exists -> {
                    if (exists) {
                        if (request.duplicatePolicy() == DuplicatePolicy.SKIP_EXISTING) {
                            acc.incrementSkipped();
                            return Mono.<Void>empty();
                        }
                        // OVERWRITE: fall through to insert (which will update)
                    }
                    if (request.dryRun()) {
                        // Dry run: count as "would insert" but do not actually write
                        acc.incrementInserted();
                        return Mono.<Void>empty();
                    }
                    return targetWriter().insert(record)
                            .doOnSuccess(v -> {
                                log.info("[{}] Inserted id={}", migrationName(), recordId(record));
                                acc.incrementInserted();
                            })
                            .onErrorResume(ex -> {
                                log.warn("[{}] Failed to insert record {}: {}",
                                        migrationName(), recordId(record), ex.getMessage());
                                acc.addError(new MigrationError(
                                        recordId(record),
                                        ex.getMessage(),
                                        ex.getClass().getSimpleName()
                                ));
                                return Mono.empty();
                            });
                });
    }
}
