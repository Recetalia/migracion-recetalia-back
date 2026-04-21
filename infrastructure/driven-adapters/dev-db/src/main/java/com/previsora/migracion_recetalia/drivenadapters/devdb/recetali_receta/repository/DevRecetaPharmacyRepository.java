package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaPharmacyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * READ/WRITE repository for dev {@code recetali_receta.pharmacy}.
 */
@Repository
public interface DevRecetaPharmacyRepository extends ReactiveCrudRepository<DevRecetaPharmacyEntity, String> {

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`pharmacy` WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT id FROM `recetali_receta`.`pharmacy` WHERE email = :email")
    Mono<String> findIdByEmail(String email);
}
