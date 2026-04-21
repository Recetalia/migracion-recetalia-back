package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdLocalityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdLocalityRepository extends ReactiveCrudRepository<ProdLocalityEntity, String> {

    @Query("SELECT * FROM recetali_receta.localities ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdLocalityEntity> findAllPaginated(int offset, int limit);
}
