package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdDispensationEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaDispensation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDispensationMapper {

    RecetaDispensation toDomain(ProdDispensationEntity entity);
}
