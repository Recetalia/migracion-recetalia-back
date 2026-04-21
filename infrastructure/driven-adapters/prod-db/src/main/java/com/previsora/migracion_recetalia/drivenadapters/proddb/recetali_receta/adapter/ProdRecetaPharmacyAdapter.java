package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaPharmacyMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaPharmacyRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter for {@code prod.recetali_receta.pharmacy}.
 * <p>
 * Reads all pharmacies from prod using paginated queries and maps to domain model.
 */
@Component("prodRecetaPharmacyReader")
public class ProdRecetaPharmacyAdapter implements SourceReader<RecetaPharmacy> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaPharmacyAdapter.class);

    private final ProdRecetaPharmacyRepository repository;
    private final ProdRecetaPharmacyMapper mapper;

    public ProdRecetaPharmacyAdapter(ProdRecetaPharmacyRepository repository,
                                      ProdRecetaPharmacyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaPharmacy> readAll(int batchSize) {
        log.info("Reading all pharmacies from prod.recetali_receta.pharmacy with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.pharmacy", ex));
    }

    @Override
    public Flux<RecetaPharmacy> readById(String sourceRecordId, int batchSize) {
        log.info("Reading pharmacy {} from prod.recetali_receta.pharmacy", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(mapper::toDomain)
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read pharmacy " + sourceRecordId + " from prod.recetali_receta.pharmacy", ex));
    }
}
