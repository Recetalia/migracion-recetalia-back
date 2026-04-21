package com.previsora.migracion_recetalia.model.dto;

/**
 * HTTP response envelope for migration endpoints.
 *
 * @param success whether the migration completed without fatal errors
 * @param message human-readable result message
 * @param summary detailed migration summary (null on startup errors)
 */
public record MigrationResponse(
        boolean success,
        String message,
        MigrationSummary summary
) {

    public static MigrationResponse ok(String message, MigrationSummary summary) {
        return new MigrationResponse(true, message, summary);
    }

    public static MigrationResponse error(String message) {
        return new MigrationResponse(false, message, null);
    }
}
