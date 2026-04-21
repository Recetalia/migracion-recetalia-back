package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdLaboratoryEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLaboratory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdLaboratoryMapper {

    RecetaLaboratory toDomain(ProdLaboratoryEntity entity);
}
