package com.previsora.migracion_recetalia.model.error;

/**
 * Thrown when reading from the production (source) database fails.
 */
public class SourceReadException extends MigrationException {

    public SourceReadException(String message) {
        super(message);
    }

    public SourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
