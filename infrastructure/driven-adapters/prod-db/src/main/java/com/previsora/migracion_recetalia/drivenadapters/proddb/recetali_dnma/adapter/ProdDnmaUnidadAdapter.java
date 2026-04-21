package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaUnidadMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaUnidadRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaUnidad;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaUnidadReader")
public class ProdDnmaUnidadAdapter implements SourceReader<DnmaUnidad> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaUnidadAdapter.class);

    private final ProdDnmaUnidadRepository repository;
    private final ProdDnmaUnidadMapper mapper;

    public ProdDnmaUnidadAdapter(ProdDnmaUnidadRepository repository, ProdDnmaUnidadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaUnidad> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.unidad with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.unidad", ex));
    }
}
