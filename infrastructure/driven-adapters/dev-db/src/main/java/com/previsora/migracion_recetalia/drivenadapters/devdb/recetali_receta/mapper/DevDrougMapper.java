package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevDrougEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDroug;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDrougMapper {

    DevDrougEntity toEntity(RecetaDroug domain);

    RecetaDroug toDomain(DevDrougEntity entity);
}
