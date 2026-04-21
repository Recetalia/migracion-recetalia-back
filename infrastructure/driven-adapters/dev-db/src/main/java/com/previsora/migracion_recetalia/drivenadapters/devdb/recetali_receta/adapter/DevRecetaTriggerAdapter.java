package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaTriggerMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaTriggerRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaTrigger;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRecetaTriggerWriter")
public class DevRecetaTriggerAdapter implements TargetWriter<RecetaTrigger> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaTriggerAdapter.class);

    private final DevRecetaTriggerRepository repository;
    private final DevRecetaTriggerMapper mapper;

    public DevRecetaTriggerAdapter(DevRecetaTriggerRepository repository, DevRecetaTriggerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaTrigger record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaTrigger record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted trigger: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert trigger " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
