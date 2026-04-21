package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaSustanciaMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaSustanciaRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaSustancia;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaSustanciaReader")
public class ProdDnmaSustanciaAdapter implements SourceReader<DnmaSustancia> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaSustanciaAdapter.class);

    private final ProdDnmaSustanciaRepository repository;
    private final ProdDnmaSustanciaMapper mapper;

    public ProdDnmaSustanciaAdapter(ProdDnmaSustanciaRepository repository, ProdDnmaSustanciaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaSustancia> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.sustancia with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.sustancia", ex));
    }
}
