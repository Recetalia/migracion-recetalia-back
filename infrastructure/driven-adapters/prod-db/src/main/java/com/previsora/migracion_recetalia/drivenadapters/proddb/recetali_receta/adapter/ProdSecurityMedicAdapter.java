package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaMedicMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaMedicRepository;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter that reads from {@code prod.recetali_receta.medic}
 * and derives {@link SecurityUser} domain objects.
 * <p>
 * There is NO securitydb in prod. This adapter reads RecetaMedic records
 * and transforms them to SecurityUser using {@link SecurityUser#fromRecetaMedic}.
 */
@Component("prodSecurityMedicReader")
public class ProdSecurityMedicAdapter implements SourceReader<SecurityUser> {

    private static final Logger log = LoggerFactory.getLogger(ProdSecurityMedicAdapter.class);

    private final ProdRecetaMedicRepository repository;
    private final ProdRecetaMedicMapper mapper;

    public ProdSecurityMedicAdapter(ProdRecetaMedicRepository repository,
                                     ProdRecetaMedicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<SecurityUser> readAll(int batchSize) {
        log.info("Reading all medics from prod.recetali_receta.medic (for SecurityUser derivation) with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(entity -> SecurityUser.fromRecetaMedic(mapper.toDomain(entity)))
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.medic for security derivation", ex));
    }

    @Override
    public Flux<SecurityUser> readById(String sourceRecordId, int batchSize) {
        log.info("Reading medic {} from prod.recetali_receta.medic for SecurityUser derivation", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(entity -> SecurityUser.fromRecetaMedic(mapper.toDomain(entity)))
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read medic " + sourceRecordId + " from prod.recetali_receta.medic for security derivation", ex));
    }
}
