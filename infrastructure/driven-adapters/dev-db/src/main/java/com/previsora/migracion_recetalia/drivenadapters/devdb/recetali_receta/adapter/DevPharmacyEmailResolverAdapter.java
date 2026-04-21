package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaPharmacyRepository;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyEmailResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Resolves a pharmacy ID in the dev database by looking up the email.
 * <p>
 * Used during pharmacy_dispenser migration to find the dev pharmacyId
 * that corresponds to a pharmacy identified by its email (business key).
 */
@Component
public class DevPharmacyEmailResolverAdapter implements PharmacyEmailResolver {

    private static final Logger log = LoggerFactory.getLogger(DevPharmacyEmailResolverAdapter.class);

    private final DevRecetaPharmacyRepository repository;

    public DevPharmacyEmailResolverAdapter(DevRecetaPharmacyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> findPharmacyIdByEmail(String email) {
        log.debug("Looking up dev pharmacy by email: {}", email);
        return repository.findIdByEmail(email);
    }
}
