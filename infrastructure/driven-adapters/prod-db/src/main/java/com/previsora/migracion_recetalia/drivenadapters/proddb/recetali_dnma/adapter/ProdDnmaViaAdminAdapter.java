package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper.ProdDnmaViaAdminMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository.ProdDnmaViaAdminRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaViaAdmin;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodDnmaViaAdminReader")
public class ProdDnmaViaAdminAdapter implements SourceReader<DnmaViaAdmin> {

    private static final Logger log = LoggerFactory.getLogger(ProdDnmaViaAdminAdapter.class);

    private final ProdDnmaViaAdminRepository repository;
    private final ProdDnmaViaAdminMapper mapper;

    public ProdDnmaViaAdminAdapter(ProdDnmaViaAdminRepository repository, ProdDnmaViaAdminMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<DnmaViaAdmin> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_dnma.via_admin with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_dnma.via_admin", ex));
    }
}
