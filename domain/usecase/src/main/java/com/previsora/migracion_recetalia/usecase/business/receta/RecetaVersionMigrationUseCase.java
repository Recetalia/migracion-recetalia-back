package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaVersion;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaVersionMigrationUseCase extends AbstractMigrationUseCase<RecetaVersion> {

    public static final String MIGRATION_NAME = "receta-version";

    private final SourceReader<RecetaVersion> sourceReader;
    private final TargetWriter<RecetaVersion> targetWriter;

    public RecetaVersionMigrationUseCase(SourceReader<RecetaVersion> sourceReader,
                                          TargetWriter<RecetaVersion> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaVersion> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaVersion> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaVersion record) { return record.id(); }
}
