package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevCountryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DevCountryRepository extends ReactiveCrudRepository<DevCountryEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.country WHERE id = :id")
    Mono<Long> countById(String id);
}
