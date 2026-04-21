package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaFfaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaFfaRepository extends ReactiveCrudRepository<DevDnmaFfaEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.ffa WHERE FFA_Id = :ffaId")
    Mono<Long> countByFfaId(String ffaId);
}
