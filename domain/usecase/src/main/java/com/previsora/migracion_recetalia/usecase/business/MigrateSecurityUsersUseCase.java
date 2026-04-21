package com.previsora.migracion_recetalia.usecase.business;

import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates users from prod.recetali_receta.user into dev.securitydb.users.
 * <p>
 * This is a CROSS-SCHEMA DERIVATION, not a direct copy.
 * Source: prod.recetali_receta.user (MariaDB 10.5, read-only)
 * Target: dev.securitydb.users (MySQL 8.0, read/write)
 * <p>
 * The SourceReader reads from prod.recetali_receta.user and derives SecurityUser
 * domain objects using {@link SecurityUser#fromRecetaUser(com.previsora.migracion_recetalia.model.domain.RecetaUser)}.
 * The TargetWriter writes to dev.securitydb.users.
 * <p>
 * Business key for duplicate detection: email (unique constraint).
 * <p>
 * <b>ASSUMPTION (MEDIUM RISK):</b> application_id defaults to 2 ("medics-recetalia-app").
 */
public class MigrateSecurityUsersUseCase extends AbstractMigrationUseCase<SecurityUser> {

    public static final String MIGRATION_NAME = "security-users";

    private final SourceReader<SecurityUser> sourceReader;
    private final TargetWriter<SecurityUser> targetWriter;

    public MigrateSecurityUsersUseCase(SourceReader<SecurityUser> sourceReader,
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
