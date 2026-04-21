package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Batch orchestrator that migrates all medics from prod into dev:
 * <ol>
 *   <li>recetali_receta.medic (direct copy)</li>
 *   <li>securitydb.users (derived from medic)</li>
 *   <li>securitydb.user_roles — assign ROLE_MEDIC</li>
 * </ol>
 * <p>
 * All three steps run sequentially using {@code Flux.concat()}.
 */
public class MigrateMedicBatchUseCase {

    private static final Logger log = LoggerFactory.getLogger(MigrateMedicBatchUseCase.class);

    private final MigrationOrchestrator recetaMedicMigration;
    private final MigrationOrchestrator securityMedicMigration;
    private final MigrationOrchestrator assignMedicRoles;

    public MigrateMedicBatchUseCase(MigrationOrchestrator recetaMedicMigration,
                                     MigrationOrchestrator securityMedicMigration,
                                     MigrationOrchestrator assignMedicRoles) {
        this.recetaMedicMigration = recetaMedicMigration;
        this.securityMedicMigration = securityMedicMigration;
        this.assignMedicRoles = assignMedicRoles;
    }

    /**
     * Execute all medic migration steps sequentially and collect results.
     *
     * @param request migration parameters (dryRun, batchSize, duplicatePolicy, truncateFirst)
     * @return list of per-step migration summaries
     */
    public Mono<List<MigrationSummary>> execute(MigrationRequest request) {
        log.info("Starting medic batch migration — dryRun={}, batchSize={}, policy={}",
                request.dryRun(), request.batchSize(), request.duplicatePolicy());

        return Flux.concat(
                        recetaMedicMigration.execute(request),
                        securityMedicMigration.execute(request),
                        assignMedicRoles.execute(request)
                )
                .collectList()
                .doOnSuccess(summaries -> {
                    long totalInserted = summaries.stream()
                            .mapToLong(MigrationSummary::totalInserted).sum();
                    long totalFailed = summaries.stream()
                            .mapToLong(MigrationSummary::totalFailed).sum();
                    log.info("Medic batch migration completed — steps={}, totalInserted={}, totalFailed={}",
                            summaries.size(), totalInserted, totalFailed);
                });
    }
}
