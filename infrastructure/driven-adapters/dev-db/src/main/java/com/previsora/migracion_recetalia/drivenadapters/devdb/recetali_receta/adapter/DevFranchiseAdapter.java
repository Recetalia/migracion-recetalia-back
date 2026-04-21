package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevFranchiseMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevFranchiseRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFranchise;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devFranchiseWriter")
public class DevFranchiseAdapter implements TargetWriter<RecetaFranchise> {

    private final DevFranchiseRepository repository;
    private final DevFranchiseMapper mapper;

    public DevFranchiseAdapter(DevFranchiseRepository repository, DevFranchiseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaFranchise record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaFranchise record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing franchise to dev" + ": " + ex.getMessage(), ex));
    }
}
