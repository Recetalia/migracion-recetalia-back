package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaLaboratorioEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaLaboratorioRepository extends ReactiveCrudRepository<DevDnmaLaboratorioEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.laboratorio WHERE LAB_Id = :labId")
    Mono<Long> countByLabId(String labId);
}
