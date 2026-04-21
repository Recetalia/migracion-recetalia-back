package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaSustancia;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateDnmaSustanciaUseCase extends AbstractMigrationUseCase<DnmaSustancia> {

    public static final String MIGRATION_NAME = "dnma-sustancia";

    private final SourceReader<DnmaSustancia> sourceReader;
    private final TargetWriter<DnmaSustancia> targetWriter;

    public MigrateDnmaSustanciaUseCase(SourceReader<DnmaSustancia> sourceReader, TargetWriter<DnmaSustancia> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaSustancia> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaSustancia> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaSustancia record) { return record.sustanciaId(); }
}
