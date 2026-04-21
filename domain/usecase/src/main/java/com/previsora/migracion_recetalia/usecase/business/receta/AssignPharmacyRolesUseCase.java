package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.SecurityUserRoleGateway;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Assigns {@code ROLE_PHARMACY} to all migrated pharmacies in securitydb.
 * <p>
 * Reads all pharmacies from prod, and for each one checks if the corresponding
 * security user already has ROLE_PHARMACY. If not, assigns it.
 */
public class AssignPharmacyRolesUseCase implements MigrationOrchestrator {

    public static final String MIGRATION_NAME = "security-pharmacy-role";
    public static final String PHARMACY_ROLE = "ROLE_PHARMACY";

    private static final Logger log = LoggerFactory.getLogger(AssignPharmacyRolesUseCase.class);

    private final SourceReader<RecetaPharmacy> sourceReader;
    private final SecurityUserRoleGateway roleGateway;

    public AssignPharmacyRolesUseCase(SourceReader<RecetaPharmacy> sourceReader,
                                       SecurityUserRoleGateway roleGateway) {
        this.sourceReader = sourceReader;
        this.roleGateway = roleGateway;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    public Mono<MigrationSummary> execute(MigrationRequest request) {
        MigrationAccumulator acc = new MigrationAccumulator(MIGRATION_NAME, request.dryRun());

        log.info("[{}] Starting role assignment — dryRun={}, batchSize={}",
                MIGRATION_NAME, request.dryRun(), request.batchSize());

        return readFromSource(request)
                .distinct(RecetaPharmacy::email)
                .doOnNext(pharmacy -> acc.incrementRead())
                .concatMap(pharmacy -> assignRoleToPharmacy(pharmacy, request, acc))
                .then(Mono.fromSupplier(acc::toSummary))
                .doOnSuccess(summary -> log.info(
                        "[{}] Role assignment finished — read={}, inserted={}, skipped={}, failed={}, dryRun={}, duration={}ms",
                        MIGRATION_NAME, summary.totalRead(), summary.totalInserted(),
                        summary.totalSkipped(), summary.totalFailed(), summary.dryRun(), summary.durationMs()
                ))
                .onErrorResume(ex -> {
                    log.error("[{}] Role assignment failed with exception: {}", MIGRATION_NAME, ex.getMessage(), ex);
                    acc.addError(new MigrationError("GLOBAL", ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.just(acc.toSummary());
                });
    }

    private reactor.core.publisher.Flux<RecetaPharmacy> readFromSource(MigrationRequest request) {
        if (Objects.nonNull(request.sourceRecordId()) && !request.sourceRecordId().isBlank()) {
            return sourceReader.readById(request.sourceRecordId(), request.batchSize());
        }
        return sourceReader.readAll(request.batchSize());
    }

    private Mono<Void> assignRoleToPharmacy(RecetaPharmacy pharmacy, MigrationRequest request, MigrationAccumulator acc) {
        return roleGateway.hasRole(pharmacy.email(), PHARMACY_ROLE)
                .flatMap(hasRole -> {
                    if (hasRole) {
                        acc.incrementSkipped();
                        return Mono.<Void>empty();
                    }
                    if (request.dryRun()) {
                        acc.incrementInserted();
                        return Mono.<Void>empty();
                    }
                    return roleGateway.assignRole(pharmacy.email(), PHARMACY_ROLE)
                            .doOnSuccess(v -> {
                                log.info("[{}] Assigned {} to {}", MIGRATION_NAME, PHARMACY_ROLE, pharmacy.email());
                                acc.incrementInserted();
                            });
                })
                .onErrorResume(ex -> {
                    log.warn("[{}] Failed to assign role to {}: {}",
                            MIGRATION_NAME, pharmacy.email(), ex.getMessage());
                    acc.addError(new MigrationError(pharmacy.email(), ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.empty();
                });
    }
}
