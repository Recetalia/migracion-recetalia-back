package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaSustanciaEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaSustancia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaSustanciaMapper {
    DevDnmaSustanciaEntity toEntity(DnmaSustancia domain);
    DnmaSustancia toDomain(DevDnmaSustanciaEntity entity);
}
