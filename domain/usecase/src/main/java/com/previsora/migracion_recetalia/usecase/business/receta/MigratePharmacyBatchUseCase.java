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
 * Batch orchestrator that migrates all pharmacies from prod into dev:
 * <ol>
 *   <li>recetali_receta.pharmacy (direct copy)</li>
 *   <li>securitydb.users (derived from pharmacy)</li>
 *   <li>securitydb.user_roles — assign ROLE_PHARMACY</li>
 * </ol>
 * <p>
 * All three steps run sequentially using {@code Flux.concat()}.
 */
public class MigratePharmacyBatchUseCase {

    private static final Logger log = LoggerFactory.getLogger(MigratePharmacyBatchUseCase.class);

    private final MigrationOrchestrator recetaPharmacyMigration;
    private final MigrationOrchestrator securityPharmacyMigration;
    private final MigrationOrchestrator assignPharmacyRoles;

    public MigratePharmacyBatchUseCase(MigrationOrchestrator recetaPharmacyMigration,
                                        MigrationOrchestrator securityPharmacyMigration,
                                        MigrationOrchestrator assignPharmacyRoles) {
        this.recetaPharmacyMigration = recetaPharmacyMigration;
        this.securityPharmacyMigration = securityPharmacyMigration;
        this.assignPharmacyRoles = assignPharmacyRoles;
    }

    /**
     * Execute all pharmacy migration steps sequentially and collect results.
     *
     * @param request migration parameters (dryRun, batchSize, duplicatePolicy, truncateFirst)
     * @return list of per-step migration summaries
     */
    public Mono<List<MigrationSummary>> execute(MigrationRequest request) {
        log.info("Starting pharmacy batch migration — dryRun={}, batchSize={}, policy={}",
                request.dryRun(), request.batchSize(), request.duplicatePolicy());

        return Flux.concat(
                        recetaPharmacyMigration.execute(request),
                        securityPharmacyMigration.execute(request),
                        assignPharmacyRoles.execute(request)
                )
                .collectList()
                .doOnSuccess(summaries -> {
                    long totalInserted = summaries.stream()
                            .mapToLong(MigrationSummary::totalInserted).sum();
                    long totalFailed = summaries.stream()
                            .mapToLong(MigrationSummary::totalFailed).sum();
                    log.info("Pharmacy batch migration completed — steps={}, totalInserted={}, totalFailed={}",
                            summaries.size(), totalInserted, totalFailed);
                });
    }
}
