package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicalProviderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProdRecetaMedicalProviderRepository extends ReactiveCrudRepository<ProdRecetaMedicalProviderEntity, String> {

    @Query("SELECT * FROM recetali_receta.medical_provider ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaMedicalProviderEntity> findAllPaginated(int offset, int limit);
}
