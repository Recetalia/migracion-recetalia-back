package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdLogMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdLogRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLog;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodLogReader")
public class ProdLogAdapter implements SourceReader<RecetaLog> {

    private static final Logger log = LoggerFactory.getLogger(ProdLogAdapter.class);

    private final ProdLogRepository repository;
    private final ProdLogMapper mapper;

    public ProdLogAdapter(ProdLogRepository repository,
                           ProdLogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaLog> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.log with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.log", ex));
    }
}
