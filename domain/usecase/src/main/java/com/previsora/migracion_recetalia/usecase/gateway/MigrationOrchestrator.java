package com.previsora.migracion_recetalia.usecase.gateway;

import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import reactor.core.publisher.Mono;

/**
 * Input port for executing a named migration.
 * <p>
 * Each migration registers itself with a unique {@link #migrationName()}.
 * The MigrationController discovers all registered orchestrators and dispatches by name.
 */
public interface MigrationOrchestrator {

    /**
     * Unique name for this migration (e.g. "receta-user", "security-users").
     */
    String migrationName();

    /**
     * Execute the migration with the given request parameters.
     *
     * @param request migration parameters (dryRun, batchSize, duplicatePolicy)
     * @return summary of the migration execution
     */
    Mono<MigrationSummary> execute(MigrationRequest request);
}
