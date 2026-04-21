package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaTfEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DevDnmaTfRepository extends ReactiveCrudRepository<DevDnmaTfEntity, String> {

    @Query("SELECT COUNT(*) FROM recetali_dnma.tf WHERE TF_Id = :tfId")
    Mono<Long> countByTfId(String tfId);
}
