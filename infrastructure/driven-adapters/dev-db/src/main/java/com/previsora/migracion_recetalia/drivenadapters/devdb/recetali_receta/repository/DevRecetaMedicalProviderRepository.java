package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicalProviderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevRecetaMedicalProviderRepository extends ReactiveCrudRepository<DevRecetaMedicalProviderEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.medical_provider WHERE rut = :rut")
    Mono<Long> countByRut(String rut);
}
