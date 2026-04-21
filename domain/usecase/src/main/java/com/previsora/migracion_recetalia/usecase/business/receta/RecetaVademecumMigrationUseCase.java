package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaVademecum;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaVademecumMigrationUseCase extends AbstractMigrationUseCase<RecetaVademecum> {

    public static final String MIGRATION_NAME = "receta-vademecum";

    private final SourceReader<RecetaVademecum> sourceReader;
    private final TargetWriter<RecetaVademecum> targetWriter;

    public RecetaVademecumMigrationUseCase(SourceReader<RecetaVademecum> sourceReader,
                                            TargetWriter<RecetaVademecum> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaVademecum> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaVademecum> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaVademecum record) { return record.id(); }
}
