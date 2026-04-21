package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevNotificationTypeMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevNotificationTypeRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationType;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devNotificationTypeWriter")
public class DevNotificationTypeAdapter implements TargetWriter<RecetaNotificationType> {

    private final DevNotificationTypeRepository repository;
    private final DevNotificationTypeMapper mapper;

    public DevNotificationTypeAdapter(DevNotificationTypeRepository repository, DevNotificationTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaNotificationType record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaNotificationType record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing notification_type to dev" + ": " + ex.getMessage(), ex));
    }
}
