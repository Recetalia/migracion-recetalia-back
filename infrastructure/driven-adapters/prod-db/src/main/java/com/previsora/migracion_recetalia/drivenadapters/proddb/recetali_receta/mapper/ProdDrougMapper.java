package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdDrougEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDroug;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDrougMapper {

    RecetaDroug toDomain(ProdDrougEntity entity);
}
