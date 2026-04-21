package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevLaboratoryEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLaboratory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevLaboratoryMapper {

    DevLaboratoryEntity toEntity(RecetaLaboratory domain);
}
