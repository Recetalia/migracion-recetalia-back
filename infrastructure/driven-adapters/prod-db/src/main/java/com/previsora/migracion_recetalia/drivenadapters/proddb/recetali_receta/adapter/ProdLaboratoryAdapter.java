package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdLaboratoryMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdLaboratoryRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLaboratory;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodLaboratoryReader")
public class ProdLaboratoryAdapter implements SourceReader<RecetaLaboratory> {

    private static final Logger log = LoggerFactory.getLogger(ProdLaboratoryAdapter.class);

    private final ProdLaboratoryRepository repository;
    private final ProdLaboratoryMapper mapper;

    public ProdLaboratoryAdapter(ProdLaboratoryRepository repository,
                                  ProdLaboratoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaLaboratory> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.laboratory with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.laboratory", ex));
    }
}
