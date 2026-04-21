package com.previsora.migracion_recetalia.reactiveweb.exception;

import com.previsora.migracion_recetalia.model.dto.MigrationResponse;
import com.previsora.migracion_recetalia.model.error.MigrationException;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * Global exception handler for all migration REST endpoints.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SourceReadException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Mono<MigrationResponse> handleSourceRead(SourceReadException ex) {
        log.error("Source read error: {}", ex.getMessage(), ex);
        return Mono.just(MigrationResponse.error("Source database error: " + ex.getMessage()));
    }

    @ExceptionHandler(TargetWriteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<MigrationResponse> handleTargetWrite(TargetWriteException ex) {
        log.error("Target write error: {}", ex.getMessage(), ex);
        return Mono.just(MigrationResponse.error("Target database error: " + ex.getMessage()));
    }

    @ExceptionHandler(MigrationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<MigrationResponse> handleMigration(MigrationException ex) {
        log.error("Migration error: {}", ex.getMessage(), ex);
        return Mono.just(MigrationResponse.error("Migration error: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<MigrationResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return Mono.just(MigrationResponse.error("Unexpected error: " + ex.getMessage()));
    }
}
