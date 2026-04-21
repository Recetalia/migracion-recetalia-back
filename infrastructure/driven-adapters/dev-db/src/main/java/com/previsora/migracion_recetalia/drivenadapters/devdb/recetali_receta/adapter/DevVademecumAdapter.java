package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevVademecumMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevVademecumRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVademecum;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devVademecumWriter")
public class DevVademecumAdapter implements TargetWriter<RecetaVademecum> {

    private final DevVademecumRepository repository;
    private final DevVademecumMapper mapper;

    public DevVademecumAdapter(DevVademecumRepository repository, DevVademecumMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaVademecum record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaVademecum record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing vademecum to dev" + ": " + ex.getMessage(), ex));
    }
}
