package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates {@code recetali_receta.pharmacy} from prod to dev.
 * <p>
 * Source: prod.recetali_receta.pharmacy (MariaDB 10.5, read-only)
 * Target: dev.recetali_receta.pharmacy (MySQL 8.0, read/write)
 * <p>
 * Schema is identical between prod and dev — direct copy.
 * Business key for duplicate detection: email (unique constraint).
 */
public class MigrateRecetaPharmacyUseCase extends AbstractMigrationUseCase<RecetaPharmacy> {

    public static final String MIGRATION_NAME = "receta-pharmacy";

    private final SourceReader<RecetaPharmacy> sourceReader;
    private final TargetWriter<RecetaPharmacy> targetWriter;

    public MigrateRecetaPharmacyUseCase(SourceReader<RecetaPharmacy> sourceReader,
                                         TargetWriter<RecetaPharmacy> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    protected SourceReader<RecetaPharmacy> sourceReader() {
        return sourceReader;
    }

    @Override
    protected TargetWriter<RecetaPharmacy> targetWriter() {
        return targetWriter;
    }

    @Override
    protected String recordId(RecetaPharmacy record) {
        return record.email();
    }
}
