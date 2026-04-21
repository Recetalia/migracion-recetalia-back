package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdCountryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdCountryRepository extends ReactiveCrudRepository<ProdCountryEntity, String> {

    @Query("SELECT * FROM recetali_receta.country ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdCountryEntity> findAllPaginated(int offset, int limit);
}
