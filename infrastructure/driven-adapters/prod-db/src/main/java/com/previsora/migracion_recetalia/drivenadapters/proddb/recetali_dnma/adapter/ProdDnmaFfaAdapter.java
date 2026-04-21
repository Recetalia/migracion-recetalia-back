package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaFfaMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaFfaRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaFfa;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaFfaReader")
public class ProdDnmaFfaAdapter implements SourceReader<DnmaFfa> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaFfaAdapter.class);

    private final ProdDnmaFfaRepository repository;
    private final ProdDnmaFfaMapper mapper;

    public ProdDnmaFfaAdapter(ProdDnmaFfaRepository repository, ProdDnmaFfaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaFfa> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.ffa with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.ffa", ex));
    }
}
