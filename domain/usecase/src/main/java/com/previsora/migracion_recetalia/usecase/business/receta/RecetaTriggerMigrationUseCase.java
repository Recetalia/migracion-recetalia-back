package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaTrigger;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaTriggerMigrationUseCase extends AbstractMigrationUseCase<RecetaTrigger> {

    public static final String MIGRATION_NAME = "receta-trigger";

    private final SourceReader<RecetaTrigger> sourceReader;
    private final TargetWriter<RecetaTrigger> targetWriter;

    public RecetaTriggerMigrationUseCase(SourceReader<RecetaTrigger> sourceReader,
                                          TargetWriter<RecetaTrigger> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaTrigger> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaTrigger> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaTrigger record) { return record.id(); }
}
