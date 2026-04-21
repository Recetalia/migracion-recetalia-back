package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdVademecumEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdVademecumRepository extends ReactiveCrudRepository<ProdVademecumEntity, String> {

    @Query("SELECT * FROM recetali_receta.vademecum ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdVademecumEntity> findAllPaginated(int offset, int limit);
}
