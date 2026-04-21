package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaLaboratory;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaLaboratoryMigrationUseCase extends AbstractMigrationUseCase<RecetaLaboratory> {

    public static final String MIGRATION_NAME = "receta-laboratory";

    private final SourceReader<RecetaLaboratory> sourceReader;
    private final TargetWriter<RecetaLaboratory> targetWriter;

    public RecetaLaboratoryMigrationUseCase(SourceReader<RecetaLaboratory> sourceReader,
                                             TargetWriter<RecetaLaboratory> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaLaboratory> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaLaboratory> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaLaboratory record) { return record.id(); }
}
