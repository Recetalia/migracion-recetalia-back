package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevNotificationTypeEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevNotificationTypeMapper {

    DevNotificationTypeEntity toEntity(RecetaNotificationType domain);

    RecetaNotificationType toDomain(DevNotificationTypeEntity entity);
}
