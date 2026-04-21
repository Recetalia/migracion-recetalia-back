package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaPatientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevRecetaPatientRepository extends ReactiveCrudRepository<DevRecetaPatientEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.patient WHERE document = :document")
    Mono<Long> countByDocument(String document);

    @Query("SELECT id FROM recetali_receta.patient WHERE document = :document")
    Mono<String> findIdByDocument(String document);
}
