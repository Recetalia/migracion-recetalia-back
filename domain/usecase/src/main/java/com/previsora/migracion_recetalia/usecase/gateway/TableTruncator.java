package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Output port for truncating target tables before a batch migration.
 * <p>
 * Implementations must handle FK constraints (e.g. by disabling checks).
 */
public interface TableTruncator {

    /**
     * Truncate the given tables in order, disabling FK checks as needed.
     *
     * @param fullyQualifiedTableNames list of schema-qualified table names
     * @return completes when all tables are truncated
     */
    Mono<Void> truncateAll(List<String> fullyQualifiedTableNames);
}
