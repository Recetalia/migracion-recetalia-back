package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdPrescriptionMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdPrescriptionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPrescription;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodPrescriptionReader")
public class ProdPrescriptionAdapter implements SourceReader<RecetaPrescription> {

    private static final Logger log = LoggerFactory.getLogger(ProdPrescriptionAdapter.class);

    private final ProdPrescriptionRepository repository;
    private final ProdPrescriptionMapper mapper;

    public ProdPrescriptionAdapter(ProdPrescriptionRepository repository,
                                    ProdPrescriptionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaPrescription> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.prescription with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.prescription", ex));
    }
}
