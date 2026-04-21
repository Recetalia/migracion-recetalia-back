package com.previsora.migracion_recetalia.model.generic;

import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.MigrationStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe mutable accumulator for tracking migration progress.
 * <p>
 * Used during the reactive migration pipeline to accumulate counts and errors.
 * The error list is capped at {@link #MAX_ERRORS} to prevent OOM on large failure runs.
 */
public class MigrationAccumulator {

    private static final int MAX_ERRORS = 500;

    private final String migrationName;
    private final boolean dryRun;
    private final long startTimeMs;

    private final AtomicLong totalRead = new AtomicLong(0);
    private final AtomicLong totalInserted = new AtomicLong(0);
    private final AtomicLong totalSkipped = new AtomicLong(0);
    private final AtomicLong totalFailed = new AtomicLong(0);
    private final List<MigrationError> errors = Collections.synchronizedList(new ArrayList<>());

    public MigrationAccumulator(String migrationName, boolean dryRun) {
        this.migrationName = migrationName;
        this.dryRun = dryRun;
        this.startTimeMs = System.currentTimeMillis();
    }

    public void incrementRead() {
        totalRead.incrementAndGet();
    }

    public void incrementInserted() {
        totalInserted.incrementAndGet();
    }

    public void incrementSkipped() {
        totalSkipped.incrementAndGet();
    }

    public void incrementFailed() {
        totalFailed.incrementAndGet();
    }

    /**
     * Record a failure. Only the first {@value #MAX_ERRORS} are kept;
     * the failed counter is always accurate.
     */
    public void addError(MigrationError error) {
        incrementFailed();
        if (errors.size() < MAX_ERRORS) {
            errors.add(error);
        }
    }

    /**
     * Build the final immutable summary snapshot.
     */
    public MigrationSummary toSummary() {
        long durationMs = System.currentTimeMillis() - startTimeMs;
        MigrationStatus status = resolveStatus();
        return new MigrationSummary(
                migrationName,
                status,
                totalRead.get(),
                totalInserted.get(),
                totalSkipped.get(),
                totalFailed.get(),
                dryRun,
                durationMs,
                List.copyOf(errors)
        );
    }

    private MigrationStatus resolveStatus() {
        if (dryRun) {
            return MigrationStatus.DRY_RUN_COMPLETED;
        }
        if (totalFailed.get() > 0 && totalInserted.get() > 0) {
            return MigrationStatus.COMPLETED_WITH_ERRORS;
        }
        if (totalFailed.get() > 0 && totalInserted.get() == 0) {
            return MigrationStatus.FAILED;
        }
        return MigrationStatus.COMPLETED;
    }
}
