package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaPharmacyEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: ProdRecetaPharmacyEntity -> RecetaPharmacy domain model.
 */
@Mapper(componentModel = "spring")
public interface ProdRecetaPharmacyMapper {

    RecetaPharmacy toDomain(ProdRecetaPharmacyEntity entity);
}
