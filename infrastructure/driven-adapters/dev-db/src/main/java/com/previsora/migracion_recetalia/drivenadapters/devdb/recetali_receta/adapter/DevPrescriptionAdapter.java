package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevPrescriptionMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevPrescriptionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPrescription;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devPrescriptionWriter")
public class DevPrescriptionAdapter implements TargetWriter<RecetaPrescription> {

    private static final Logger log = LoggerFactory.getLogger(DevPrescriptionAdapter.class);

    private final DevPrescriptionRepository repository;
    private final DevPrescriptionMapper mapper;

    public DevPrescriptionAdapter(DevPrescriptionRepository repository,
                                   DevPrescriptionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaPrescription record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaPrescription record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted prescription: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert prescription " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
