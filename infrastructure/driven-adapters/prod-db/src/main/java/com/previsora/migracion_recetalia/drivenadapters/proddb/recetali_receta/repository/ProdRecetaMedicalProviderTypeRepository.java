package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicalProviderTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProdRecetaMedicalProviderTypeRepository extends ReactiveCrudRepository<ProdRecetaMedicalProviderTypeEntity, String> {

    @Query("SELECT * FROM recetali_receta.medical_provider_type ORDER BY id ASC LIMIT :limit OFFSET :offset")
    Flux<ProdRecetaMedicalProviderTypeEntity> findAllPaginated(int offset, int limit);
}
