package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevPharmacyDispenserMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevPharmacyDispenserRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacyDispenser;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devPharmacyDispenserWriter")
public class DevPharmacyDispenserAdapter implements TargetWriter<RecetaPharmacyDispenser> {

    private static final Logger log = LoggerFactory.getLogger(DevPharmacyDispenserAdapter.class);

    private final DevPharmacyDispenserRepository repository;
    private final DevPharmacyDispenserMapper mapper;

    public DevPharmacyDispenserAdapter(DevPharmacyDispenserRepository repository,
                                        DevPharmacyDispenserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaPharmacyDispenser record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaPharmacyDispenser record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted pharmacy_dispenser: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert pharmacy_dispenser " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
