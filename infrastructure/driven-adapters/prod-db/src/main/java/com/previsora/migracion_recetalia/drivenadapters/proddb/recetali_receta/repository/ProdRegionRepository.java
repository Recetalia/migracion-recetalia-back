package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRegionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdRegionRepository extends ReactiveCrudRepository<ProdRegionEntity, String> {

    @Query("SELECT * FROM recetali_receta.regions ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRegionEntity> findAllPaginated(int offset, int limit);
}
