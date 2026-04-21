package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaFfaEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaFfa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaFfaMapper {
    DevDnmaFfaEntity toEntity(DnmaFfa domain);
    DnmaFfa toDomain(DevDnmaFfaEntity entity);
}
