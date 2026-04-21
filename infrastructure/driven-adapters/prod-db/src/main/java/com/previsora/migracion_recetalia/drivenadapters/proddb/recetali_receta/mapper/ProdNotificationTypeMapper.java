package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdNotificationTypeEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdNotificationTypeMapper {

    RecetaNotificationType toDomain(ProdNotificationTypeEntity entity);
}
