package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevFranchiseEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFranchise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevFranchiseMapper {

    DevFranchiseEntity toEntity(RecetaFranchise domain);

    RecetaFranchise toDomain(DevFranchiseEntity entity);
}
