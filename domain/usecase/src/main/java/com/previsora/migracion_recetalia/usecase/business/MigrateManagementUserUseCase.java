package com.previsora.migracion_recetalia.usecase.business;

import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.error.MigrationException;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * Orchestrates the migration of a single receta user into dev receta/security schemas
 * and ensures the resulting security user has {@code ROLE_MANAGEMENT}.
 */
public class MigrateManagementUserUseCase {

    public static final String MANAGEMENT_ROLE = "ROLE_MANAGEMENT";

    private static final Logger log = LoggerFactory.getLogger(MigrateManagementUserUseCase.class);

    private final SourceReader<RecetaUser> recetaUserSourceReader;
    private final MigrationOrchestrator recetaUserMigration;
    private final MigrationOrchestrator securityUsersMigration;
    private final AssignSecurityRoleUseCase assignSecurityRoleUseCase;

    public MigrateManagementUserUseCase(SourceReader<RecetaUser> recetaUserSourceReader,
                                        MigrationOrchestrator recetaUserMigration,
                                        MigrationOrchestrator securityUsersMigration,
                                        AssignSecurityRoleUseCase assignSecurityRoleUseCase) {
        this.recetaUserSourceReader = recetaUserSourceReader;
        this.recetaUserMigration = recetaUserMigration;
        this.securityUsersMigration = securityUsersMigration;
        this.assignSecurityRoleUseCase = assignSecurityRoleUseCase;
    }

    public Mono<List<MigrationSummary>> execute(MigrationRequest request) {
        if (Objects.isNull(request.sourceRecordId()) || request.sourceRecordId().isBlank()) {
            return Mono.error(new MigrationException("sourceRecordId is required for management user migration"));
        }

        return recetaUserSourceReader.readById(request.sourceRecordId(), request.batchSize())
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new MigrationException(
                        "No source user found for id " + request.sourceRecordId())))
                .flatMap(sourceUser -> {
                    log.info("Starting management user migration for sourceUserId={}, email={}",
                            sourceUser.id(), sourceUser.email());
                    return Flux.concat(
                                    recetaUserMigration.execute(request),
                                    securityUsersMigration.execute(request),
                                    assignSecurityRoleUseCase.execute(sourceUser, MANAGEMENT_ROLE, request)
                            )
                            .collectList();
                });
    }
}
