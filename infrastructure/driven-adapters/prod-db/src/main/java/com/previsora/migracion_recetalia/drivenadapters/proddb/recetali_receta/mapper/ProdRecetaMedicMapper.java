package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: ProdRecetaMedicEntity -> RecetaMedic domain model.
 */
@Mapper(componentModel = "spring")
public interface ProdRecetaMedicMapper {

    RecetaMedic toDomain(ProdRecetaMedicEntity entity);
}
