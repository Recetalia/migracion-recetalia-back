package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for verifying that a prescription migrated to dev exists, returning its dev ID.
 * <p>
 * Since the prescription migration preserves the primary key, the returned ID equals the
 * input when the record exists in dev; empty otherwise.
 */
public interface PrescriptionIdResolver {

    /**
     * Verify a prescription exists in dev and return its dev ID.
     *
     * @param prodPrescriptionId the prod prescription ID (equal to dev ID when migrated)
     * @return the dev prescription ID, or empty if the prescription is not present in dev
     */
    Mono<String> resolveDevPrescriptionId(String prodPrescriptionId);
}
