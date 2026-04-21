package com.previsora.migracion_recetalia.usecase.business;

import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates {@code recetali_receta.user} from prod to dev.
 * <p>
 * Source: prod.recetali_receta.user (MariaDB 10.5, read-only)
 * Target: dev.recetali_receta.user (MySQL 8.0, read/write)
 * <p>
 * Schema is identical between prod and dev — direct copy.
 * Business key for duplicate detection: email (unique constraint).
 */
public class MigrateRecetaUserUseCase extends AbstractMigrationUseCase<RecetaUser> {

    public static final String MIGRATION_NAME = "receta-user";

    private final SourceReader<RecetaUser> sourceReader;
    private final TargetWriter<RecetaUser> targetWriter;

    public MigrateRecetaUserUseCase(SourceReader<RecetaUser> sourceReader,
                                     TargetWriter<RecetaUser> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    protected SourceReader<RecetaUser> sourceReader() {
        return sourceReader;
    }

    @Override
    protected TargetWriter<RecetaUser> targetWriter() {
        return targetWriter;
    }

    @Override
    protected String recordId(RecetaUser record) {
        return record.email();
    }
}
