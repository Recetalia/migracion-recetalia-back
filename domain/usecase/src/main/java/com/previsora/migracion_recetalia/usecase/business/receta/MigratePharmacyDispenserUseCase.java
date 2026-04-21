package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacyDispenser;
import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyEmailResolver;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Migrates {@code recetali_receta.pharmacy_dispenser} from prod to dev,
 * remapping the {@code pharmacyId} foreign key.
 * <p>
 * The remapping strategy:
 * <ol>
 *   <li>Look up the prod pharmacy by its ID to obtain the email</li>
 *   <li>Use the email (business key) to find the matching pharmacy in dev</li>
 *   <li>Insert the dispenser record with the dev pharmacyId</li>
 * </ol>
 */
public class MigratePharmacyDispenserUseCase implements MigrationOrchestrator {

    public static final String MIGRATION_NAME = "receta-pharmacy-dispenser";

    private static final Logger log = LoggerFactory.getLogger(MigratePharmacyDispenserUseCase.class);

    private final SourceReader<RecetaPharmacyDispenser> dispenserReader;
    private final SourceReader<RecetaPharmacy> pharmacyReader;
    private final PharmacyEmailResolver pharmacyEmailResolver;
    private final TargetWriter<RecetaPharmacyDispenser> dispenserWriter;

    public MigratePharmacyDispenserUseCase(
            SourceReader<RecetaPharmacyDispenser> dispenserReader,
            SourceReader<RecetaPharmacy> pharmacyReader,
            PharmacyEmailResolver pharmacyEmailResolver,
            TargetWriter<RecetaPharmacyDispenser> dispenserWriter) {
        this.dispenserReader = dispenserReader;
        this.pharmacyReader = pharmacyReader;
        this.pharmacyEmailResolver = pharmacyEmailResolver;
        this.dispenserWriter = dispenserWriter;
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

        return dispenserReader.readAll(request.batchSize())
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

    private Mono<Void> resolveAndProcess(RecetaPharmacyDispenser record,
                                          MigrationRequest request,
                                          MigrationAccumulator acc) {
        return pharmacyReader.readById(record.pharmacyId(), 1)
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Prod pharmacy not found for id " + record.pharmacyId())))
                .map(RecetaPharmacy::email)
                .flatMap(email -> pharmacyEmailResolver.findPharmacyIdByEmail(email)
                        .switchIfEmpty(Mono.error(new RuntimeException(
                                "Dev pharmacy not found for email " + email))))
                .flatMap(devPharmacyId -> {
                    RecetaPharmacyDispenser remapped = record.withPharmacyId(devPharmacyId);
                    return processRecord(remapped, request, acc);
                })
                .onErrorResume(ex -> {
                    log.warn("[{}] Failed to resolve pharmacyId for dispenser {}: {}",
                            migrationName(), record.id(), ex.getMessage());
                    acc.addError(new MigrationError(
                            record.id(), ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.empty();
                });
    }

    private Mono<Void> processRecord(RecetaPharmacyDispenser record,
                                      MigrationRequest request,
                                      MigrationAccumulator acc) {
        return dispenserWriter.exists(record)
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
                    return dispenserWriter.insert(record)
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
