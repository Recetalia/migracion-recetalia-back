package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.TableTruncator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Batch orchestrator that runs all Level 0 receta table migrations sequentially.
 * <p>
 * Level 0 tables have no foreign-key dependencies and can be migrated independently.
 * When {@code truncateFirst=true}, all target tables are truncated before migration starts.
 */
public class RecetaLevel0BatchUseCase {

    private static final Logger log = LoggerFactory.getLogger(RecetaLevel0BatchUseCase.class);

    /** Level 0 table names in truncation order (safe — no FK deps from other Level 0 tables). */
    private static final List<String> LEVEL_0_TABLES = List.of(
            "recetali_receta.country",
            "recetali_receta.regions",
            "recetali_receta.localities",
            "recetali_receta.file",
            "recetali_receta.medic_especiality",
            "recetali_receta.medical_provider_type",
            "recetali_receta.franchise",
            "recetali_receta.notification_type",
            "recetali_receta.droug",
            "recetali_receta.vademecum",
            "recetali_receta.version",
            "`recetali_receta`.`trigger`",
            "recetali_receta.notification_old",
            "recetali_receta.medical_provider"
    );

    private final List<MigrationOrchestrator> level0Orchestrators;
    private final TableTruncator tableTruncator;

    public RecetaLevel0BatchUseCase(List<MigrationOrchestrator> level0Orchestrators,
                                     TableTruncator tableTruncator) {
        this.level0Orchestrators = level0Orchestrators;
        this.tableTruncator = tableTruncator;
    }

    /**
     * Execute all Level 0 migrations sequentially and collect results.
     *
     * @param request migration parameters (dryRun, batchSize, duplicatePolicy, truncateFirst)
     * @return list of per-table migration summaries
     */
    public Mono<List<MigrationSummary>> execute(MigrationRequest request) {
        log.info("Starting receta Level 0 batch migration — {} tables, dryRun={}, truncateFirst={}",
                level0Orchestrators.size(), request.dryRun(), request.truncateFirst());

        Mono<Void> preStep = Mono.empty();
        if (request.truncateFirst() && !request.dryRun()) {
            preStep = tableTruncator.truncateAll(LEVEL_0_TABLES)
                    .doOnSuccess(v -> log.info("All Level 0 target tables truncated"));
        }

        return preStep.then(
                Flux.fromIterable(level0Orchestrators)
                        .concatMap(orchestrator -> {
                            log.info("  → Running migration: {}", orchestrator.migrationName());
                            return orchestrator.execute(request);
                        })
                        .collectList()
                        .doOnSuccess(summaries -> {
                            long totalInserted = summaries.stream()
                                    .mapToLong(MigrationSummary::totalInserted).sum();
                            long totalFailed = summaries.stream()
                                    .mapToLong(MigrationSummary::totalFailed).sum();
                            log.info("Receta Level 0 batch completed — tables={}, totalInserted={}, totalFailed={}",
                                    summaries.size(), totalInserted, totalFailed);
                        })
        );
    }
}
