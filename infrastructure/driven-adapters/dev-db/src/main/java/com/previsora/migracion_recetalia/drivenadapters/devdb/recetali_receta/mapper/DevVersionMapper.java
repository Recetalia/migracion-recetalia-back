package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevVersionEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevVersionMapper {

    DevVersionEntity toEntity(RecetaVersion domain);

    RecetaVersion toDomain(DevVersionEntity entity);
}
