package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdNotificationEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdNotificationMapper {

    RecetaNotification toDomain(ProdNotificationEntity entity);
}
