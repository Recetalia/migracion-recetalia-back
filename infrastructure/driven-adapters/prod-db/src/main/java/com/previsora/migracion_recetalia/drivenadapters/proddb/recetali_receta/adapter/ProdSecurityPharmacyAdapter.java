package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaPharmacyMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaPharmacyRepository;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter that reads from {@code prod.recetali_receta.pharmacy}
 * and derives {@link SecurityUser} domain objects.
 * <p>
 * There is NO securitydb in prod. This adapter reads RecetaPharmacy records
 * and transforms them to SecurityUser using {@link SecurityUser#fromRecetaPharmacy}.
 */
@Component("prodSecurityPharmacyReader")
public class ProdSecurityPharmacyAdapter implements SourceReader<SecurityUser> {

    private static final Logger log = LoggerFactory.getLogger(ProdSecurityPharmacyAdapter.class);

    private final ProdRecetaPharmacyRepository repository;
    private final ProdRecetaPharmacyMapper mapper;

    public ProdSecurityPharmacyAdapter(ProdRecetaPharmacyRepository repository,
                                        ProdRecetaPharmacyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<SecurityUser> readAll(int batchSize) {
        log.info("Reading all pharmacies from prod.recetali_receta.pharmacy (for SecurityUser derivation) with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(entity -> SecurityUser.fromRecetaPharmacy(mapper.toDomain(entity)))
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.pharmacy for security derivation", ex));
    }

    @Override
    public Flux<SecurityUser> readById(String sourceRecordId, int batchSize) {
        log.info("Reading pharmacy {} from prod.recetali_receta.pharmacy for SecurityUser derivation", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(entity -> SecurityUser.fromRecetaPharmacy(mapper.toDomain(entity)))
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read pharmacy " + sourceRecordId + " from prod.recetali_receta.pharmacy for security derivation", ex));
    }
}
