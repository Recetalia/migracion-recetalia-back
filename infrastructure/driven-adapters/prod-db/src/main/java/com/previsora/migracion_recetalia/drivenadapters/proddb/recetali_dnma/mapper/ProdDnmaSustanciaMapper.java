package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaSustanciaEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaSustancia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaSustanciaMapper {
    DnmaSustancia toDomain(ProdDnmaSustanciaEntity entity);
}
