package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity.DevRecetaMedicEspecialityEntity;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicEspeciality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevRecetaMedicEspecialityMapper {

    DevRecetaMedicEspecialityEntity toEntity(RecetaMedicEspeciality domain);

    RecetaMedicEspeciality toDomain(DevRecetaMedicEspecialityEntity entity);
}
