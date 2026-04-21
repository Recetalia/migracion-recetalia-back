package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotification;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaNotificationMigrationUseCase extends AbstractMigrationUseCase<RecetaNotification> {

    public static final String MIGRATION_NAME = "receta-notification";

    private final SourceReader<RecetaNotification> sourceReader;
    private final TargetWriter<RecetaNotification> targetWriter;

    public RecetaNotificationMigrationUseCase(SourceReader<RecetaNotification> sourceReader,
                                               TargetWriter<RecetaNotification> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaNotification> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaNotification> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaNotification record) { return record.id(); }
}
