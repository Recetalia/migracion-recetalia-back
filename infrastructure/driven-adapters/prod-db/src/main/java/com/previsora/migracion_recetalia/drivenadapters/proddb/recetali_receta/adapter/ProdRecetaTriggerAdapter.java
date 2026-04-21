package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaTriggerMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaTriggerRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaTrigger;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaTriggerReader")
public class ProdRecetaTriggerAdapter implements SourceReader<RecetaTrigger> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaTriggerAdapter.class);

    private final ProdRecetaTriggerRepository repository;
    private final ProdRecetaTriggerMapper mapper;

    public ProdRecetaTriggerAdapter(ProdRecetaTriggerRepository repository, ProdRecetaTriggerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaTrigger> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.trigger with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.trigger", ex));
    }
}
