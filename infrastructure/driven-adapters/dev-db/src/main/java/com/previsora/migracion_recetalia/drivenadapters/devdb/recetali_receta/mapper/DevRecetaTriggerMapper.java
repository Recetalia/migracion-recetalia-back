package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaTriggerEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaTrigger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaTriggerMapper {

    DevRecetaTriggerEntity toEntity(RecetaTrigger domain);
}
