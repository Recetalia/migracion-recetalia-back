package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates {@code recetali_receta.medic} from prod to dev.
 * <p>
 * Source: prod.recetali_receta.medic (MariaDB 10.5, read-only)
 * Target: dev.recetali_receta.medic (MySQL 8.0, read/write)
 * <p>
 * Schema is identical between prod and dev — direct copy.
 * Business key for duplicate detection: email (unique constraint).
 */
public class MigrateRecetaMedicUseCase extends AbstractMigrationUseCase<RecetaMedic> {

    public static final String MIGRATION_NAME = "receta-medic";

    private final SourceReader<RecetaMedic> sourceReader;
    private final TargetWriter<RecetaMedic> targetWriter;

    public MigrateRecetaMedicUseCase(SourceReader<RecetaMedic> sourceReader,
                                      TargetWriter<RecetaMedic> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    protected SourceReader<RecetaMedic> sourceReader() {
        return sourceReader;
    }

    @Override
    protected TargetWriter<RecetaMedic> targetWriter() {
        return targetWriter;
    }

    @Override
    protected String recordId(RecetaMedic record) {
        return record.email();
    }
}
