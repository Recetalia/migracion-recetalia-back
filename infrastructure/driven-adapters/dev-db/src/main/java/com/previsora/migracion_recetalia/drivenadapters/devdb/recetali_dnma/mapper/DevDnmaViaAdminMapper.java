package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity.DevDnmaViaAdminEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaViaAdmin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevDnmaViaAdminMapper {
    DevDnmaViaAdminEntity toEntity(DnmaViaAdmin domain);
    DnmaViaAdmin toDomain(DevDnmaViaAdminEntity entity);
}
