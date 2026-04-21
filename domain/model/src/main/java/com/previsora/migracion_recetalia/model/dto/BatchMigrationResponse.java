package com.previsora.migracion_recetalia.model.dto;

import java.util.List;

/**
 * HTTP response envelope for batch migration endpoints.
 *
 * @param success   whether all migrations completed without fatal errors
 * @param message   human-readable result message
 * @param summaries per-table migration summaries
 */
public record BatchMigrationResponse(
        boolean success,
        String message,
        List<MigrationSummary> summaries
) {

    public static BatchMigrationResponse ok(String message, List<MigrationSummary> summaries) {
        return new BatchMigrationResponse(true, message, summaries);
    }

    public static BatchMigrationResponse error(String message) {
        return new BatchMigrationResponse(false, message, List.of());
    }
}
