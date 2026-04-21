package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaTfEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaTf;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaTfMapper {
    DevDnmaTfEntity toEntity(DnmaTf domain);
    DnmaTf toDomain(DevDnmaTfEntity entity);
}
