package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevPharmacyDispenserEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacyDispenser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevPharmacyDispenserMapper {

    DevPharmacyDispenserEntity toEntity(RecetaPharmacyDispenser domain);
}
