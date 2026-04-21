package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaLog;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaLogMigrationUseCase extends AbstractMigrationUseCase<RecetaLog> {

    public static final String MIGRATION_NAME = "receta-log";

    private final SourceReader<RecetaLog> sourceReader;
    private final TargetWriter<RecetaLog> targetWriter;

    public RecetaLogMigrationUseCase(SourceReader<RecetaLog> sourceReader,
                                      TargetWriter<RecetaLog> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaLog> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaLog> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaLog record) { return record.id(); }
}
