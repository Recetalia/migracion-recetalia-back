package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevLocalityMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevLocalityRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLocality;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devLocalityWriter")
public class DevLocalityAdapter implements TargetWriter<RecetaLocality> {

    private final DevLocalityRepository repository;
    private final DevLocalityMapper mapper;

    public DevLocalityAdapter(DevLocalityRepository repository, DevLocalityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaLocality record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaLocality record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing locality to dev" + ": " + ex.getMessage(), ex));
    }
}
