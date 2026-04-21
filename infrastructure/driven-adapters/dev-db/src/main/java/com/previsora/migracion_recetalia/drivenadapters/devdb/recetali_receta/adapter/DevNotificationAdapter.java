package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevNotificationMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevNotificationRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotification;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devNotificationWriter")
public class DevNotificationAdapter implements TargetWriter<RecetaNotification> {

    private static final Logger log = LoggerFactory.getLogger(DevNotificationAdapter.class);

    private final DevNotificationRepository repository;
    private final DevNotificationMapper mapper;

    public DevNotificationAdapter(DevNotificationRepository repository,
                                   DevNotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaNotification record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaNotification record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted notification: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert notification " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
