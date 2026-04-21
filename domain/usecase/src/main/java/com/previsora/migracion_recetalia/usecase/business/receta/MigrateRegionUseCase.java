package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaRegion;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateRegionUseCase extends AbstractMigrationUseCase<RecetaRegion> {

    public static final String MIGRATION_NAME = "receta-region";

    private final SourceReader<RecetaRegion> sourceReader;
    private final TargetWriter<RecetaRegion> targetWriter;

    public MigrateRegionUseCase(SourceReader<RecetaRegion> sourceReader,
                                 TargetWriter<RecetaRegion> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaRegion> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaRegion> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaRegion record) { return record.id(); }
}
