package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevDispensationEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDispensation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDispensationMapper {

    DevDispensationEntity toEntity(RecetaDispensation domain);
}
