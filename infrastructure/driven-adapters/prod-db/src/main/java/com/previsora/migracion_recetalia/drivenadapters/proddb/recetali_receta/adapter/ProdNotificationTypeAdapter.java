package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdNotificationTypeMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdNotificationTypeRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationType;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodNotificationTypeReader")
public class ProdNotificationTypeAdapter implements SourceReader<RecetaNotificationType> {

    private final ProdNotificationTypeRepository repository;
    private final ProdNotificationTypeMapper mapper;

    public ProdNotificationTypeAdapter(ProdNotificationTypeRepository repository, ProdNotificationTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaNotificationType> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                        (offset, limit) -> repository.findAllPaginated(offset, limit)
                                .map(mapper::toDomain)
                                .collectList())
                .onErrorMap(ex -> new SourceReadException("Error reading notification_type from prod", ex));
    }
}
