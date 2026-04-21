package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRegionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaRegion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRegionMapper {

    RecetaRegion toDomain(ProdRegionEntity entity);
}
