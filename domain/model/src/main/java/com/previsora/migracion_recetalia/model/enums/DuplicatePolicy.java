package com.previsora.migracion_recetalia.model.enums;

/**
 * Policy to apply when a record already exists in the target database.
 */
public enum DuplicatePolicy {

    /** Skip the record silently — increment the skipped counter. */
    SKIP_EXISTING,

    /** Overwrite the existing record with source data. */
    OVERWRITE
}
