package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdVademecumEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVademecum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdVademecumMapper {

    RecetaVademecum toDomain(ProdVademecumEntity entity);
}
