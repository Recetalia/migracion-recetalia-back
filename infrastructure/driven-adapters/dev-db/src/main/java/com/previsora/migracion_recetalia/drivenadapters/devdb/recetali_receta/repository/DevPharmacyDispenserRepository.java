package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevPharmacyDispenserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevPharmacyDispenserRepository extends ReactiveCrudRepository<DevPharmacyDispenserEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_receta.pharmacy_dispenser WHERE id = :id")
    Mono<Long> countById(String id);

    @Query("SELECT id FROM recetali_receta.pharmacy_dispenser WHERE id = :id AND pharmacyId = :pharmacyId")
    Mono<String> findIdByIdAndPharmacyId(String id, String pharmacyId);
}
