package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdVersionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdVersionMapper {

    RecetaVersion toDomain(ProdVersionEntity entity);
}
