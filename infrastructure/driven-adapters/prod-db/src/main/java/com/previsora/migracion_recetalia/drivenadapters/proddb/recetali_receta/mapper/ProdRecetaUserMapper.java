package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaUserEntity;
import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: ProdRecetaUserEntity -> RecetaUser domain model.
 */
@Mapper(componentModel = "spring")
public interface ProdRecetaUserMapper {

    RecetaUser toDomain(ProdRecetaUserEntity entity);
}
