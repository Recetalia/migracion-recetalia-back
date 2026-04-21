package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdNotificationTemplateMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdNotificationTemplateRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationTemplate;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodNotificationTemplateReader")
public class ProdNotificationTemplateAdapter implements SourceReader<RecetaNotificationTemplate> {

    private static final Logger log = LoggerFactory.getLogger(ProdNotificationTemplateAdapter.class);

    private final ProdNotificationTemplateRepository repository;
    private final ProdNotificationTemplateMapper mapper;

    public ProdNotificationTemplateAdapter(ProdNotificationTemplateRepository repository,
                                            ProdNotificationTemplateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaNotificationTemplate> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.notification_template with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.notification_template", ex));
    }
}
