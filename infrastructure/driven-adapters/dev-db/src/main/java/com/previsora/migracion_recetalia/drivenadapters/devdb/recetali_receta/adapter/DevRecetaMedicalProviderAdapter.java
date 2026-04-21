package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaMedicalProviderMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaMedicalProviderRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProvider;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component("devRecetaMedicalProviderWriter")
public class DevRecetaMedicalProviderAdapter implements TargetWriter<RecetaMedicalProvider> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaMedicalProviderAdapter.class);

    private final DevRecetaMedicalProviderRepository repository;
    private final DevRecetaMedicalProviderMapper mapper;
    private final DatabaseClient databaseClient;

    public DevRecetaMedicalProviderAdapter(DevRecetaMedicalProviderRepository repository,
                                            DevRecetaMedicalProviderMapper mapper,
                                            @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(RecetaMedicalProvider record) {
        return databaseClient.sql(
                        "SELECT COUNT(*) FROM recetali_receta.medical_provider WHERE name = :name")
                .bind("name", record.name())
                .map((row, meta) -> row.get(0, Long.class))
                .one()
                .map(count -> count != null && count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaMedicalProvider record) {
        var entity = mapper.toEntity(record);
        DatabaseClient.GenericExecuteSpec insert = databaseClient.sql("""
                        INSERT INTO recetali_receta.medical_provider
                        (id, medicalProviderTypeId, name, addressCountryId, addressLocalityId, addressStreet,
                         addressNumber, addressComments, phone, email, password, businessName, rut,
                         createdAt, updatedAt, deletedAt, logoId, passwordForgotCode, status)
                        VALUES
                        (:id, :medicalProviderTypeId, :name, :addressCountryId, :addressLocalityId, :addressStreet,
                         :addressNumber, :addressComments, :phone, :email, :password, :businessName, :rut,
                         :createdAt, :updatedAt, :deletedAt, :logoId, :passwordForgotCode, :status)
                        """)
                .bind("id", entity.getId())
                .bind("medicalProviderTypeId", entity.getMedicalProviderTypeId())
                .bind("name", entity.getName())
                .bind("phone", entity.getPhone())
                .bind("email", entity.getEmail())
                .bind("password", entity.getPassword())
                .bind("businessName", entity.getBusinessName())
                .bind("rut", entity.getRut())
                .bind("createdAt", entity.getCreatedAt())
                .bind("status", entity.getStatus());

        insert = bindNullableString(insert, "addressCountryId", entity.getAddressCountryId());
        insert = bindNullableString(insert, "addressLocalityId", entity.getAddressLocalityId());
        insert = bindNullableString(insert, "addressStreet", entity.getAddressStreet());
        insert = bindNullableString(insert, "addressNumber", entity.getAddressNumber());
        insert = bindNullableString(insert, "addressComments", entity.getAddressComments());
        insert = bindNullableLocalDateTime(insert, "updatedAt", entity.getUpdatedAt());
        insert = bindNullableLocalDateTime(insert, "deletedAt", entity.getDeletedAt());
        insert = bindNullableString(insert, "logoId", entity.getLogoId());
        insert = bindNullableString(insert, "passwordForgotCode", entity.getPasswordForgotCode());

        return insert.fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for medical_provider with rut " + record.rut())))
                .doOnSuccess(updated -> log.debug("Inserted medical_provider with rut: {}", record.rut()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert medical_provider with rut " + record.rut() + ": " + ex.getMessage(), ex))
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
