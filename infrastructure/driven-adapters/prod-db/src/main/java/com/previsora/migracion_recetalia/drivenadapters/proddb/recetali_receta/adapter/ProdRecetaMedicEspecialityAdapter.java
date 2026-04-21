package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaMedicEspecialityMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaMedicEspecialityRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicEspeciality;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaMedicEspecialityReader")
public class ProdRecetaMedicEspecialityAdapter implements SourceReader<RecetaMedicEspeciality> {

    private final ProdRecetaMedicEspecialityRepository repository;
    private final ProdRecetaMedicEspecialityMapper mapper;

    public ProdRecetaMedicEspecialityAdapter(ProdRecetaMedicEspecialityRepository repository, ProdRecetaMedicEspecialityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaMedicEspeciality> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Error reading from recetali_receta.medic_especiality", ex));
    }
}
