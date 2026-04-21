package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaUserMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaUserRepository;
import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Target writer adapter for {@code dev.recetali_receta.user}.
 * <p>
 * Checks for duplicates by email and inserts new records.
 */
@Component("devRecetaUserWriter")
public class DevRecetaUserAdapter implements TargetWriter<RecetaUser> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaUserAdapter.class);

    private final DevRecetaUserRepository repository;
    private final DevRecetaUserMapper mapper;
    private final DatabaseClient databaseClient;

    public DevRecetaUserAdapter(DevRecetaUserRepository repository,
                                 DevRecetaUserMapper mapper,
                                 @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(RecetaUser record) {
        return repository.countByEmail(record.email()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaUser record) {
        var entity = mapper.toEntity(record);
        DatabaseClient.GenericExecuteSpec insert = databaseClient.sql("""
                        INSERT INTO `recetali_receta`.`user`
                        (`id`, `name`, `lastname`, `email`, `password`, `createdAt`, `updatedAt`, `deletedAt`, `avatarId`)
                        VALUES
                        (:id, :name, :lastname, :email, :password, :createdAt, :updatedAt, :deletedAt, :avatarId)
                        """)
                .bind("id", entity.getId())
                .bind("name", entity.getName())
                .bind("lastname", entity.getLastname())
                .bind("email", entity.getEmail())
                .bind("password", entity.getPassword())
                .bind("createdAt", entity.getCreatedAt())
                .bind("updatedAt", entity.getUpdatedAt());

        insert = entity.getDeletedAt() != null
                ? insert.bind("deletedAt", entity.getDeletedAt())
                : insert.bindNull("deletedAt", java.time.LocalDateTime.class);

        insert = entity.getAvatarId() != null
                ? insert.bind("avatarId", entity.getAvatarId())
                : insert.bindNull("avatarId", String.class);

        return insert.fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for receta user " + record.email())))
                .doOnSuccess(updated -> log.debug("Inserted receta user: {}", record.email()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert receta user " + record.email() + ": " + ex.getMessage(), ex))
                .then();
    }
}
