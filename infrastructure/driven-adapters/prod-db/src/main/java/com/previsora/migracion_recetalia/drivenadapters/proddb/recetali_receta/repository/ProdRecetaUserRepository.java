package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaUserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * READ-ONLY repository for prod {@code recetali_receta.user}.
 */
@Repository
public interface ProdRecetaUserRepository extends ReactiveCrudRepository<ProdRecetaUserEntity, String> {

    @Query("SELECT * FROM `recetali_receta`.`user` ORDER BY createdAt ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaUserEntity> findAllPaginated(int offset, int limit);

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`user`")
    Mono<Long> countAll();

    @Query("SELECT COUNT(*) FROM `recetali_receta`.`user` WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT * FROM `recetali_receta`.`user` WHERE id = :id")
    Mono<ProdRecetaUserEntity> findBySourceId(String id);
}
