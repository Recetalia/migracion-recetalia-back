package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevLocalityEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLocality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevLocalityMapper {

    DevLocalityEntity toEntity(RecetaLocality domain);

    RecetaLocality toDomain(DevLocalityEntity entity);
}
