package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdCountryEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaCountry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdCountryMapper {

    RecetaCountry toDomain(ProdCountryEntity entity);
}
