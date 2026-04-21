package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaMedicMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaMedicRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Target writer adapter for {@code dev.recetali_receta.medic}.
 * <p>
 * Checks for duplicates by email and inserts new records.
 */
@Component("devRecetaMedicWriter")
public class DevRecetaMedicAdapter implements TargetWriter<RecetaMedic> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaMedicAdapter.class);

    private final DevRecetaMedicRepository repository;
    private final DevRecetaMedicMapper mapper;
    private final DatabaseClient databaseClient;

    public DevRecetaMedicAdapter(DevRecetaMedicRepository repository,
                                  DevRecetaMedicMapper mapper,
                                  @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(RecetaMedic record) {
        return repository.countByEmail(record.email()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaMedic record) {
        var entity = mapper.toEntity(record);
        DatabaseClient.GenericExecuteSpec insert = databaseClient.sql("""
                        INSERT INTO `recetali_receta`.`medic`
                        (`id`, `name`, `lastname`, `gender`, `email`, `password`, `phone`, `document`,
                         `birthdate`, `addressCountryId`, `addressLocalityId`, `addressStreet`,
                         `addressNumber`, `addressComments`, `createdAt`, `updatedAt`, `deletedAt`,
                         `cjp`, `passwordForgotCode`, `status`, `especialityId`, `medicalProviderId`)
                        VALUES
                        (:id, :name, :lastname, :gender, :email, :password, :phone, :document,
                         :birthdate, :addressCountryId, :addressLocalityId, :addressStreet,
                         :addressNumber, :addressComments, :createdAt, :updatedAt, :deletedAt,
                         :cjp, :passwordForgotCode, :status, :especialityId, :medicalProviderId)
                        """)
                .bind("id", entity.getId())
                .bind("name", entity.getName())
                .bind("lastname", entity.getLastname())
                .bind("email", entity.getEmail())
                .bind("password", entity.getPassword())
                .bind("phone", entity.getPhone())
                .bind("document", entity.getDocument())
                .bind("birthdate", entity.getBirthdate())
                .bind("createdAt", entity.getCreatedAt())
                .bind("updatedAt", entity.getUpdatedAt())
                .bind("cjp", entity.getCjp())
                .bind("status", entity.getStatus());

        insert = bindNullableString(insert, "gender", entity.getGender());
        insert = bindNullableString(insert, "addressCountryId", entity.getAddressCountryId());
        insert = bindNullableString(insert, "addressLocalityId", entity.getAddressLocalityId());
        insert = bindNullableString(insert, "addressStreet", entity.getAddressStreet());
        insert = bindNullableString(insert, "addressNumber", entity.getAddressNumber());
        insert = bindNullableString(insert, "addressComments", entity.getAddressComments());
        insert = bindNullableLocalDateTime(insert, "deletedAt", entity.getDeletedAt());
        insert = bindNullableString(insert, "passwordForgotCode", entity.getPasswordForgotCode());
        insert = bindNullableString(insert, "especialityId", entity.getEspecialityId());
        insert = bindNullableString(insert, "medicalProviderId", entity.getMedicalProviderId());

        return insert.fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for medic " + record.email())))
                .doOnSuccess(updated -> log.debug("Inserted medic: {}", record.email()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert medic " + record.email() + ": " + ex.getMessage(), ex))
                .then();
    }

    private DatabaseClient.GenericExecuteSpec bindNullableString(DatabaseClient.GenericExecuteSpec spec,
                                                                  String name,
                                                                  String value) {
        return value != null ? spec.bind(name, value) : spec.bindNull(name, String.class);
    }

    private DatabaseClient.GenericExecuteSpec bindNullableLocalDateTime(DatabaseClient.GenericExecuteSpec spec,
                                                                        String name,
                                                                        LocalDateTime value) {
        return value != null ? spec.bind(name, value) : spec.bindNull(name, LocalDateTime.class);
    }
}
