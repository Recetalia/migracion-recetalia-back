package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdFranchiseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdFranchiseRepository extends ReactiveCrudRepository<ProdFranchiseEntity, String> {

    @Query("SELECT * FROM recetali_receta.franchise ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdFranchiseEntity> findAllPaginated(int offset, int limit);
}
