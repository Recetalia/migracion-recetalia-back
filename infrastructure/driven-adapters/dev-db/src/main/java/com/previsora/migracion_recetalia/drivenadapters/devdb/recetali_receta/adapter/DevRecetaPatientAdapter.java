package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaPatientMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaPatientRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import com.previsora.migracion_recetalia.utility.DocumentNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component("devRecetaPatientWriter")
public class DevRecetaPatientAdapter implements TargetWriter<RecetaPatient> {

    private static final Logger log = LoggerFactory.getLogger(DevRecetaPatientAdapter.class);

    private final DevRecetaPatientRepository repository;
    private final DevRecetaPatientMapper mapper;
    private final DatabaseClient databaseClient;

    public DevRecetaPatientAdapter(DevRecetaPatientRepository repository,
                                   DevRecetaPatientMapper mapper,
                                   @Qualifier("devDatabaseClient") DatabaseClient databaseClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Boolean> exists(RecetaPatient record) {
        return repository.countByDocument(DocumentNormalizer.normalize(record.document()))
                .map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaPatient record) {
        var entity = mapper.toEntity(record);
        entity.setDocument(DocumentNormalizer.normalize(entity.getDocument()));
        DatabaseClient.GenericExecuteSpec insert = databaseClient.sql("""
                        INSERT INTO recetali_receta.patient
                        (id, name, lastname, email, phone, document, addressCountryId, addressLocalityId,
                         addressStreet, addressNumber, addressComments, `user`, password, birthdate,
                         createdAt, updatedAt, deletedAt, avatarId, sex)
                        VALUES
                        (:id, :name, :lastname, :email, :phone, :document, :addressCountryId, :addressLocalityId,
                         :addressStreet, :addressNumber, :addressComments, :user, :password, :birthdate,
                         :createdAt, :updatedAt, :deletedAt, :avatarId, :sex)
                        """)
                .bind("id", entity.getId())
                .bind("name", entity.getName())
                .bind("lastname", entity.getLastname())
                .bind("phone", entity.getPhone())
                .bind("document", entity.getDocument())
                .bind("user", entity.getUser())
                .bind("password", entity.getPassword())
                .bind("birthdate", entity.getBirthdate())
                .bind("createdAt", entity.getCreatedAt());

        insert = bindNullableString(insert, "email", entity.getEmail());
        insert = bindNullableString(insert, "addressCountryId", entity.getAddressCountryId());
        insert = bindNullableString(insert, "addressLocalityId", entity.getAddressLocalityId());
        insert = bindNullableString(insert, "addressStreet", entity.getAddressStreet());
        insert = bindNullableString(insert, "addressNumber", entity.getAddressNumber());
        insert = bindNullableString(insert, "addressComments", entity.getAddressComments());
        insert = bindNullableLocalDateTime(insert, "updatedAt", entity.getUpdatedAt());
        insert = bindNullableLocalDateTime(insert, "deletedAt", entity.getDeletedAt());
        insert = bindNullableString(insert, "avatarId", entity.getAvatarId());
        insert = bindNullableString(insert, "sex", entity.getSex());

        return insert.fetch()
                .rowsUpdated()
                .filter(updated -> updated > 0)
                .switchIfEmpty(Mono.error(new TargetWriteException(
                        "Insert affected 0 rows for receta patient with document " + record.document())))
                .doOnSuccess(updated -> log.debug("Inserted receta patient with document: {}", record.document()))
                .onErrorMap(ex -> new TargetWriteException(
                        "Failed to insert receta patient with document " + record.document() + ": " + ex.getMessage(), ex))
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
