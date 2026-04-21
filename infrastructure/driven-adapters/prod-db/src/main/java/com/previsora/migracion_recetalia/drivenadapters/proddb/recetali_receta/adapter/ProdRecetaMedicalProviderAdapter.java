package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaMedicalProviderMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaMedicalProviderRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProvider;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaMedicalProviderReader")
public class ProdRecetaMedicalProviderAdapter implements SourceReader<RecetaMedicalProvider> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaMedicalProviderAdapter.class);

    private final ProdRecetaMedicalProviderRepository repository;
    private final ProdRecetaMedicalProviderMapper mapper;

    public ProdRecetaMedicalProviderAdapter(ProdRecetaMedicalProviderRepository repository,
                                             ProdRecetaMedicalProviderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaMedicalProvider> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.medical_provider with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.medical_provider", ex));
    }
}
