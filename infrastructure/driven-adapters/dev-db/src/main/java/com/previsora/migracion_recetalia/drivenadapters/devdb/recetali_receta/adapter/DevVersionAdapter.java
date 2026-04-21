package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevVersionMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevVersionRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVersion;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devVersionWriter")
public class DevVersionAdapter implements TargetWriter<RecetaVersion> {

    private final DevVersionRepository repository;
    private final DevVersionMapper mapper;

    public DevVersionAdapter(DevVersionRepository repository, DevVersionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaVersion record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaVersion record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing version to dev" + ": " + ex.getMessage(), ex));
    }
}
