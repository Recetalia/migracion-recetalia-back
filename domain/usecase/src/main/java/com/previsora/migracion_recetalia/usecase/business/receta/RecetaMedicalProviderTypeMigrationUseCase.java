package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProviderType;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaMedicalProviderTypeMigrationUseCase extends AbstractMigrationUseCase<RecetaMedicalProviderType> {

    public static final String MIGRATION_NAME = "receta-medical-provider-type";

    private final SourceReader<RecetaMedicalProviderType> sourceReader;
    private final TargetWriter<RecetaMedicalProviderType> targetWriter;

    public RecetaMedicalProviderTypeMigrationUseCase(SourceReader<RecetaMedicalProviderType> sourceReader,
                                                      TargetWriter<RecetaMedicalProviderType> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaMedicalProviderType> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaMedicalProviderType> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaMedicalProviderType record) { return record.id(); }
}
