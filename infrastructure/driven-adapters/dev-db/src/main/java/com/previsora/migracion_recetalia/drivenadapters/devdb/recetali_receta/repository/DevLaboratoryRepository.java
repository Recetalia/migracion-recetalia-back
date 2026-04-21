package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevLaboratoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevLaboratoryRepository extends ReactiveCrudRepository<DevLaboratoryEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.laboratory WHERE id = :id")
    Mono<Long> countById(String id);
}
