package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: RecetaMedic domain model <-> DevRecetaMedicEntity.
 */
@Mapper(componentModel = "spring")
public interface DevRecetaMedicMapper {

    DevRecetaMedicEntity toEntity(RecetaMedic domain);

    RecetaMedic toDomain(DevRecetaMedicEntity entity);
}
