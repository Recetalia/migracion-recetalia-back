package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaPatientEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaPatientMapper {

    RecetaPatient toDomain(ProdRecetaPatientEntity entity);
}
