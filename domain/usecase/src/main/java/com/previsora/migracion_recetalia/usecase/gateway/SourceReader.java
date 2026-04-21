package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Flux;

/**
 * Output port for reading records from the source (prod) database.
 *
 * @param <T> the domain model type being read
 */
public interface SourceReader<T> {

    /**
     * Read all records from the source table in pages of {@code batchSize}.
     *
     * @param batchSize number of records per page
     * @return a reactive stream of domain model objects
     */
    Flux<T> readAll(int batchSize);

    /**
     * Read a single source record identified by {@code sourceRecordId}.
     * Implementations may override this when the source supports targeted reads.
     *
     * @param sourceRecordId source-side primary identifier
     * @param batchSize retained for interface symmetry with paginated implementations
     * @return a reactive stream containing zero or one record
     */
    default Flux<T> readById(String sourceRecordId, int batchSize) {
        return Flux.error(new UnsupportedOperationException(
                "Targeted source reads are not supported by " + getClass().getSimpleName()));
    }
}
