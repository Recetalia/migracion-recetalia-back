package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaSustanciaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaSustanciaRepository extends ReactiveCrudRepository<DevDnmaSustanciaEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.sustancia WHERE SUSTANCIA_ID = :sustanciaId")
    Mono<Long> countBySustanciaId(String sustanciaId);
}
