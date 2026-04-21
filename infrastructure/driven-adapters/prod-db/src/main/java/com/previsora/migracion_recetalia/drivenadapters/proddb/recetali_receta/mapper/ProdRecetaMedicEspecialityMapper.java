package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity.ProdRecetaMedicEspecialityEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicEspeciality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdRecetaMedicEspecialityMapper {

    RecetaMedicEspeciality toDomain(ProdRecetaMedicEspecialityEntity entity);
}
