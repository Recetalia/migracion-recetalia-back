package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRegionMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRegionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaRegion;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRegionWriter")
public class DevRegionAdapter implements TargetWriter<RecetaRegion> {

    private final DevRegionRepository repository;
    private final DevRegionMapper mapper;

    public DevRegionAdapter(DevRegionRepository repository, DevRegionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaRegion record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaRegion record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing region to dev" + ": " + ex.getMessage(), ex));
    }
}
