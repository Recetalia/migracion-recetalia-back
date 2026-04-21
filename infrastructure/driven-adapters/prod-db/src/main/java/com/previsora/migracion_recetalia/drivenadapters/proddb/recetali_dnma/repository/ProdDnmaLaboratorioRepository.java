package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaLaboratorioEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaLaboratorioRepository extends ReactiveCrudRepository<ProdDnmaLaboratorioEntity, String> {

    @Query("SELECT * FROM recetali_dnma.laboratorio ORDER BY LAB_Id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaLaboratorioEntity> findAllPaginated(int offset, int limit);
}
