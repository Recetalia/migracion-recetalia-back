package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaPharmacyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * READ-ONLY repository for prod {@code recetali_receta.pharmacy}.
 */
@Repository
public interface ProdRecetaPharmacyRepository extends ReactiveCrudRepository<ProdRecetaPharmacyEntity, String> {

    @Query("SELECT * FROM `recetali_receta`.`pharmacy` ORDER BY createdAt ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaPharmacyEntity> findAllPaginated(int offset, int limit);

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`pharmacy`")
    Mono<Long> countAll();

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`pharmacy` WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT * FROM `recetali_receta`.`pharmacy` WHERE id = :id")
    Mono<ProdRecetaPharmacyEntity> findBySourceId(String id);
}
