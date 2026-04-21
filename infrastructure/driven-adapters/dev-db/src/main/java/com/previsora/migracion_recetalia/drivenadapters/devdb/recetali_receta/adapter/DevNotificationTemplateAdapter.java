package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevNotificationTemplateMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevNotificationTemplateRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationTemplate;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devNotificationTemplateWriter")
public class DevNotificationTemplateAdapter implements TargetWriter<RecetaNotificationTemplate> {

    private static final Logger log = LoggerFactory.getLogger(DevNotificationTemplateAdapter.class);

    private final DevNotificationTemplateRepository repository;
    private final DevNotificationTemplateMapper mapper;

    public DevNotificationTemplateAdapter(DevNotificationTemplateRepository repository,
                                           DevNotificationTemplateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaNotificationTemplate record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaNotificationTemplate record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted notification_template: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert notification_template " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
