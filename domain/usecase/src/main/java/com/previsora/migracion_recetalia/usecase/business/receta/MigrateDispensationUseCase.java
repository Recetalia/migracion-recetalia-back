package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaDispensation;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyDispenserIdResolver;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyEmailResolver;
import com.previsora.migracion_recetalia.usecase.gateway.PrescriptionIdResolver;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Migrates {@code recetali_receta.dispensation} from prod to dev, remapping three foreign keys.
 * <p>
 * This migration is strictly READ-ONLY against prod — no prod record is ever modified.
 * <p>
 * Remapping strategy per record:
 * <ol>
 *   <li>prescriptionId: prescription IDs are preserved by the prescription migration,
 *       so verify the dev prescription exists by the same id.</li>
 *   <li>pharmacyId: look up prod pharmacy by id → get email → find dev pharmacy by email
 *       → use dev pharmacyId.</li>
 *   <li>dispensedById: pharmacy_dispenser IDs are preserved by the dispenser migration,
 *       so verify the dev dispenser exists by id AND is associated with the already-mapped
 *       dev pharmacyId.</li>
 * </ol>
 */
public class MigrateDispensationUseCase implements MigrationOrchestrator {

    public static final String MIGRATION_NAME = "receta-dispensation";

    private static final Logger log = LoggerFactory.getLogger(MigrateDispensationUseCase.class);

    private final SourceReader<RecetaDispensation> dispensationReader;
    private final SourceReader<RecetaPharmacy> pharmacyReader;
    private final PrescriptionIdResolver prescriptionIdResolver;
    private final PharmacyEmailResolver pharmacyEmailResolver;
    private final PharmacyDispenserIdResolver pharmacyDispenserIdResolver;
    private final TargetWriter<RecetaDispensation> dispensationWriter;

    public MigrateDispensationUseCase(
            SourceReader<RecetaDispensation> dispensationReader,
            SourceReader<RecetaPharmacy> pharmacyReader,
            PrescriptionIdResolver prescriptionIdResolver,
            PharmacyEmailResolver pharmacyEmailResolver,
            PharmacyDispenserIdResolver pharmacyDispenserIdResolver,
            TargetWriter<RecetaDispensation> dispensationWriter) {
        this.dispensationReader = dispensationReader;
        this.pharmacyReader = pharmacyReader;
        this.prescriptionIdResolver = prescriptionIdResolver;
        this.pharmacyEmailResolver = pharmacyEmailResolver;
        this.pharmacyDispenserIdResolver = pharmacyDispenserIdResolver;
        this.dispensationWriter = dispensationWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    public Mono<MigrationSummary> execute(MigrationRequest request) {
        MigrationAccumulator acc = new MigrationAccumulator(migrationName(), request.dryRun());

        log.info("[{}] Starting migration — dryRun={}, batchSize={}, policy={}",
                migrationName(), request.dryRun(), request.batchSize(), request.duplicatePolicy());

        return dispensationReader.readAll(request.batchSize())
                .doOnNext(record -> acc.incrementRead())
                .concatMap(record -> resolveAndProcess(record, request, acc))
                .then(Mono.fromSupplier(acc::toSummary))
                .doOnSuccess(summary -> log.info(
                        "[{}] Migration finished — read={}, inserted={}, skipped={}, failed={}, dryRun={}, duration={}ms",
                        migrationName(), summary.totalRead(), summary.totalInserted(),
                        summary.totalSkipped(), summary.totalFailed(), summary.dryRun(), summary.durationMs()
                ))
                .onErrorResume(ex -> {
                    log.error("[{}] Migration failed with exception: {}", migrationName(), ex.getMessage(), ex);
                    acc.addError(new MigrationError("GLOBAL", ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.just(acc.toSummary());
                });
    }

    private Mono<Void> resolveAndProcess(RecetaDispensation record,
                                          MigrationRequest request,
                                          MigrationAccumulator acc) {
        return resolveDevPrescriptionId(record.prescriptionId())
                .zipWith(resolveDevPharmacyId(record.pharmacyId()))
                .flatMap(ids -> {
                    String devPrescriptionId = ids.getT1();
                    String devPharmacyId = ids.getT2();
                    return pharmacyDispenserIdResolver
                            .resolveDevDispenserId(record.dispensedById(), devPharmacyId)
                            .switchIfEmpty(Mono.error(new RuntimeException(
                                    "Dev pharmacy_dispenser not found for id " + record.dispensedById()
                                            + " with pharmacyId " + devPharmacyId)))
                            .map(devDispenserId -> record.withRemappedIds(
                                    devPrescriptionId, devPharmacyId, devDispenserId));
                })
                .flatMap(remapped -> processRecord(remapped, request, acc))
                .onErrorResume(ex -> {
                    log.warn("[{}] Failed to resolve FKs for dispensation {}: {}",
                            migrationName(), record.id(), ex.getMessage());
                    acc.addError(new MigrationError(
                            record.id(), ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.empty();
                });
    }

    private Mono<String> resolveDevPrescriptionId(String prodPrescriptionId) {
        return prescriptionIdResolver.resolveDevPrescriptionId(prodPrescriptionId)
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Dev prescription not found for id " + prodPrescriptionId)));
    }

    private Mono<String> resolveDevPharmacyId(String prodPharmacyId) {
        return pharmacyReader.readById(prodPharmacyId, 1)
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Prod pharmacy not found for id " + prodPharmacyId)))
                .map(RecetaPharmacy::email)
                .flatMap(email -> pharmacyEmailResolver.findPharmacyIdByEmail(email)
                        .switchIfEmpty(Mono.error(new RuntimeException(
                                "Dev pharmacy not found for email " + email))));
    }

    private Mono<Void> processRecord(RecetaDispensation record,
                                      MigrationRequest request,
                                      MigrationAccumulator acc) {
        return dispensationWriter.exists(record)
                .flatMap(exists -> {
                    if (exists) {
                        if (request.duplicatePolicy() == DuplicatePolicy.SKIP_EXISTING) {
                            acc.incrementSkipped();
                            return Mono.<Void>empty();
                        }
                    }
                    if (request.dryRun()) {
                        acc.incrementInserted();
                        return Mono.<Void>empty();
                    }
                    return dispensationWriter.insert(record)
                            .doOnSuccess(v -> {
                                log.info("[{}] Inserted id={}", migrationName(), record.id());
                                acc.incrementInserted();
                            })
                            .onErrorResume(ex -> {
                                log.warn("[{}] Failed to insert record {}: {}",
                                        migrationName(), record.id(), ex.getMessage());
                                acc.addError(new MigrationError(
                                        record.id(),
                                        ex.getMessage(),
                                        ex.getClass().getSimpleName()
                                ));
                                return Mono.empty();
                            });
                });
    }
}
