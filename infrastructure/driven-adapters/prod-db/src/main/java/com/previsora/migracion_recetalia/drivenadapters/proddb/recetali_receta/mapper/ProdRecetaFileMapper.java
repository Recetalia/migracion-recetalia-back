package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaFileEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaFileMapper {

    RecetaFile toDomain(ProdRecetaFileEntity entity);
}
