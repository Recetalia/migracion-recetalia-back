package com.previsora.migracion_recetalia.model.error;

/**
 * Base unchecked exception for all migration-related errors.
 */
public class MigrationException extends RuntimeException {

    public MigrationException(String message) {
        super(message);
    }

    public MigrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
