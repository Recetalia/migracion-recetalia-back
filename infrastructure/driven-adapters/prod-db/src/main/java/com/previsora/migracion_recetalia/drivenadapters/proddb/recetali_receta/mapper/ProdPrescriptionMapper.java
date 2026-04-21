package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdPrescriptionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPrescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdPrescriptionMapper {

    RecetaPrescription toDomain(ProdPrescriptionEntity entity);
}
