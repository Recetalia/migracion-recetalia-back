package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicEspecialityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DevRecetaMedicEspecialityRepository extends ReactiveCrudRepository<DevRecetaMedicEspecialityEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.medic_especiality WHERE id = :id")
    Mono<Long> countById(String id);
}
