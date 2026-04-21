package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicalProviderTypeEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProviderType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaMedicalProviderTypeMapper {

    RecetaMedicalProviderType toDomain(ProdRecetaMedicalProviderTypeEntity entity);
}
