package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaViaAdminEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdDnmaViaAdminRepository extends ReactiveCrudRepository<ProdDnmaViaAdminEntity, String> {

    @Query("SELECT * FROM recetali_dnma.via_admin ORDER BY VIA_ADMIN_Id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdDnmaViaAdminEntity> findAllPaginated(int offset, int limit);
}
