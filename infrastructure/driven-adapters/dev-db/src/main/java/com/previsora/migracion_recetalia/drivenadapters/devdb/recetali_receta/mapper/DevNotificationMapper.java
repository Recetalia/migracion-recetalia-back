package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevNotificationEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevNotificationMapper {

    DevNotificationEntity toEntity(RecetaNotification domain);
}
