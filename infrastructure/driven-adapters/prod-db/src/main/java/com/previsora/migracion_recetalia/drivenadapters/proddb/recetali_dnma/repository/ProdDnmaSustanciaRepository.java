package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaSustanciaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaSustanciaRepository extends ReactiveCrudRepository<ProdDnmaSustanciaEntity, String> {

    @Query("SELECT * FROM recetali_dnma.sustancia ORDER BY SUSTANCIA_ID ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaSustanciaEntity> findAllPaginated(int offset, int limit);
}
