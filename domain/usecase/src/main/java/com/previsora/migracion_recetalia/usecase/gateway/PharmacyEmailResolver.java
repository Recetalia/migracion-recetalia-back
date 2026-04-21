package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for resolving a pharmacy ID in the dev database by email.
 * <p>
 * Used during pharmacy_dispenser migration to remap prod pharmacyId → dev pharmacyId
 * using the pharmacy email as the business key.
 */
public interface PharmacyEmailResolver {

    /**
     * Find the pharmacy ID in the dev database for the given email.
     *
     * @param email the pharmacy email (unique business key)
     * @return the dev pharmacy ID, or empty if not found
     */
    Mono<String> findPharmacyIdByEmail(String email);
}
