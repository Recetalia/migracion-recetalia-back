package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicalProviderEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaMedicalProviderMapper {

    DevRecetaMedicalProviderEntity toEntity(RecetaMedicalProvider domain);
}
