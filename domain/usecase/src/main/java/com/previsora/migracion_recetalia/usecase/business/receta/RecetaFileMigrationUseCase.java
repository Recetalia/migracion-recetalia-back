package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaFile;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaFileMigrationUseCase extends AbstractMigrationUseCase<RecetaFile> {

    public static final String MIGRATION_NAME = "receta-file";

    private final SourceReader<RecetaFile> sourceReader;
    private final TargetWriter<RecetaFile> targetWriter;

    public RecetaFileMigrationUseCase(SourceReader<RecetaFile> sourceReader,
                                       TargetWriter<RecetaFile> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaFile> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaFile> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaFile record) { return record.id(); }
}
