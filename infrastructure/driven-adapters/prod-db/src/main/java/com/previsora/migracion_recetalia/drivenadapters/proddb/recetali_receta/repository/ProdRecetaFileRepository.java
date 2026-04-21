package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaFileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdRecetaFileRepository extends ReactiveCrudRepository<ProdRecetaFileEntity, String> {

    @Query("SELECT * FROM recetali_receta.file ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaFileEntity> findAllPaginated(int offset, int limit);
}
