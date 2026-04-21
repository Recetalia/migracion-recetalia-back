package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaViaAdminEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaViaAdmin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaViaAdminMapper {
    DnmaViaAdmin toDomain(ProdDnmaViaAdminEntity entity);
}
