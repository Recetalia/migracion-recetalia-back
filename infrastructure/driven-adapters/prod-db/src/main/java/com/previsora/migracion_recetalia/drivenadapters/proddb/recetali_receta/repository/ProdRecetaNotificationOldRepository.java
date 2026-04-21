package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaNotificationOldEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdRecetaNotificationOldRepository extends ReactiveCrudRepository<ProdRecetaNotificationOldEntity, String> {

    @Query("SELECT * FROM recetali_receta.notification_old ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaNotificationOldEntity> findAllPaginated(int offset, int limit);
}
