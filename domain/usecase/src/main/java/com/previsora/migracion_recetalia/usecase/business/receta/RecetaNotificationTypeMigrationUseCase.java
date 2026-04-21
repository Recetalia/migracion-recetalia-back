package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationType;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaNotificationTypeMigrationUseCase extends AbstractMigrationUseCase<RecetaNotificationType> {

    public static final String MIGRATION_NAME = "receta-notification-type";

    private final SourceReader<RecetaNotificationType> sourceReader;
    private final TargetWriter<RecetaNotificationType> targetWriter;

    public RecetaNotificationTypeMigrationUseCase(SourceReader<RecetaNotificationType> sourceReader,
                                                   TargetWriter<RecetaNotificationType> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaNotificationType> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaNotificationType> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaNotificationType record) { return record.id(); }
}
