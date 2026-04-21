package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaUserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * READ/WRITE repository for dev {@code recetali_receta.user}.
 */
@Repository
public interface DevRecetaUserRepository extends ReactiveCrudRepository<DevRecetaUserEntity, String> {

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`user` WHERE email = :email")
    Mono<Long> countByEmail(String email);
}
