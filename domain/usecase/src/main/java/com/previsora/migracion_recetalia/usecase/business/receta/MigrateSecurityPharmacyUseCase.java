package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates pharmacies from prod.recetali_receta.pharmacy into dev.securitydb.users.
 * <p>
 * This is a CROSS-SCHEMA DERIVATION, not a direct copy.
 * Source: prod.recetali_receta.pharmacy (MariaDB 10.5, read-only)
 * Target: dev.securitydb.users (MySQL 8.0, read/write)
 * <p>
 * The SourceReader reads from prod.recetali_receta.pharmacy and derives SecurityUser
 * domain objects using {@link SecurityUser#fromRecetaPharmacy}.
 * The TargetWriter writes to dev.securitydb.users.
 * <p>
 * Business key for duplicate detection: email (unique constraint).
 */
public class MigrateSecurityPharmacyUseCase extends AbstractMigrationUseCase<SecurityUser> {

    public static final String MIGRATION_NAME = "security-pharmacies";

    private final SourceReader<SecurityUser> sourceReader;
    private final TargetWriter<SecurityUser> targetWriter;

    public MigrateSecurityPharmacyUseCase(SourceReader<SecurityUser> sourceReader,
                                           TargetWriter<SecurityUser> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    protected SourceReader<SecurityUser> sourceReader() {
        return sourceReader;
    }

    @Override
    protected TargetWriter<SecurityUser> targetWriter() {
        return targetWriter;
    }

    @Override
    protected String recordId(SecurityUser record) {
        return record.email();
    }
}
