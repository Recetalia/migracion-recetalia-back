package com.previsora.migracion_recetalia.usecase.business.dnma;

import com.previsora.migracion_recetalia.model.domain.dnma.DnmaViaAdmin;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateDnmaViaAdminUseCase extends AbstractMigrationUseCase<DnmaViaAdmin> {

    public static final String MIGRATION_NAME = "dnma-via-admin";

    private final SourceReader<DnmaViaAdmin> sourceReader;
    private final TargetWriter<DnmaViaAdmin> targetWriter;

    public MigrateDnmaViaAdminUseCase(SourceReader<DnmaViaAdmin> sourceReader, TargetWriter<DnmaViaAdmin> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<DnmaViaAdmin> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<DnmaViaAdmin> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(DnmaViaAdmin record) { return record.viaAdminId(); }
}
