package com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.mapper.DevSecurityUserMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.securitydb.repository.DevSecurityUserRepository;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Target writer adapter for {@code dev.securitydb.users}.
 * <p>
 * Checks for duplicates by email and inserts new records.
 * The id is auto-increment — set to null before save.
 */
@Component("devSecurityUserWriter")
public class DevSecurityUserAdapter implements TargetWriter<SecurityUser> {

    private static final Logger log = LoggerFactory.getLogger(DevSecurityUserAdapter.class);

    private final DevSecurityUserRepository repository;
    private final DevSecurityUserMapper mapper;
    private final DatabaseClient databaseClient;

    public DevSecurityUserAdapter(DevSecurityUserRepository repository,
                                   DevSecurityUserMapper mapper,
                                   @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(SecurityUser record) {
        return repository.countByEmail(record.email()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(SecurityUser record) {
        var entity = mapper.toEntity(record);
        return databaseClient.sql("""
                        INSERT INTO securitydb.users
                        (is_active, application_id, email, password, username)
                        VALUES
                        (:isActive, :applicationId, :email, :password, :username)
                        """)
                .bind("isActive", entity.isActive())
                .bind("applicationId", entity.getApplicationId())
                .bind("email", entity.getEmail())
                .bind("password", entity.getPassword())
                .bind("username", entity.getUsername())
                .fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for security user " + record.email())))
                .doOnSuccess(updated -> log.debug("Inserted security user: {}", record.email()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert security user " + record.email() + ": " + ex.getMessage(), ex))
                .then();
    }
}
