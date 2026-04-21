package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaPatientMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaPatientRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaPatientReader")
public class ProdRecetaPatientAdapter implements SourceReader<RecetaPatient> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaPatientAdapter.class);

    private final ProdRecetaPatientRepository repository;
    private final ProdRecetaPatientMapper mapper;

    public ProdRecetaPatientAdapter(ProdRecetaPatientRepository repository,
                                    ProdRecetaPatientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaPatient> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.patient with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.patient", ex));
    }

    @Override
    public Flux<RecetaPatient> readById(String sourceRecordId, int batchSize) {
        log.info("Reading patient {} from prod.recetali_receta.patient", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(mapper::toDomain)
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read patient " + sourceRecordId + " from prod.recetali_receta.patient", ex));
    }
}
