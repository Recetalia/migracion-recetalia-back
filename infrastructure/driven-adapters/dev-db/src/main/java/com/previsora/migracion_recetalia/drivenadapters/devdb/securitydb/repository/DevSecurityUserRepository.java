package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.repository;

import com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.entity.DevSecurityUserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * READ/WRITE repository for dev {@code securitydb.users}.
 */
@Repository
public interface DevSecurityUserRepository extends ReactiveCrudRepository<DevSecurityUserEntity, Long> {

    @Query("SELECT COUNT(*) FROM securitydb.users WHERE email = :email")
    Mono<Long> countByEmail(String email);

    @Query("SELECT * FROM securitydb.users WHERE email = :email")
    Mono<DevSecurityUserEntity> findByEmail(String email);
}
