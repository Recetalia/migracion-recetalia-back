package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevDrougMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevDrougRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDroug;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDrougWriter")
public class DevDrougAdapter implements TargetWriter<RecetaDroug> {

    private final DevDrougRepository repository;
    private final DevDrougMapper mapper;

    public DevDrougAdapter(DevDrougRepository repository, DevDrougMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaDroug record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaDroug record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing droug to dev" + ": " + ex.getMessage(), ex));
    }
}
