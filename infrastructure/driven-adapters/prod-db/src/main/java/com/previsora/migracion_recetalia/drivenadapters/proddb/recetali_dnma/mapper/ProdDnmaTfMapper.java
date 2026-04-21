package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity.ProdDnmaTfEntity;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaTf;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdDnmaTfMapper {
    DnmaTf toDomain(ProdDnmaTfEntity entity);
}
