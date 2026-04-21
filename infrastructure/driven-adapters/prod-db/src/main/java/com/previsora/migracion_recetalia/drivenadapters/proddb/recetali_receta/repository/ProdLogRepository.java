package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdLogEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdLogRepository extends ReactiveCrudRepository<ProdLogEntity, String> {

    @Query("SELECT * FROM `recetali_receta`.`log` ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdLogEntity> findAllPaginated(int offset, int limit);
}
