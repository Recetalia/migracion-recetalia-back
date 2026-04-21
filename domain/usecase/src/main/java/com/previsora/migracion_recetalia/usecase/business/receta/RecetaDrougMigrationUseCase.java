package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaDroug;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaDrougMigrationUseCase extends AbstractMigrationUseCase<RecetaDroug> {

    public static final String MIGRATION_NAME = "receta-droug";

    private final SourceReader<RecetaDroug> sourceReader;
    private final TargetWriter<RecetaDroug> targetWriter;

    public RecetaDrougMigrationUseCase(SourceReader<RecetaDroug> sourceReader,
                                        TargetWriter<RecetaDroug> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaDroug> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaDroug> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaDroug record) { return record.id(); }
}
