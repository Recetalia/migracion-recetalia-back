package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPrescription;
import com.previsora.migracion_recetalia.model.dto.MigrationError;
import com.previsora.migracion_recetalia.model.dto.MigrationRequest;
import com.previsora.migracion_recetalia.model.dto.MigrationSummary;
import com.previsora.migracion_recetalia.model.enums.DuplicatePolicy;
import com.previsora.migracion_recetalia.model.generic.MigrationAccumulator;
import com.previsora.migracion_recetalia.usecase.gateway.MedicEmailResolver;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.PatientDocumentResolver;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Migrates {@code recetali_receta.prescription} from prod to dev,
 * remapping both {@code medicId} and {@code patientId} foreign keys.
 * <p>
 * The remapping strategy:
 * <ol>
 *   <li>Look up the prod medic by ID to obtain the email (business key)</li>
 *   <li>Look up the prod patient by ID to obtain the document (business key)</li>
 *   <li>Use the email to find the matching medic in dev → get dev medicId</li>
 *   <li>Use the document to find the matching patient in dev → get dev patientId</li>
 *   <li>Insert the prescription with the remapped IDs</li>
 * </ol>
 */
public class MigratePrescriptionUseCase implements MigrationOrchestrator {

    public static final String MIGRATION_NAME = "receta-prescription";

    private static final Logger log = LoggerFactory.getLogger(MigratePrescriptionUseCase.class);

    private final SourceReader<RecetaPrescription> prescriptionReader;
    private final SourceReader<RecetaMedic> medicReader;
    private final SourceReader<RecetaPatient> patientReader;
    private final MedicEmailResolver medicEmailResolver;
    private final PatientDocumentResolver patientDocumentResolver;
    private final TargetWriter<RecetaPrescription> prescriptionWriter;

    public MigratePrescriptionUseCase(
            SourceReader<RecetaPrescription> prescriptionReader,
            SourceReader<RecetaMedic> medicReader,
            SourceReader<RecetaPatient> patientReader,
            MedicEmailResolver medicEmailResolver,
            PatientDocumentResolver patientDocumentResolver,
            TargetWriter<RecetaPrescription> prescriptionWriter) {
        this.prescriptionReader = prescriptionReader;
        this.medicReader = medicReader;
        this.patientReader = patientReader;
        this.medicEmailResolver = medicEmailResolver;
        this.patientDocumentResolver = patientDocumentResolver;
        this.prescriptionWriter = prescriptionWriter;
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

        return prescriptionReader.readAll(request.batchSize())
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

    private Mono<Void> resolveAndProcess(RecetaPrescription record,
                                          MigrationRequest request,
                                          MigrationAccumulator acc) {
        Mono<String> devMedicId = resolveMedicId(record.medicId());
        Mono<String> devPatientId = resolvePatientId(record.patientId());

        return Mono.zip(devMedicId, devPatientId)
                .flatMap(ids -> {
                    RecetaPrescription remapped = record.withRemappedIds(ids.getT1(), ids.getT2());
                    return processRecord(remapped, request, acc);
                })
                .onErrorResume(ex -> {
                    log.warn("[{}] Failed to resolve IDs for prescription {}: {}",
                            migrationName(), record.id(), ex.getMessage());
                    acc.addError(new MigrationError(
                            record.id(), ex.getMessage(), ex.getClass().getSimpleName()));
                    return Mono.empty();
                });
    }

    private Mono<String> resolveMedicId(String prodMedicId) {
        return medicReader.readById(prodMedicId, 1)
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Prod medic not found for id " + prodMedicId)))
                .map(RecetaMedic::email)
                .flatMap(email -> medicEmailResolver.findMedicIdByEmail(email)
                        .switchIfEmpty(Mono.error(new RuntimeException(
                                "Dev medic not found for email " + email))));
    }

    private Mono<String> resolvePatientId(String prodPatientId) {
        return patientReader.readById(prodPatientId, 1)
                .singleOrEmpty()
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Prod patient not found for id " + prodPatientId)))
                .map(RecetaPatient::document)
                .flatMap(document -> patientDocumentResolver.findPatientIdByDocument(document)
                        .switchIfEmpty(Mono.error(new RuntimeException(
                                "Dev patient not found for document " + document))));
    }

    private Mono<Void> processRecord(RecetaPrescription record,
                                      MigrationRequest request,
                                      MigrationAccumulator acc) {
        return prescriptionWriter.exists(record)
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
                    return prescriptionWriter.insert(record)
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
