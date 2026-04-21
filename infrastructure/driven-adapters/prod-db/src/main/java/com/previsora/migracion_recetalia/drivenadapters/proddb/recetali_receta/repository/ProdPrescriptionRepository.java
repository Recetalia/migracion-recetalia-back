package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdPrescriptionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdPrescriptionRepository extends ReactiveCrudRepository<ProdPrescriptionEntity, String> {

    @Query("SELECT * FROM recetali_receta.prescription ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdPrescriptionEntity> findAllPaginated(int offset, int limit);
}
