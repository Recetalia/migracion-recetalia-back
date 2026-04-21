package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaTriggerEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaTrigger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaTriggerMapper {

    RecetaTrigger toDomain(ProdRecetaTriggerEntity entity);
}
