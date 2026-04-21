package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevPrescriptionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPrescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevPrescriptionMapper {

    DevPrescriptionEntity toEntity(RecetaPrescription domain);
}
