package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdFranchiseMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdFranchiseRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFranchise;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodFranchiseReader")
public class ProdFranchiseAdapter implements SourceReader<RecetaFranchise> {

    private final ProdFranchiseRepository repository;
    private final ProdFranchiseMapper mapper;

    public ProdFranchiseAdapter(ProdFranchiseRepository repository, ProdFranchiseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaFranchise> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                        (offset, limit) -> repository.findAllPaginated(offset, limit)
                                .map(mapper::toDomain)
                                .collectList())
                .onErrorMap(ex -> new SourceReadException("Error reading franchise from prod", ex));
    }
}
