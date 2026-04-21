package com.previsora.migracion_recetalia.model.dto;

import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;

/**
 * Inbound request DTO for triggering a migration.
 *
 * @param dryRun          when true, the migration reads and validates but does not write
 * @param batchSize       page size for paginated reads from prod
 * @param duplicatePolicy how to handle records that already exist in the target
 * @param truncateFirst   when true, truncate the target tables before migrating (batch endpoints only)
 * @param sourceRecordId  optional source-side identifier used to scope a migration to a single record
 * @param insertDelayMs   throttle between records (milliseconds); 0 disables throttling
 */
public record MigrationRequest(
        boolean dryRun,
        int batchSize,
        DuplicatePolicy duplicatePolicy,
        boolean truncateFirst,
        String sourceRecordId,
        long insertDelayMs
) {

    /** Default request: dryRun=true, batchSize=500, SKIP_EXISTING, truncateFirst=false, insertDelayMs=500. */
    public static MigrationRequest defaults() {
        return new MigrationRequest(true, 500, DuplicatePolicy.SKIP_EXISTING, false, null, 500L);
    }

    public MigrationRequest withSourceRecordId(String sourceRecordId) {
        return new MigrationRequest(dryRun, batchSize, duplicatePolicy, truncateFirst, sourceRecordId, insertDelayMs);
    }
}
