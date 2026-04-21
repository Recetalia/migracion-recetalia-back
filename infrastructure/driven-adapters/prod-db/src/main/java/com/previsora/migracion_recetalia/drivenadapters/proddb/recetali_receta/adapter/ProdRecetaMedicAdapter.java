package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaMedicMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaMedicRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter for {@code prod.recetali_receta.medic}.
 * <p>
 * Reads all medics from prod using paginated queries and maps to domain model.
 */
@Component("prodRecetaMedicReader")
public class ProdRecetaMedicAdapter implements SourceReader<RecetaMedic> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaMedicAdapter.class);

    private final ProdRecetaMedicRepository repository;
    private final ProdRecetaMedicMapper mapper;

    public ProdRecetaMedicAdapter(ProdRecetaMedicRepository repository,
                                   ProdRecetaMedicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaMedic> readAll(int batchSize) {
        log.info("Reading all medics from prod.recetali_receta.medic with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.medic", ex));
    }

    @Override
    public Flux<RecetaMedic> readById(String sourceRecordId, int batchSize) {
        log.info("Reading medic {} from prod.recetali_receta.medic", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(mapper::toDomain)
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read medic " + sourceRecordId + " from prod.recetali_receta.medic", ex));
    }
}
