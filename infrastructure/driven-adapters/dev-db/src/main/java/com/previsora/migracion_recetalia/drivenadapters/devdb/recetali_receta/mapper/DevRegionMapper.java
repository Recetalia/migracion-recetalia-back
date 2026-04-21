package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRegionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaRegion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRegionMapper {

    DevRegionEntity toEntity(RecetaRegion domain);

    RecetaRegion toDomain(DevRegionEntity entity);
}
