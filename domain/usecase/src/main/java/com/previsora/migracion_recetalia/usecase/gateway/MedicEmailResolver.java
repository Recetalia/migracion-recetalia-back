package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for resolving a medic ID in the dev database by email.
 * <p>
 * Used during prescription migration to remap prod medicId → dev medicId
 * using the medic email as the business key.
 */
public interface MedicEmailResolver {

    /**
     * Find the medic ID in the dev database for the given email.
     *
     * @param email the medic email (unique business key)
     * @return the dev medic ID, or empty if not found
     */
    Mono<String> findMedicIdByEmail(String email);
}
