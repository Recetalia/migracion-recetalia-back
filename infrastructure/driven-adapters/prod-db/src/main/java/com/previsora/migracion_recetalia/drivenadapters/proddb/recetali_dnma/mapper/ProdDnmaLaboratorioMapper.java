package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaLaboratorioEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaLaboratorio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaLaboratorioMapper {
    DnmaLaboratorio toDomain(ProdDnmaLaboratorioEntity entity);
}
