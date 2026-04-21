package com.previsora.migracion_recetalia.model.dto;

import com.previsora.migracion_recetalia.model.enums.MigrationStatus;

import java.time.Duration;
import java.util.List;

/**
 * Complete result of a migration execution.
 *
 * @param migrationName name of the migration (e.g. "receta-user")
 * @param status        final status
 * @param totalRead     records read from source
 * @param totalInserted records successfully inserted into target
 * @param totalSkipped  records skipped (already existed)
 * @param totalFailed   records that failed to insert
 * @param dryRun        whether this was a dry run
 * @param durationMs    elapsed time in milliseconds
 * @param errors        detail list of individual failures (capped at 500)
 */
public record MigrationSummary(
        String migrationName,
        MigrationStatus status,
        long totalRead,
        long totalInserted,
        long totalSkipped,
        long totalFailed,
        boolean dryRun,
        long durationMs,
        List<MigrationError> errors
) {
}
