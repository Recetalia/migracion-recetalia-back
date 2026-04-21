package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaMedicEspecialityMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaMedicEspecialityRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicEspeciality;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRecetaMedicEspecialityWriter")
public class DevRecetaMedicEspecialityAdapter implements TargetWriter<RecetaMedicEspeciality> {

    private final DevRecetaMedicEspecialityRepository repository;
    private final DevRecetaMedicEspecialityMapper mapper;

    public DevRecetaMedicEspecialityAdapter(DevRecetaMedicEspecialityRepository repository, DevRecetaMedicEspecialityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaMedicEspeciality record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaMedicEspeciality record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing to recetali_receta.medic_especiality" + ": " + ex.getMessage(), ex));
    }
}
