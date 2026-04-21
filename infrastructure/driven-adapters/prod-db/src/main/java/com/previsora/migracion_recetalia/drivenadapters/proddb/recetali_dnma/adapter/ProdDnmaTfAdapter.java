package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaTfMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaTfRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaTf;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaTfReader")
public class ProdDnmaTfAdapter implements SourceReader<DnmaTf> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaTfAdapter.class);

    private final ProdDnmaTfRepository repository;
    private final ProdDnmaTfMapper mapper;

    public ProdDnmaTfAdapter(ProdDnmaTfRepository repository, ProdDnmaTfMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaTf> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.tf with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.tf", ex));
    }
}
