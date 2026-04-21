package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaFranchise;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaFranchiseMigrationUseCase extends AbstractMigrationUseCase<RecetaFranchise> {

    public static final String MIGRATION_NAME = "receta-franchise";

    private final SourceReader<RecetaFranchise> sourceReader;
    private final TargetWriter<RecetaFranchise> targetWriter;

    public RecetaFranchiseMigrationUseCase(SourceReader<RecetaFranchise> sourceReader,
                                            TargetWriter<RecetaFranchise> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaFranchise> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaFranchise> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaFranchise record) { return record.id(); }
}
