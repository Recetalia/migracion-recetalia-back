package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaFileEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaFileMapper {

    DevRecetaFileEntity toEntity(RecetaFile domain);

    RecetaFile toDomain(DevRecetaFileEntity entity);
}
