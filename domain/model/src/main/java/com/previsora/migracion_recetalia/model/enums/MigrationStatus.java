package com.previsora.migracion_recetalia.model.enums;

/**
 * Lifecycle status of a migration execution.
 */
public enum MigrationStatus {
    RUNNING,
    COMPLETED,
    COMPLETED_WITH_ERRORS,
    FAILED,
    DRY_RUN_COMPLETED
}
