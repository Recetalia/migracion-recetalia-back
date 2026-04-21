package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevPrescriptionRepository;
import com.previsora.migracion_recetalia.usecase.gateway.PrescriptionIdResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Verifies a prescription exists in dev by its (preserved) primary key and returns the dev ID.
 */
@Component
public class DevPrescriptionIdResolverAdapter implements PrescriptionIdResolver {

    private static final Logger log = LoggerFactory.getLogger(DevPrescriptionIdResolverAdapter.class);

    private final DevPrescriptionRepository repository;

    public DevPrescriptionIdResolverAdapter(DevPrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> resolveDevPrescriptionId(String prodPrescriptionId) {
        log.debug("Verifying dev prescription exists for id: {}", prodPrescriptionId);
        return repository.countById(prodPrescriptionId)
                .filter(count -> count > 0)
                .map(count -> prodPrescriptionId);
    }
}
