package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdLogEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdLogMapper {

    RecetaLog toDomain(ProdLogEntity entity);
}
