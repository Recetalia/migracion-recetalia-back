package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdFranchiseEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFranchise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdFranchiseMapper {

    RecetaFranchise toDomain(ProdFranchiseEntity entity);
}
