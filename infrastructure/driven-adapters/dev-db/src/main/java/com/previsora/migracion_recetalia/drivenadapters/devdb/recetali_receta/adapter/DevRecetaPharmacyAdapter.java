package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaPharmacyMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaPharmacyRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
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
 * Target writer adapter for {@code dev.recetali_receta.pharmacy}.
 * <p>
 * Checks for duplicates by email and inserts new records.
 */
@Component("devRecetaPharmacyWriter")
public class DevRecetaPharmacyAdapter implements TargetWriter<RecetaPharmacy> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaPharmacyAdapter.class);

    private final DevRecetaPharmacyRepository repository;
    private final DevRecetaPharmacyMapper mapper;
    private final DatabaseClient databaseClient;

    public DevRecetaPharmacyAdapter(DevRecetaPharmacyRepository repository,
                                     DevRecetaPharmacyMapper mapper,
                                     @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(RecetaPharmacy record) {
        return repository.countByEmail(record.email()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaPharmacy record) {
        var entity = mapper.toEntity(record);
        DatabaseClient.GenericExecuteSpec insert = databaseClient.sql("""
                        INSERT INTO `recetali_receta`.`pharmacy`
                        (`id`, `name`, `addressCountryId`, `addressLocalityId`, `addressStreet`,
                         `addressNumber`, `addressComments`, `phone`, `email`, `password`,
                         `businessName`, `rut`, `camera`, `createdAt`, `updatedAt`, `deletedAt`,
                         `franchiseId`, `logoId`, `passwordForgotCode`, `managerName`,
                         `managerLastname`, `managerCJP`, `status`, `managerDocument`)
                        VALUES
                        (:id, :name, :addressCountryId, :addressLocalityId, :addressStreet,
                         :addressNumber, :addressComments, :phone, :email, :password,
                         :businessName, :rut, :camera, :createdAt, :updatedAt, :deletedAt,
                         :franchiseId, :logoId, :passwordForgotCode, :managerName,
                         :managerLastname, :managerCJP, :status, :managerDocument)
                        """)
                .bind("id", entity.getId())
                .bind("name", entity.getName())
                .bind("phone", entity.getPhone())
                .bind("email", entity.getEmail())
                .bind("password", entity.getPassword())
                .bind("businessName", entity.getBusinessName())
                .bind("rut", entity.getRut())
                .bind("createdAt", entity.getCreatedAt())
                .bind("updatedAt", entity.getUpdatedAt())
                .bind("managerName", entity.getManagerName())
                .bind("managerLastname", entity.getManagerLastname())
                .bind("managerCJP", entity.getManagerCJP())
                .bind("status", entity.getStatus());

        insert = bindNullableString(insert, "addressCountryId", entity.getAddressCountryId());
        insert = bindNullableString(insert, "addressLocalityId", entity.getAddressLocalityId());
        insert = bindNullableString(insert, "addressStreet", entity.getAddressStreet());
        insert = bindNullableString(insert, "addressNumber", entity.getAddressNumber());
        insert = bindNullableString(insert, "addressComments", entity.getAddressComments());
        insert = bindNullableString(insert, "camera", entity.getCamera());
        insert = bindNullableLocalDateTime(insert, "deletedAt", entity.getDeletedAt());
        insert = bindNullableString(insert, "franchiseId", entity.getFranchiseId());
        insert = bindNullableString(insert, "logoId", entity.getLogoId());
        insert = bindNullableString(insert, "passwordForgotCode", entity.getPasswordForgotCode());
        insert = bindNullableString(insert, "managerDocument", entity.getManagerDocument());

        return insert.fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for pharmacy " + record.email())))
                .doOnSuccess(updated -> log.debug("Inserted pharmacy: {}", record.email()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert pharmacy " + record.email() + ": " + ex.getMessage(), ex))
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
