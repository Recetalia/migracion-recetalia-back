package com.previsora.migracion_recetalia.model.error;

/**
 * Thrown when writing to the development (target) database fails.
 */
public class TargetWriteException extends MigrationException {

    public TargetWriteException(String message) {
        super(message);
    }

    public TargetWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
