package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevVademecumEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVademecum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevVademecumMapper {

    DevVademecumEntity toEntity(RecetaVademecum domain);

    RecetaVademecum toDomain(DevVademecumEntity entity);
}
