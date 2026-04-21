package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaPatientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProdRecetaPatientRepository extends ReactiveCrudRepository<ProdRecetaPatientEntity, String> {

    @Query("SELECT * FROM recetali_receta.patient ORDER BY createdAt ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaPatientEntity> findAllPaginated(int offset, int limit);

    @Query("SELECT * FROM recetali_receta.patient WHERE id = :id")
    Mono<ProdRecetaPatientEntity> findBySourceId(String id);
}
