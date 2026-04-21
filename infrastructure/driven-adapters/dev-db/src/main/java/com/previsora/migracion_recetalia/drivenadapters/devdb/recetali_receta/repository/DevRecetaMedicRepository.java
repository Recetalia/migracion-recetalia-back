package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * READ/WRITE repository for dev {@code recetali_receta.medic}.
 */
@Repository
public interface DevRecetaMedicRepository extends ReactiveCrudRepository<DevRecetaMedicEntity, String> {

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`medic` WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT id FROM `recetali_receta`.`medic` WHERE email = :email")
    Mono<String> findIdByEmail(String email);
}
