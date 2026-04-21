package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaUserEntity;
import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper: RecetaUser domain model -> DevRecetaUserEntity.
 */
@Mapper(componentModel = "spring")
public interface DevRecetaUserMapper {

    DevRecetaUserEntity toEntity(RecetaUser domain);

    RecetaUser toDomain(DevRecetaUserEntity entity);
}
