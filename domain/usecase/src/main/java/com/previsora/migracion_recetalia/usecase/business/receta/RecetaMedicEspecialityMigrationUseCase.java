package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicEspeciality;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaMedicEspecialityMigrationUseCase extends AbstractMigrationUseCase<RecetaMedicEspeciality> {

    public static final String MIGRATION_NAME = "receta-medic-especiality";

    private final SourceReader<RecetaMedicEspeciality> sourceReader;
    private final TargetWriter<RecetaMedicEspeciality> targetWriter;

    public RecetaMedicEspecialityMigrationUseCase(SourceReader<RecetaMedicEspeciality> sourceReader,
                                                   TargetWriter<RecetaMedicEspeciality> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaMedicEspeciality> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaMedicEspeciality> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaMedicEspeciality record) { return record.id(); }
}
