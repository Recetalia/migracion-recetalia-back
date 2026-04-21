package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaLaboratorioEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaLaboratorio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaLaboratorioMapper {
    DevDnmaLaboratorioEntity toEntity(DnmaLaboratorio domain);
    DnmaLaboratorio toDomain(DevDnmaLaboratorioEntity entity);
}
