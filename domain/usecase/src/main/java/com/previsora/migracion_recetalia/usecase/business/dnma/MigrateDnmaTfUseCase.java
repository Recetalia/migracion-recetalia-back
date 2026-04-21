package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaTf;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateDnmaTfUseCase extends AbstractMigrationUseCase<DnmaTf> {

    public static final String MIGRATION_NAME = "dnma-tf";

    private final SourceReader<DnmaTf> sourceReader;
    private final TargetWriter<DnmaTf> targetWriter;

    public MigrateDnmaTfUseCase(SourceReader<DnmaTf> sourceReader, TargetWriter<DnmaTf> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaTf> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaTf> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaTf record) { return record.tfId(); }
}
