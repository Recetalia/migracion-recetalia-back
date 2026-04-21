package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaUnidadEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaUnidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaUnidadMapper {
    DnmaUnidad toDomain(ProdDnmaUnidadEntity entity);
}
