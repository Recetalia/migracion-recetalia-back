package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevLogEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevLogRepository extends ReactiveCrudRepository<DevLogEntity, String> {

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`log` WHERE id = :id")
    Mono<Long> countById(String id);
}
