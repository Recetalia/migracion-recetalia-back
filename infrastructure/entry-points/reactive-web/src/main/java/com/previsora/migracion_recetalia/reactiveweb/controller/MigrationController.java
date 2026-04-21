package com.previsora.migracion_recetalia.reactiveweb.controller;

import com.previsora.migracion_recetalia.model.dto.JobStartedResponse;
import com.previsora.migracion_recetalia.model.dto.JobState;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;
import com.previsora.migracion_recetalia.reactiveweb.service.MigrationJobRegistry;
import com.previsora.migracion_recetalia.usecase.business.MigrateManagementUserUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateMedicBatchUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigratePharmacyBatchUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.RecetaLevel0BatchUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * REST controller for triggering and monitoring data migrations.
 * <p>
 * All migration endpoints submit work to {@link MigrationJobRegistry} and return
 * {@code 202 Accepted} with a {@code jobId} immediately. Poll {@code GET /jobs/{jobId}}
 * for status and final summaries.
 */
@RestController
@RequestMapping("/v1/migrations")
@Tag(name = "Migrations", description = "Data migration endpoints (async)")
public class MigrationController {

    private static final Logger log = LoggerFactory.getLogger(MigrationController.class);

    private final Map<String, MigrationOrchestrator> orchestrators = new ConcurrentHashMap<>();
    private final RecetaLevel0BatchUseCase recetaLevel0BatchUseCase;
    private final MigrateManagementUserUseCase migrateManagementUserUseCase;
    private final MigrateMedicBatchUseCase migrateMedicBatchUseCase;
    private final MigratePharmacyBatchUseCase migratePharmacyBatchUseCase;
    private final MigrationJobRegistry jobRegistry;

    public MigrationController(List<MigrationOrchestrator> orchestratorList,
                               RecetaLevel0BatchUseCase recetaLevel0BatchUseCase,
                               MigrateManagementUserUseCase migrateManagementUserUseCase,
                               MigrateMedicBatchUseCase migrateMedicBatchUseCase,
                               MigratePharmacyBatchUseCase migratePharmacyBatchUseCase,
                               MigrationJobRegistry jobRegistry) {
        this.recetaLevel0BatchUseCase = recetaLevel0BatchUseCase;
        this.migrateManagementUserUseCase = migrateManagementUserUseCase;
        this.migrateMedicBatchUseCase = migrateMedicBatchUseCase;
        this.migratePharmacyBatchUseCase = migratePharmacyBatchUseCase;
        this.jobRegistry = jobRegistry;
        orchestratorList.forEach(o -> {
            log.info("Registered migration orchestrator: {}", o.migrationName());
            orchestrators.put(o.migrationName(), o);
        });
    }

    // ─── Single-table migrations ───

    @PostMapping("/receta-user")
    @Operation(summary = "Migrate recetali_receta.user from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateRecetaUser(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-user", resolveRequest(request));
    }

    @PostMapping("/security-users")
    @Operation(summary = "Migrate users from prod.recetali_receta.user to dev.securitydb.users")
    public ResponseEntity<JobStartedResponse> migrateSecurityUsers(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("security-users", resolveRequest(request));
    }

    @PostMapping("/patients")
    @Operation(summary = "Migrate recetali_receta.patient from prod to dev using document as duplicate key")
    public ResponseEntity<JobStartedResponse> migratePatients(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-patient", resolveRequest(request));
    }

    @PostMapping("/medical-providers")
    @Operation(summary = "Migrate recetali_receta.medical_provider from prod to dev using rut as duplicate key")
    public ResponseEntity<JobStartedResponse> migrateMedicalProviders(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-medical-provider", resolveRequest(request));
    }

    @PostMapping("/users/{userId}/management")
    @Operation(summary = "Migrate one prod.recetali_receta.user into dev and ensure ROLE_MANAGEMENT")
    public ResponseEntity<JobStartedResponse> migrateManagementUser(
            @PathVariable("userId") String userId,
            @RequestBody(required = false) MigrationRequest request) {
        MigrationRequest scoped = resolveRequest(request).withSourceRecordId(userId);
        String name = "management-user:" + userId;
        String jobId = jobRegistry.submitBatch(name, () -> migrateManagementUserUseCase.execute(scoped));
        return accepted(jobId, name);
    }

    // ─── Medic batch migration ───

    @PostMapping("/medics")
    @Operation(summary = "Migrate all medics from prod to dev (recetali_receta.medic + securitydb.users + ROLE_MEDIC)")
    public ResponseEntity<JobStartedResponse> migrateMedics(@RequestBody(required = false) MigrationRequest request) {
        MigrationRequest req = resolveRequest(request);
        String jobId = jobRegistry.submitBatch("medics", () -> migrateMedicBatchUseCase.execute(req));
        return accepted(jobId, "medics");
    }

    // ─── Pharmacy batch migration ───

    @PostMapping("/pharmacys")
    @Operation(summary = "Migrate all pharmacies from prod to dev (recetali_receta.pharmacy + securitydb.users + ROLE_PHARMACY)")
    public ResponseEntity<JobStartedResponse> migratePharmacys(@RequestBody(required = false) MigrationRequest request) {
        MigrationRequest req = resolveRequest(request);
        String jobId = jobRegistry.submitBatch("pharmacys", () -> migratePharmacyBatchUseCase.execute(req));
        return accepted(jobId, "pharmacys");
    }

    // ─── DNMA base tables ───

    @PostMapping("/dnma-ffa")
    @Operation(summary = "Migrate recetali_dnma.ffa from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaFfa(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-ffa", resolveRequest(request));
    }

    @PostMapping("/dnma-laboratorio")
    @Operation(summary = "Migrate recetali_dnma.laboratorio from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaLaboratorio(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-laboratorio", resolveRequest(request));
    }

    @PostMapping("/dnma-sustancia")
    @Operation(summary = "Migrate recetali_dnma.sustancia from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaSustancia(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-sustancia", resolveRequest(request));
    }

    @PostMapping("/dnma-tf")
    @Operation(summary = "Migrate recetali_dnma.tf from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaTf(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-tf", resolveRequest(request));
    }

    @PostMapping("/dnma-unidad")
    @Operation(summary = "Migrate recetali_dnma.unidad from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaUnidad(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-unidad", resolveRequest(request));
    }

    @PostMapping("/dnma-via-admin")
    @Operation(summary = "Migrate recetali_dnma.via_admin from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateDnmaViaAdmin(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("dnma-via-admin", resolveRequest(request));
    }

    // ─── recetali_receta Level 1 (FK dependencies) ───

    @PostMapping("/laboratories")
    @Operation(summary = "Migrate recetali_receta.laboratory from prod to dev (depends on file)")
    public ResponseEntity<JobStartedResponse> migrateLaboratories(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-laboratory", resolveRequest(request));
    }

    @PostMapping("/notifications")
    @Operation(summary = "Migrate recetali_receta.notification from prod to dev (depends on notification_type)")
    public ResponseEntity<JobStartedResponse> migrateNotifications(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-notification", resolveRequest(request));
    }

    @PostMapping("/notification-templates")
    @Operation(summary = "Migrate recetali_receta.notification_template from prod to dev (depends on notification_type)")
    public ResponseEntity<JobStartedResponse> migrateNotificationTemplates(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-notification-template", resolveRequest(request));
    }

    @PostMapping("/logs")
    @Operation(summary = "Migrate recetali_receta.log from prod to dev (depends on user)")
    public ResponseEntity<JobStartedResponse> migrateLogs(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-log", resolveRequest(request));
    }

    @PostMapping("/pharmacy-dispensers")
    @Operation(summary = "Migrate recetali_receta.pharmacy_dispenser from prod to dev (remaps pharmacyId via email)")
    public ResponseEntity<JobStartedResponse> migratePharmacyDispensers(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-pharmacy-dispenser", resolveRequest(request));
    }

    @PostMapping("/prescriptions")
    @Operation(summary = "Migrate recetali_receta.prescription from prod to dev (remaps medicId via email, patientId via document)")
    public ResponseEntity<JobStartedResponse> migratePrescriptions(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-prescription", resolveRequest(request));
    }

    @PostMapping("/dispensations")
    @Operation(summary = "Migrate recetali_receta.dispensation from prod to dev (verifies prescriptionId, remaps pharmacyId via email, verifies dispensedById with mapped pharmacy)")
    public ResponseEntity<JobStartedResponse> migrateDispensations(@RequestBody(required = false) MigrationRequest request) {
        return submitSingle("receta-dispensation", resolveRequest(request));
    }

    // ─── Batch: recetali_receta Level 0 ───

    @PostMapping("/batch/receta-level-0")
    @Operation(summary = "Migrate all Level 0 recetali_receta tables (no FK dependencies) from prod to dev")
    public ResponseEntity<JobStartedResponse> migrateRecetaLevel0(@RequestBody(required = false) MigrationRequest request) {
        MigrationRequest req = resolveRequest(request);
        String jobId = jobRegistry.submitBatch("receta-level-0", () -> recetaLevel0BatchUseCase.execute(req));
        return accepted(jobId, "receta-level-0");
    }

    // ─── Generic ───

    /**
     * Generic endpoint: trigger any registered migration by name.
     */
    @PostMapping("/{migrationName}")
    @Operation(summary = "Trigger a migration by its registered name")
    public ResponseEntity<JobStartedResponse> migrateByName(
            @PathVariable String migrationName,
            @RequestBody(required = false) MigrationRequest request) {
        return submitSingle(migrationName, resolveRequest(request));
    }

    // ─── Job status endpoints ───

    @GetMapping("/jobs/{jobId}")
    @Operation(summary = "Get status of a submitted migration job")
    public Mono<ResponseEntity<JobState>> getJob(@PathVariable String jobId) {
        JobState state = jobRegistry.get(jobId);
        if (state == null) {
            return Mono.just(ResponseEntity.notFound().build());
        }
        return Mono.just(ResponseEntity.ok(state));
    }

    @GetMapping("/jobs")
    @Operation(summary = "List all migration jobs (most recent first)")
    public Mono<List<JobState>> listJobs() {
        return Mono.just(jobRegistry.list());
    }

    @GetMapping("/status")
    @Operation(summary = "List all registered migrations")
    public Mono<Map<String, String>> status() {
        Map<String, String> status = new ConcurrentHashMap<>();
        orchestrators.keySet().forEach(name -> status.put(name, "READY"));
        return Mono.just(status);
    }

    // ─── Helpers ───

    private ResponseEntity<JobStartedResponse> submitSingle(String name, MigrationRequest request) {
        MigrationOrchestrator orchestrator = orchestrators.get(name);
        if (orchestrator == null) {
            return ResponseEntity.badRequest().body(JobStartedResponse.of(
                    null, "No orchestrator registered for migration: " + name, Instant.now()));
        }
        String jobId = jobRegistry.submitSingle(name, () -> orchestrator.execute(request));
        return accepted(jobId, name);
    }

    private ResponseEntity<JobStartedResponse> accepted(String jobId, String name) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(JobStartedResponse.of(jobId, name, Instant.now()));
    }

    private MigrationRequest resolveRequest(MigrationRequest request) {
        if (request == null) {
            return MigrationRequest.defaults();
        }
        return new MigrationRequest(
                request.dryRun(),
                request.batchSize() > 0 ? request.batchSize() : 500,
                request.duplicatePolicy() != null ? request.duplicatePolicy() : DuplicatePolicy.SKIP_EXISTING,
                request.truncateFirst(),
                request.sourceRecordId(),
                request.insertDelayMs() >= 0 ? request.insertDelayMs() : 500L
        );
    }
}
