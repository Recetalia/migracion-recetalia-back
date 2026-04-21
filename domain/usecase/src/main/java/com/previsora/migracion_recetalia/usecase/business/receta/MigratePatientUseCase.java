package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaPatient;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigratePatientUseCase extends AbstractMigrationUseCase<RecetaPatient> {

    public static final String MIGRATION_NAME = "receta-patient";

    private final SourceReader<RecetaPatient> sourceReader;
    private final TargetWriter<RecetaPatient> targetWriter;

    public MigratePatientUseCase(SourceReader<RecetaPatient> sourceReader,
                                 TargetWriter<RecetaPatient> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() {
        return MIGRATION_NAME;
    }

    @Override
    protected SourceReader<RecetaPatient> sourceReader() {
        return sourceReader;
    }

    @Override
    protected TargetWriter<RecetaPatient> targetWriter() {
        return targetWriter;
    }

    @Override
    protected String recordId(RecetaPatient record) {
        return record.document();
    }
}
