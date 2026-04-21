package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicalProviderTypeEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProviderType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaMedicalProviderTypeMapper {

    DevRecetaMedicalProviderTypeEntity toEntity(RecetaMedicalProviderType domain);

    RecetaMedicalProviderType toDomain(DevRecetaMedicalProviderTypeEntity entity);
}
