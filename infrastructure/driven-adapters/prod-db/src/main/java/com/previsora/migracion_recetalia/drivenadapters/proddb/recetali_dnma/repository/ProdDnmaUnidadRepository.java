package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaUnidadEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaUnidadRepository extends ReactiveCrudRepository<ProdDnmaUnidadEntity, String> {

    @Query("SELECT * FROM recetali_dnma.unidad ORDER BY UNIDAD_ID ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaUnidadEntity> findAllPaginated(int offset, int limit);
}
