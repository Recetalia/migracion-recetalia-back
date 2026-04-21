package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProvider;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaMedicalProviderMigrationUseCase extends AbstractMigrationUseCase<RecetaMedicalProvider> {

    public static final String MIGRATION_NAME = "receta-medical-provider";

    private final SourceReader<RecetaMedicalProvider> sourceReader;
    private final TargetWriter<RecetaMedicalProvider> targetWriter;

    public RecetaMedicalProviderMigrationUseCase(SourceReader<RecetaMedicalProvider> sourceReader,
                                                  TargetWriter<RecetaMedicalProvider> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaMedicalProvider> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaMedicalProvider> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaMedicalProvider record) { return record.name(); }
}
