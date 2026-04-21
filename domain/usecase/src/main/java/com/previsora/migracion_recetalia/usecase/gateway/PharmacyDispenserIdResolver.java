package com.previsora.migracion_recetalia.usecase.gateway;

import reactor.core.publisher.Mono;

/**
 * Output port for verifying a pharmacy_dispenser exists in dev with the expected dev pharmacy
 * association, returning its dev ID.
 * <p>
 * Since the pharmacy_dispenser migration preserves the primary key, the returned ID equals the
 * input when the record exists in dev AND its pharmacyId equals the already-mapped
 * {@code devPharmacyId}; empty otherwise.
 */
public interface PharmacyDispenserIdResolver {

    /**
     * Verify a pharmacy_dispenser exists in dev and return its dev ID.
     *
     * @param prodDispenserId the prod dispenser ID (equal to dev ID when migrated)
     * @param devPharmacyId   the mapped dev pharmacyId that the dispenser must reference
     * @return the dev dispenser ID, or empty if not found with matching pharmacy association
     */
    Mono<String> resolveDevDispenserId(String prodDispenserId, String devPharmacyId);
}
