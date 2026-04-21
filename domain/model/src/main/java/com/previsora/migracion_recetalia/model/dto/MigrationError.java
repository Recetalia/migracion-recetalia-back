package com.previsora.migracion_recetalia.model.dto;

/**
 * Details about a single record that failed during migration.
 *
 * @param recordId      business identifier of the failed record (e.g. email, PK)
 * @param reason        human-readable error description
 * @param exceptionType class name of the original exception, if any
 */
public record MigrationError(
        String recordId,
        String reason,
        String exceptionType
) {
}
