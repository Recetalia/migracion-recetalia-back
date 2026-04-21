package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for resolving a patient ID in the dev database by document.
 * <p>
 * Used during prescription migration to remap prod patientId → dev patientId
 * using the patient document as the business key.
 */
public interface PatientDocumentResolver {

    /**
     * Find the patient ID in the dev database for the given document.
     *
     * @param document the patient document (business key)
     * @return the dev patient ID, or empty if not found
     */
    Mono<String> findPatientIdByDocument(String document);
}
