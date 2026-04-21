package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdNotificationMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdNotificationRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotification;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodNotificationReader")
public class ProdNotificationAdapter implements SourceReader<RecetaNotification> {

    private static final Logger log = LoggerFactory.getLogger(ProdNotificationAdapter.class);

    private final ProdNotificationRepository repository;
    private final ProdNotificationMapper mapper;

    public ProdNotificationAdapter(ProdNotificationRepository repository,
                                    ProdNotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaNotification> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.notification with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.notification", ex));
    }
}
