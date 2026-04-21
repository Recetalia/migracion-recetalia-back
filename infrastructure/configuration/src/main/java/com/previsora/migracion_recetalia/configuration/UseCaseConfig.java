package com.previsora.migracion_recetalia.configuration;

import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.model.domain.dnma.*;
import com.previsora.migracion_recetalia.model.domain.receta.*;
import com.previsora.migracion_recetalia.usecase.business.AssignSecurityRoleUseCase;
import com.previsora.migracion_recetalia.usecase.business.MigrateManagementUserUseCase;
import com.previsora.migracion_recetalia.usecase.business.MigrateRecetaUserUseCase;
import com.previsora.migracion_recetalia.usecase.business.MigrateSecurityUsersUseCase;
import com.previsora.migracion_recetalia.usecase.business.dnma.*;
import com.previsora.migracion_recetalia.usecase.business.receta.*;
import com.previsora.migracion_recetalia.usecase.business.receta.AssignMedicRolesUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.AssignPharmacyRolesUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateMedicBatchUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigratePharmacyBatchUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateRecetaMedicUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateRecetaPharmacyUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateSecurityMedicUseCase;
import com.previsora.migracion_recetalia.usecase.business.receta.MigrateSecurityPharmacyUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.MedicEmailResolver;
import com.previsora.migracion_recetalia.usecase.gateway.MigrationOrchestrator;
import com.previsora.migracion_recetalia.usecase.gateway.PatientDocumentResolver;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyDispenserIdResolver;
import com.previsora.migracion_recetalia.usecase.gateway.PharmacyEmailResolver;
import com.previsora.migracion_recetalia.usecase.gateway.PrescriptionIdResolver;
import com.previsora.migracion_recetalia.usecase.gateway.SecurityUserRoleGateway;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TableTruncator;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Explicit wiring of use case beans.
 * <p>
 * Each use case is registered as a {@link MigrationOrchestrator} bean,
 * which is auto-discovered by the MigrationController.
 */
@Configuration
public class UseCaseConfig {

    // ─── recetali_receta (existing) ───

    @Bean
    public MigrationOrchestrator migrateRecetaUserUseCase(
            @Qualifier("prodRecetaUserReader") SourceReader<RecetaUser> sourceReader,
            @Qualifier("devRecetaUserWriter") TargetWriter<RecetaUser> targetWriter) {
        return new MigrateRecetaUserUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateSecurityUsersUseCase(
            @Qualifier("prodSecurityUserReader") SourceReader<SecurityUser> sourceReader,
            @Qualifier("devSecurityUserWriter") TargetWriter<SecurityUser> targetWriter) {
        return new MigrateSecurityUsersUseCase(sourceReader, targetWriter);
    }

    @Bean
    public AssignSecurityRoleUseCase assignSecurityRoleUseCase(
            SecurityUserRoleGateway securityUserRoleGateway) {
        return new AssignSecurityRoleUseCase(securityUserRoleGateway);
    }

    @Bean
    public MigrateManagementUserUseCase migrateManagementUserUseCase(
            @Qualifier("prodRecetaUserReader") SourceReader<RecetaUser> recetaUserSourceReader,
            @Qualifier("migrateRecetaUserUseCase") MigrationOrchestrator recetaUserMigration,
            @Qualifier("migrateSecurityUsersUseCase") MigrationOrchestrator securityUsersMigration,
            AssignSecurityRoleUseCase assignSecurityRoleUseCase) {
        return new MigrateManagementUserUseCase(
                recetaUserSourceReader,
                recetaUserMigration,
                securityUsersMigration,
                assignSecurityRoleUseCase
        );
    }

    // ─── recetali_dnma (base tables — no FK dependencies) ───

    @Bean
    public MigrationOrchestrator migrateDnmaFfaUseCase(
            @Qualifier("prodDnmaFfaReader") SourceReader<DnmaFfa> sourceReader,
            @Qualifier("devDnmaFfaWriter") TargetWriter<DnmaFfa> targetWriter) {
        return new MigrateDnmaFfaUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDnmaLaboratorioUseCase(
            @Qualifier("prodDnmaLaboratorioReader") SourceReader<DnmaLaboratorio> sourceReader,
            @Qualifier("devDnmaLaboratorioWriter") TargetWriter<DnmaLaboratorio> targetWriter) {
        return new MigrateDnmaLaboratorioUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDnmaSustanciaUseCase(
            @Qualifier("prodDnmaSustanciaReader") SourceReader<DnmaSustancia> sourceReader,
            @Qualifier("devDnmaSustanciaWriter") TargetWriter<DnmaSustancia> targetWriter) {
        return new MigrateDnmaSustanciaUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDnmaTfUseCase(
            @Qualifier("prodDnmaTfReader") SourceReader<DnmaTf> sourceReader,
            @Qualifier("devDnmaTfWriter") TargetWriter<DnmaTf> targetWriter) {
        return new MigrateDnmaTfUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDnmaUnidadUseCase(
            @Qualifier("prodDnmaUnidadReader") SourceReader<DnmaUnidad> sourceReader,
            @Qualifier("devDnmaUnidadWriter") TargetWriter<DnmaUnidad> targetWriter) {
        return new MigrateDnmaUnidadUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDnmaViaAdminUseCase(
            @Qualifier("prodDnmaViaAdminReader") SourceReader<DnmaViaAdmin> sourceReader,
            @Qualifier("devDnmaViaAdminWriter") TargetWriter<DnmaViaAdmin> targetWriter) {
        return new MigrateDnmaViaAdminUseCase(sourceReader, targetWriter);
    }

    // ─── recetali_receta Level 0 (no FK dependencies) ───

    @Bean
    public MigrationOrchestrator migrateCountryUseCase(
            @Qualifier("prodCountryReader") SourceReader<RecetaCountry> sourceReader,
            @Qualifier("devCountryWriter") TargetWriter<RecetaCountry> targetWriter) {
        return new MigrateCountryUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateRegionUseCase(
            @Qualifier("prodRegionReader") SourceReader<RecetaRegion> sourceReader,
            @Qualifier("devRegionWriter") TargetWriter<RecetaRegion> targetWriter) {
        return new MigrateRegionUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateLocalityUseCase(
            @Qualifier("prodLocalityReader") SourceReader<RecetaLocality> sourceReader,
            @Qualifier("devLocalityWriter") TargetWriter<RecetaLocality> targetWriter) {
        return new MigrateLocalityUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateRecetaFileUseCase(
            @Qualifier("prodRecetaFileReader") SourceReader<RecetaFile> sourceReader,
            @Qualifier("devRecetaFileWriter") TargetWriter<RecetaFile> targetWriter) {
        return new RecetaFileMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateMedicEspecialityUseCase(
            @Qualifier("prodRecetaMedicEspecialityReader") SourceReader<RecetaMedicEspeciality> sourceReader,
            @Qualifier("devRecetaMedicEspecialityWriter") TargetWriter<RecetaMedicEspeciality> targetWriter) {
        return new RecetaMedicEspecialityMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateMedicalProviderTypeUseCase(
            @Qualifier("prodRecetaMedicalProviderTypeReader") SourceReader<RecetaMedicalProviderType> sourceReader,
            @Qualifier("devRecetaMedicalProviderTypeWriter") TargetWriter<RecetaMedicalProviderType> targetWriter) {
        return new RecetaMedicalProviderTypeMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateFranchiseUseCase(
            @Qualifier("prodFranchiseReader") SourceReader<RecetaFranchise> sourceReader,
            @Qualifier("devFranchiseWriter") TargetWriter<RecetaFranchise> targetWriter) {
        return new RecetaFranchiseMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateNotificationTypeUseCase(
            @Qualifier("prodNotificationTypeReader") SourceReader<RecetaNotificationType> sourceReader,
            @Qualifier("devNotificationTypeWriter") TargetWriter<RecetaNotificationType> targetWriter) {
        return new RecetaNotificationTypeMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDrougUseCase(
            @Qualifier("prodDrougReader") SourceReader<RecetaDroug> sourceReader,
            @Qualifier("devDrougWriter") TargetWriter<RecetaDroug> targetWriter) {
        return new RecetaDrougMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateVademecumUseCase(
            @Qualifier("prodVademecumReader") SourceReader<RecetaVademecum> sourceReader,
            @Qualifier("devVademecumWriter") TargetWriter<RecetaVademecum> targetWriter) {
        return new RecetaVademecumMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateVersionUseCase(
            @Qualifier("prodVersionReader") SourceReader<RecetaVersion> sourceReader,
            @Qualifier("devVersionWriter") TargetWriter<RecetaVersion> targetWriter) {
        return new RecetaVersionMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateTriggerUseCase(
            @Qualifier("prodRecetaTriggerReader") SourceReader<RecetaTrigger> sourceReader,
            @Qualifier("devRecetaTriggerWriter") TargetWriter<RecetaTrigger> targetWriter) {
        return new RecetaTriggerMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateNotificationOldUseCase(
            @Qualifier("prodRecetaNotificationOldReader") SourceReader<RecetaNotificationOld> sourceReader,
            @Qualifier("devRecetaNotificationOldWriter") TargetWriter<RecetaNotificationOld> targetWriter) {
        return new RecetaNotificationOldMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateMedicalProviderUseCase(
            @Qualifier("prodRecetaMedicalProviderReader") SourceReader<RecetaMedicalProvider> sourceReader,
            @Qualifier("devRecetaMedicalProviderWriter") TargetWriter<RecetaMedicalProvider> targetWriter) {
        return new RecetaMedicalProviderMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migratePatientUseCase(
            @Qualifier("prodRecetaPatientReader") SourceReader<RecetaPatient> sourceReader,
            @Qualifier("devRecetaPatientWriter") TargetWriter<RecetaPatient> targetWriter) {
        return new MigratePatientUseCase(sourceReader, targetWriter);
    }

    // ─── recetali_receta.medic → dev.recetali_receta.medic + securitydb ───

    @Bean
    public MigrationOrchestrator migrateRecetaMedicUseCase(
            @Qualifier("prodRecetaMedicReader") SourceReader<RecetaMedic> sourceReader,
            @Qualifier("devRecetaMedicWriter") TargetWriter<RecetaMedic> targetWriter) {
        return new MigrateRecetaMedicUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateSecurityMedicUseCase(
            @Qualifier("prodSecurityMedicReader") SourceReader<SecurityUser> sourceReader,
            @Qualifier("devSecurityUserWriter") TargetWriter<SecurityUser> targetWriter) {
        return new MigrateSecurityMedicUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator assignMedicRolesUseCase(
            @Qualifier("prodRecetaMedicReader") SourceReader<RecetaMedic> sourceReader,
            SecurityUserRoleGateway securityUserRoleGateway) {
        return new AssignMedicRolesUseCase(sourceReader, securityUserRoleGateway);
    }

    @Bean
    public MigrateMedicBatchUseCase migrateMedicBatchUseCase(
            @Qualifier("migrateRecetaMedicUseCase") MigrationOrchestrator recetaMedicMigration,
            @Qualifier("migrateSecurityMedicUseCase") MigrationOrchestrator securityMedicMigration,
            @Qualifier("assignMedicRolesUseCase") MigrationOrchestrator assignMedicRoles) {
        return new MigrateMedicBatchUseCase(recetaMedicMigration, securityMedicMigration, assignMedicRoles);
    }

    // ─── recetali_receta.pharmacy → dev.recetali_receta.pharmacy + securitydb ───

    @Bean
    public MigrationOrchestrator migrateRecetaPharmacyUseCase(
            @Qualifier("prodRecetaPharmacyReader") SourceReader<RecetaPharmacy> sourceReader,
            @Qualifier("devRecetaPharmacyWriter") TargetWriter<RecetaPharmacy> targetWriter) {
        return new MigrateRecetaPharmacyUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateSecurityPharmacyUseCase(
            @Qualifier("prodSecurityPharmacyReader") SourceReader<SecurityUser> sourceReader,
            @Qualifier("devSecurityUserWriter") TargetWriter<SecurityUser> targetWriter) {
        return new MigrateSecurityPharmacyUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator assignPharmacyRolesUseCase(
            @Qualifier("prodRecetaPharmacyReader") SourceReader<RecetaPharmacy> sourceReader,
            SecurityUserRoleGateway securityUserRoleGateway) {
        return new AssignPharmacyRolesUseCase(sourceReader, securityUserRoleGateway);
    }

    @Bean
    public MigratePharmacyBatchUseCase migratePharmacyBatchUseCase(
            @Qualifier("migrateRecetaPharmacyUseCase") MigrationOrchestrator recetaPharmacyMigration,
            @Qualifier("migrateSecurityPharmacyUseCase") MigrationOrchestrator securityPharmacyMigration,
            @Qualifier("assignPharmacyRolesUseCase") MigrationOrchestrator assignPharmacyRoles) {
        return new MigratePharmacyBatchUseCase(recetaPharmacyMigration, securityPharmacyMigration, assignPharmacyRoles);
    }

    // ─── recetali_receta Level 1 (FK dependencies) ───

    @Bean
    public MigrationOrchestrator migrateLaboratoryUseCase(
            @Qualifier("prodLaboratoryReader") SourceReader<RecetaLaboratory> sourceReader,
            @Qualifier("devLaboratoryWriter") TargetWriter<RecetaLaboratory> targetWriter) {
        return new RecetaLaboratoryMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateNotificationUseCase(
            @Qualifier("prodNotificationReader") SourceReader<RecetaNotification> sourceReader,
            @Qualifier("devNotificationWriter") TargetWriter<RecetaNotification> targetWriter) {
        return new RecetaNotificationMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateNotificationTemplateUseCase(
            @Qualifier("prodNotificationTemplateReader") SourceReader<RecetaNotificationTemplate> sourceReader,
            @Qualifier("devNotificationTemplateWriter") TargetWriter<RecetaNotificationTemplate> targetWriter) {
        return new RecetaNotificationTemplateMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migrateLogUseCase(
            @Qualifier("prodLogReader") SourceReader<RecetaLog> sourceReader,
            @Qualifier("devLogWriter") TargetWriter<RecetaLog> targetWriter) {
        return new RecetaLogMigrationUseCase(sourceReader, targetWriter);
    }

    @Bean
    public MigrationOrchestrator migratePharmacyDispenserUseCase(
            @Qualifier("prodPharmacyDispenserReader") SourceReader<RecetaPharmacyDispenser> dispenserReader,
            @Qualifier("prodRecetaPharmacyReader") SourceReader<RecetaPharmacy> pharmacyReader,
            PharmacyEmailResolver pharmacyEmailResolver,
            @Qualifier("devPharmacyDispenserWriter") TargetWriter<RecetaPharmacyDispenser> dispenserWriter) {
        return new MigratePharmacyDispenserUseCase(
                dispenserReader, pharmacyReader, pharmacyEmailResolver, dispenserWriter);
    }

    @Bean
    public MigrationOrchestrator migratePrescriptionUseCase(
            @Qualifier("prodPrescriptionReader") SourceReader<RecetaPrescription> prescriptionReader,
            @Qualifier("prodRecetaMedicReader") SourceReader<RecetaMedic> medicReader,
            @Qualifier("prodRecetaPatientReader") SourceReader<RecetaPatient> patientReader,
            MedicEmailResolver medicEmailResolver,
            PatientDocumentResolver patientDocumentResolver,
            @Qualifier("devPrescriptionWriter") TargetWriter<RecetaPrescription> prescriptionWriter) {
        return new MigratePrescriptionUseCase(
                prescriptionReader, medicReader, patientReader,
                medicEmailResolver, patientDocumentResolver, prescriptionWriter);
    }

    @Bean
    public MigrationOrchestrator migrateDispensationUseCase(
            @Qualifier("prodDispensationReader") SourceReader<RecetaDispensation> dispensationReader,
            @Qualifier("prodRecetaPharmacyReader") SourceReader<RecetaPharmacy> pharmacyReader,
            PrescriptionIdResolver prescriptionIdResolver,
            PharmacyEmailResolver pharmacyEmailResolver,
            PharmacyDispenserIdResolver pharmacyDispenserIdResolver,
            @Qualifier("devDispensationWriter") TargetWriter<RecetaDispensation> dispensationWriter) {
        return new MigrateDispensationUseCase(
                dispensationReader, pharmacyReader,
                prescriptionIdResolver, pharmacyEmailResolver, pharmacyDispenserIdResolver,
                dispensationWriter);
    }

    // ─── Batch orchestrator for Level 0 ───

    @Bean
    public RecetaLevel0BatchUseCase recetaLevel0BatchUseCase(
            @Qualifier("devTableTruncator") TableTruncator tableTruncator,
            @Qualifier("migrateCountryUseCase") MigrationOrchestrator country,
            @Qualifier("migrateRegionUseCase") MigrationOrchestrator region,
            @Qualifier("migrateLocalityUseCase") MigrationOrchestrator locality,
            @Qualifier("migrateRecetaFileUseCase") MigrationOrchestrator file,
            @Qualifier("migrateMedicEspecialityUseCase") MigrationOrchestrator medicEspeciality,
            @Qualifier("migrateMedicalProviderTypeUseCase") MigrationOrchestrator medicalProviderType,
            @Qualifier("migrateFranchiseUseCase") MigrationOrchestrator franchise,
            @Qualifier("migrateNotificationTypeUseCase") MigrationOrchestrator notificationType,
            @Qualifier("migrateDrougUseCase") MigrationOrchestrator droug,
            @Qualifier("migrateVademecumUseCase") MigrationOrchestrator vademecum,
            @Qualifier("migrateVersionUseCase") MigrationOrchestrator version,
            @Qualifier("migrateTriggerUseCase") MigrationOrchestrator trigger,
            @Qualifier("migrateNotificationOldUseCase") MigrationOrchestrator notificationOld,
            @Qualifier("migrateMedicalProviderUseCase") MigrationOrchestrator medicalProvider) {

        return new RecetaLevel0BatchUseCase(List.of(
                country, region, locality, file, medicEspeciality,
                medicalProviderType, franchise, notificationType, droug,
                vademecum, version, trigger, notificationOld, medicalProvider
        ), tableTruncator);
    }
}
