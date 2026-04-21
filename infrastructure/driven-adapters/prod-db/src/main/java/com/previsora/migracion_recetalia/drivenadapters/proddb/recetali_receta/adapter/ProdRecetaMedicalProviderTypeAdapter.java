package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaMedicalProviderTypeMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaMedicalProviderTypeRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProviderType;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaMedicalProviderTypeReader")
public class ProdRecetaMedicalProviderTypeAdapter implements SourceReader<RecetaMedicalProviderType> {

    private final ProdRecetaMedicalProviderTypeRepository repository;
    private final ProdRecetaMedicalProviderTypeMapper mapper;

    public ProdRecetaMedicalProviderTypeAdapter(ProdRecetaMedicalProviderTypeRepository repository, ProdRecetaMedicalProviderTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaMedicalProviderType> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Error reading from recetali_receta.medical_provider_type", ex));
    }
}
