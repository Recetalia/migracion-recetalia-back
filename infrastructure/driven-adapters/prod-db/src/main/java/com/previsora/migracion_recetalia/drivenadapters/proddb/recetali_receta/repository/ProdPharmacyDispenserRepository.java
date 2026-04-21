package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdPharmacyDispenserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdPharmacyDispenserRepository extends ReactiveCrudRepository<ProdPharmacyDispenserEntity, String> {

    @Query("SELECT * FROM recetali_receta.pharmacy_dispenser ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdPharmacyDispenserEntity> findAllPaginated(int offset, int limit);
}
