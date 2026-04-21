package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaTfEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaTfRepository extends ReactiveCrudRepository<ProdDnmaTfEntity, String> {

    @Query("SELECT * FROM recetali_dnma.tf ORDER BY TF_Id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaTfEntity> findAllPaginated(int offset, int limit);
}
