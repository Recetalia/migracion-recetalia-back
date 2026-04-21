package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevCountryEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaCountry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevCountryMapper {

    DevCountryEntity toEntity(RecetaCountry domain);

    RecetaCountry toDomain(DevCountryEntity entity);
}
