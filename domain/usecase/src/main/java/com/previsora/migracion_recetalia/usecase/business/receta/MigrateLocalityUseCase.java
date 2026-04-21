package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaLocality;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateLocalityUseCase extends AbstractMigrationUseCase<RecetaLocality> {

    public static final String MIGRATION_NAME = "receta-locality";

    private final SourceReader<RecetaLocality> sourceReader;
    private final TargetWriter<RecetaLocality> targetWriter;

    public MigrateLocalityUseCase(SourceReader<RecetaLocality> sourceReader,
                                   TargetWriter<RecetaLocality> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaLocality> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaLocality> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaLocality record) { return record.id(); }
}
