package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaViaAdminEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaViaAdminRepository extends ReactiveCrudRepository<DevDnmaViaAdminEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.via_admin WHERE VIA_ADMIN_Id = :viaAdminId")
    Mono<Long> countByViaAdminId(String viaAdminId);
}
