package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdNotificationTemplateEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdNotificationTemplateMapper {

    RecetaNotificationTemplate toDomain(ProdNotificationTemplateEntity entity);
}
