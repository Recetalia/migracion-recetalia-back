package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * READ-ONLY repository for prod {@code recetali_receta.medic}.
 */
@Repository
public interface ProdRecetaMedicRepository extends ReactiveCrudRepository<ProdRecetaMedicEntity, String> {

    @Query("SELECT * FROM `recetali_receta`.`medic` ORDER BY createdAt ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaMedicEntity> findAllPaginated(int offset, int limit);

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`medic`")
    Mono<Long> countAll();

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`medic` WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT * FROM `recetali_receta`.`medic` WHERE id = :id")
    Mono<ProdRecetaMedicEntity> findBySourceId(String id);
}
