package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaUnidad;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateDnmaUnidadUseCase extends AbstractMigrationUseCase<DnmaUnidad> {

    public static final String MIGRATION_NAME = "dnma-unidad";

    private final SourceReader<DnmaUnidad> sourceReader;
    private final TargetWriter<DnmaUnidad> targetWriter;

    public MigrateDnmaUnidadUseCase(SourceReader<DnmaUnidad> sourceReader, TargetWriter<DnmaUnidad> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaUnidad> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaUnidad> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaUnidad record) { return record.unidadId(); }
}
