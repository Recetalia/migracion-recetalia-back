package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaPharmacyEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: RecetaPharmacy domain model <-> DevRecetaPharmacyEntity.
 */
@Mapper(componentModel = "spring")
public interface DevRecetaPharmacyMapper {

    DevRecetaPharmacyEntity toEntity(RecetaPharmacy domain);

    RecetaPharmacy toDomain(DevRecetaPharmacyEntity entity);
}
