package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaNotificationOldMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaNotificationOldRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationOld;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaNotificationOldReader")
public class ProdRecetaNotificationOldAdapter implements SourceReader<RecetaNotificationOld> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaNotificationOldAdapter.class);

    private final ProdRecetaNotificationOldRepository repository;
    private final ProdRecetaNotificationOldMapper mapper;

    public ProdRecetaNotificationOldAdapter(ProdRecetaNotificationOldRepository repository,
                                             ProdRecetaNotificationOldMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaNotificationOld> readAll(int batchSize) {
        log.info("Reading all records from prod.recetali_receta.notification_old with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Failed to read from prod.recetali_receta.notification_old", ex));
    }
}
