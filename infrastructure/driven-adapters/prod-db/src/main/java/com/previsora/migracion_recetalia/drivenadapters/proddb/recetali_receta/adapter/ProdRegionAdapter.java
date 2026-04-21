package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRegionMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRegionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaRegion;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRegionReader")
public class ProdRegionAdapter implements SourceReader<RecetaRegion> {

    private final ProdRegionRepository repository;
    private final ProdRegionMapper mapper;

    public ProdRegionAdapter(ProdRegionRepository repository, ProdRegionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaRegion> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                        (offset, limit) -> repository.findAllPaginated(offset, limit)
                                .map(mapper::toDomain)
                                .collectList())
                .onErrorMap(ex -> new SourceReadException("Error reading regions from prod", ex));
    }
}
