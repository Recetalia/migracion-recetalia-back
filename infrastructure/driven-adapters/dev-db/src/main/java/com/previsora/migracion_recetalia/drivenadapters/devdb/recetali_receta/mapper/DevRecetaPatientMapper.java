package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaPatientEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaPatientMapper {

    DevRecetaPatientEntity toEntity(RecetaPatient domain);
}
