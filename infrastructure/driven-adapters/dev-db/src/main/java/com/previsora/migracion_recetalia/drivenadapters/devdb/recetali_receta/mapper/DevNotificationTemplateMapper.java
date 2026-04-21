package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevNotificationTemplateEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevNotificationTemplateMapper {

    DevNotificationTemplateEntity toEntity(RecetaNotificationTemplate domain);
}
