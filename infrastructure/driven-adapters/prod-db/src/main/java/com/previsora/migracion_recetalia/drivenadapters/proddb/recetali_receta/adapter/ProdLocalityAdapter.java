package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdLocalityMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdLocalityRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLocality;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodLocalityReader")
public class ProdLocalityAdapter implements SourceReader<RecetaLocality> {

    private final ProdLocalityRepository repository;
    private final ProdLocalityMapper mapper;

    public ProdLocalityAdapter(ProdLocalityRepository repository, ProdLocalityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaLocality> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                        (offset, limit) -> repository.findAllPaginated(offset, limit)
                                .map(mapper::toDomain)
                                .collectList())
                .onErrorMap(ex -> new SourceReadException("Error reading localities from prod", ex));
    }
}
