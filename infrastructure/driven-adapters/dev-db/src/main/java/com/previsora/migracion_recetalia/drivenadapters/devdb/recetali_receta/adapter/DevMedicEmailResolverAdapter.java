package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaMedicRepository;
import com.previsora.migracion_recetalia.usecase.gateway.MedicEmailResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Resolves a medic ID in the dev database by looking up the email.
 */
@Component
public class DevMedicEmailResolverAdapter implements MedicEmailResolver {

    private static final Logger log = LoggerFactory.getLogger(DevMedicEmailResolverAdapter.class);

    private final DevRecetaMedicRepository repository;

    public DevMedicEmailResolverAdapter(DevRecetaMedicRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> findMedicIdByEmail(String email) {
        log.debug("Looking up dev medic by email: {}", email);
        return repository.findIdByEmail(email);
    }
}
