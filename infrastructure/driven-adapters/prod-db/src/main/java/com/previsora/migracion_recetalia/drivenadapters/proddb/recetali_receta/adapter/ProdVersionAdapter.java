package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdVersionMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdVersionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVersion;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodVersionReader")
public class ProdVersionAdapter implements SourceReader<RecetaVersion> {

    private final ProdVersionRepository repository;
    private final ProdVersionMapper mapper;

    public ProdVersionAdapter(ProdVersionRepository repository, ProdVersionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaVersion> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                        (offset, limit) -> repository.findAllPaginated(offset, limit)
                                .map(mapper::toDomain)
                                .collectList())
                .onErrorMap(ex -> new SourceReadException("Error reading version from prod", ex));
    }
}
