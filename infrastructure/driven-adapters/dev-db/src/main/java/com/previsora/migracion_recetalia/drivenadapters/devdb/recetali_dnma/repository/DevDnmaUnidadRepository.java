package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaUnidadEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaUnidadRepository extends ReactiveCrudRepository<DevDnmaUnidadEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.unidad WHERE UNIDAD_ID = :unidadId")
    Mono<Long> countByUnidadId(String unidadId);
}
