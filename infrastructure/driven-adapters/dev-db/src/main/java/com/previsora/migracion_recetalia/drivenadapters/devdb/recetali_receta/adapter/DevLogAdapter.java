package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevLogMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevLogRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLog;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devLogWriter")
public class DevLogAdapter implements TargetWriter<RecetaLog> {

    private static final Logger log = LoggerFactory.getLogger(DevLogAdapter.class);

    private final DevLogRepository repository;
    private final DevLogMapper mapper;

    public DevLogAdapter(DevLogRepository repository,
                          DevLogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaLog record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaLog record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted log: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert log " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
