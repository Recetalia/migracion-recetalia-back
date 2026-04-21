package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicalProviderEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaMedicalProviderMapper {

    RecetaMedicalProvider toDomain(ProdRecetaMedicalProviderEntity entity);
}
