package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdPharmacyDispenserEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacyDispenser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdPharmacyDispenserMapper {

    RecetaPharmacyDispenser toDomain(ProdPharmacyDispenserEntity entity);
}
