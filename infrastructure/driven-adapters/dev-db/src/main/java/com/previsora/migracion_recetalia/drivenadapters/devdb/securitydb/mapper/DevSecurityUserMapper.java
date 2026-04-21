package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.mapper;

import com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.entity.DevSecurityUserEntity;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper: SecurityUser domain model -> DevSecurityUserEntity.
 */
@Mapper(componentModel = "spring")
public interface DevSecurityUserMapper {

    @Mapping(target = "active", source = "isActive")
    DevSecurityUserEntity toEntity(SecurityUser domain);

    @Mapping(target = "isActive", source = "active")
    SecurityUser toDomain(DevSecurityUserEntity entity);
}
