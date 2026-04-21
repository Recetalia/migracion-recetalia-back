package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaNotificationOldEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationOld;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaNotificationOldMapper {

    DevRecetaNotificationOldEntity toEntity(RecetaNotificationOld domain);
}
