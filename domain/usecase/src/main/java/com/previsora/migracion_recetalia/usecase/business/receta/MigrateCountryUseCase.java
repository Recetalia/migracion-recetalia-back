package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaCountry;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class MigrateCountryUseCase extends AbstractMigrationUseCase<RecetaCountry> {

    public static final String MIGRATION_NAME = "receta-country";

    private final SourceReader<RecetaCountry> sourceReader;
    private final TargetWriter<RecetaCountry> targetWriter;

    public MigrateCountryUseCase(SourceReader<RecetaCountry> sourceReader,
                                  TargetWriter<RecetaCountry> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaCountry> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaCountry> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaCountry record) { return record.id(); }
}
