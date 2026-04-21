package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaNotificationOldMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaNotificationOldRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationOld;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRecetaNotificationOldWriter")
public class DevRecetaNotificationOldAdapter implements TargetWriter<RecetaNotificationOld> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaNotificationOldAdapter.class);

    private final DevRecetaNotificationOldRepository repository;
    private final DevRecetaNotificationOldMapper mapper;

    public DevRecetaNotificationOldAdapter(DevRecetaNotificationOldRepository repository,
                                            DevRecetaNotificationOldMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaNotificationOld record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaNotificationOld record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted notification_old: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert notification_old " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
