package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for writing records to the target (dev) database.
 *
 * @param <T> the domain model type being written
 */
public interface TargetWriter<T> {

    /**
     * Check if a record with the same business key already exists in the target.
     *
     * @param record the domain model to check
     * @return true if the record already exists in target
     */
    Mono<Boolean> exists(T record);

    /**
     * Insert a new record into the target database.
     *
     * @param record the domain model to insert
     * @return completes when the insert is done
     */
    Mono<Void> insert(T record);
}
