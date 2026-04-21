package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaFfa;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

/**
 * Migrates recetali_dnma.ffa from prod to dev.
 * Business key: FFA_Id (primary key).
 */
public class MigrateDnmaFfaUseCase extends AbstractMigrationUseCase<DnmaFfa> {

    public static final String MIGRATION_NAME = "dnma-ffa";

    private final SourceReader<DnmaFfa> sourceReader;
    private final TargetWriter<DnmaFfa> targetWriter;

    public MigrateDnmaFfaUseCase(SourceReader<DnmaFfa> sourceReader, TargetWriter<DnmaFfa> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaFfa> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaFfa> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaFfa record) { return record.ffaId(); }
}
