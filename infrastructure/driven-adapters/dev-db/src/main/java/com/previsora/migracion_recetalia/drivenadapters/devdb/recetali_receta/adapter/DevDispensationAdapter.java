package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevDispensationMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevDispensationRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDispensation;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDispensationWriter")
public class DevDispensationAdapter implements TargetWriter<RecetaDispensation> {

    private static final Logger log = LoggerFactory.getLogger(DevDispensationAdapter.class);

    private final DevDispensationRepository repository;
    private final DevDispensationMapper mapper;

    public DevDispensationAdapter(DevDispensationRepository repository,
                                   DevDispensationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaDispensation record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaDispensation record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dispensation: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dispensation " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
