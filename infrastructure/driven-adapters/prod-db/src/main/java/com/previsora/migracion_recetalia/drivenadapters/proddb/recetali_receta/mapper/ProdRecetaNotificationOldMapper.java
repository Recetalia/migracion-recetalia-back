package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaNotificationOldEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationOld;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaNotificationOldMapper {

    RecetaNotificationOld toDomain(ProdRecetaNotificationOldEntity entity);
}
