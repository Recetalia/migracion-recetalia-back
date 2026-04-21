package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaLaboratorioMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaLaboratorioRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaLaboratorio;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaLaboratorioReader")
public class ProdDnmaLaboratorioAdapter implements SourceReader<DnmaLaboratorio> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaLaboratorioAdapter.class);

    private final ProdDnmaLaboratorioRepository repository;
    private final ProdDnmaLaboratorioMapper mapper;

    public ProdDnmaLaboratorioAdapter(ProdDnmaLaboratorioRepository repository, ProdDnmaLaboratorioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaLaboratorio> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.laboratorio with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.laboratorio", ex));
    }
}
