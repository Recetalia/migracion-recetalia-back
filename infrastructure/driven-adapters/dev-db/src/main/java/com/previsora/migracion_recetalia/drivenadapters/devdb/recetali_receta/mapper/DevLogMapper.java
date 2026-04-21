package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevLogEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevLogMapper {

    DevLogEntity toEntity(RecetaLog domain);
}
