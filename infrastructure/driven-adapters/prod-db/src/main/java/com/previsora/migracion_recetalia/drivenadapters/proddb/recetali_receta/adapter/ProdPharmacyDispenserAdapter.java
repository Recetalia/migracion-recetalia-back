package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdPharmacyDispenserMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdPharmacyDispenserRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacyDispenser;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodPharmacyDispenserReader")
public class ProdPharmacyDispenserAdapter implements SourceReader<RecetaPharmacyDispenser> {

    private static final Logger log = LoggerFactory.getLogger(ProdPharmacyDispenserAdapter.class);

    private final ProdPharmacyDispenserRepository repository;
    private final ProdPharmacyDispenserMapper mapper;

    public ProdPharmacyDispenserAdapter(ProdPharmacyDispenserRepository repository,
                                         ProdPharmacyDispenserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaPharmacyDispenser> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.pharmacy_dispenser with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.pharmacy_dispenser", ex));
    }
}
