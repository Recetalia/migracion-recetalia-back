package com.previsora.migracion_recetalia.usecase.business;

import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.SecurityUserRoleGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Assigns a security role to the migrated security user derived from a receta user.
 */
public class AssignSecurityRoleUseCase {

    public static final String MIGRATION_NAME = "security-user-role";

    private static final Logger log = LoggerFactory.getLogger(AssignSecurityRoleUseCase.class);

    private final SecurityUserRoleGateway roleGateway;

    public AssignSecurityRoleUseCase(SecurityUserRoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public Mono<MigrationSummary> execute(RecetaUser sourceUser, String roleName, MigrationRequest request) {
        MigrationAccumulator acc = new MigrationAccumulator(MIGRATION_NAME, request.dryRun());
        acc.incrementRead();

        return roleGateway.hasRole(sourceUser.email(), roleName)
                .flatMap(hasRole -> {
                    if (hasRole) {
                        acc.incrementSkipped();
                        return Mono.<Void>empty();
                    }
                    if (request.dryRun()) {
                        acc.incrementInserted();
                        return Mono.<Void>empty();
                    }
                    return roleGateway.assignRole(sourceUser.email(), roleName)
                            .doOnSuccess(v -> {
                                log.info("[{}] Assigned role {} to {}", MIGRATION_NAME, roleName, sourceUser.email());
                                acc.incrementInserted();
                            });
                })
                .thenReturn(acc.toSummary())
                .onErrorResume(ex -> {
                    log.warn("[{}] Failed to assign role {} to {}: {}",
                            MIGRATION_NAME, roleName, sourceUser.email(), ex.getMessage());
                    acc.addError(new MigrationError(sourceUser.email(), ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.just(acc.toSummary());
                });
    }
}
