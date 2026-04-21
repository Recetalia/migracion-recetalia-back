package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaFfaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaFfaRepository extends ReactiveCrudRepository<ProdDnmaFfaEntity, String> {

    @Query("SELECT * FROM recetali_dnma.ffa ORDER BY FFA_Id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaFfaEntity> findAllPaginated(int offset, int limit);
}
