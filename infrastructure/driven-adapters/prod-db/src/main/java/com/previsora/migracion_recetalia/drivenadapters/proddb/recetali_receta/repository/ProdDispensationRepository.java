package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdDispensationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * READ-ONLY repository for prod {@code recetali_receta.dispensation}.
 */
@Repository
public interface ProdDispensationRepository extends ReactiveCrudRepository<ProdDispensationEntity, String> {

    @Query("SELECT * FROM recetali_receta.dispensation ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDispensationEntity> findAllPaginated(int offset, int limit);
}
