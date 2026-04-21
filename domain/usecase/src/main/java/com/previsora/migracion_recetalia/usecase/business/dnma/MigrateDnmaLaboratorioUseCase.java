package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaLaboratorio;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateDnmaLaboratorioUseCase extends AbstractMigrationUseCase<DnmaLaboratorio> {

    public static final String MIGRATION_NAME = "dnma-laboratorio";

    private final SourceReader<DnmaLaboratorio> sourceReader;
    private final TargetWriter<DnmaLaboratorio> targetWriter;

    public MigrateDnmaLaboratorioUseCase(SourceReader<DnmaLaboratorio> sourceReader, TargetWriter<DnmaLaboratorio> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaLaboratorio> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaLaboratorio> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaLaboratorio record) { return record.labId(); }
}
