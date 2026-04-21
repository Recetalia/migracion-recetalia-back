package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaPatientRepository;
import com.previsora.migracion_recetalia.usecase.gateway.PatientDocumentResolver;
import com.previsora.migracion_recetalia.utility.DocumentNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Resolves a patient ID in the dev database by looking up the document.
 */
@Component
public class DevPatientDocumentResolverAdapter implements PatientDocumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DevPatientDocumentResolverAdapter.class);

    private final DevRecetaPatientRepository repository;

    public DevPatientDocumentResolverAdapter(DevRecetaPatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> findPatientIdByDocument(String document) {
        String normalized = DocumentNormalizer.normalize(document);
        log.debug("Looking up dev patient by document: {}", normalized);
        return repository.findIdByDocument(normalized);
    }
}
