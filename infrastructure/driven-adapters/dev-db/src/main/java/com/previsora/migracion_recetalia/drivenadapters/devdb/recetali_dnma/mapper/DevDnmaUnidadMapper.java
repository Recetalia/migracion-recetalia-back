package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaUnidadEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaUnidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaUnidadMapper {
    DevDnmaUnidadEntity toEntity(DnmaUnidad domain);
    DnmaUnidad toDomain(DevDnmaUnidadEntity entity);
}
