package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdLocalityEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLocality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdLocalityMapper {

    RecetaLocality toDomain(ProdLocalityEntity entity);
}
