package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevPharmacyDispenserRepository;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyDispenserIdResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Verifies a pharmacy_dispenser exists in dev with its (preserved) primary key AND matches
 * the mapped dev pharmacyId, returning the dev dispenser ID.
 */
@Component
public class DevPharmacyDispenserIdResolverAdapter implements PharmacyDispenserIdResolver {

    private static final Logger log = LoggerFactory.getLogger(DevPharmacyDispenserIdResolverAdapter.class);

    private final DevPharmacyDispenserRepository repository;

    public DevPharmacyDispenserIdResolverAdapter(DevPharmacyDispenserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> resolveDevDispenserId(String prodDispenserId, String devPharmacyId) {
        log.debug("Verifying dev pharmacy_dispenser exists for id={} with pharmacyId={}",
                prodDispenserId, devPharmacyId);
        return repository.findIdByIdAndPharmacyId(prodDispenserId, devPharmacyId);
    }
}
