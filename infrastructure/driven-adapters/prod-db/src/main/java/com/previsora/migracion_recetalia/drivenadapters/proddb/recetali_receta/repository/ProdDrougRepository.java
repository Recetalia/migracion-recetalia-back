package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdDrougEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDrougRepository extends ReactiveCrudRepository<ProdDrougEntity, String> {

    @Query("SELECT * FROM recetali_receta.droug ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDrougEntity> findAllPaginated(int offset, int limit);
}
