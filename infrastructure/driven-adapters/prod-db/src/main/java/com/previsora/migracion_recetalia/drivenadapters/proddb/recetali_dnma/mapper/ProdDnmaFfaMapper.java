package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaFfaEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaFfa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaFfaMapper {
    DnmaFfa toDomain(ProdDnmaFfaEntity entity);
}
