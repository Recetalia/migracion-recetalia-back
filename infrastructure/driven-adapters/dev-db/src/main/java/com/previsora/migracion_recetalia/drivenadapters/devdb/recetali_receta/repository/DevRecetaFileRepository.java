package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaFileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DevRecetaFileRepository extends ReactiveCrudRepository<DevRecetaFileEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.file WHERE id = :id")
    Mono<Long> countById(String id);
}
