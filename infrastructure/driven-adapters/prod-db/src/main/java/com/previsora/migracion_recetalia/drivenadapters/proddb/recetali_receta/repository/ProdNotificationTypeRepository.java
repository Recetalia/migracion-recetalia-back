package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdNotificationTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdNotificationTypeRepository extends ReactiveCrudRepository<ProdNotificationTypeEntity, String> {

    @Query("SELECT * FROM recetali_receta.notification_type ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdNotificationTypeEntity> findAllPaginated(int offset, int limit);
}
