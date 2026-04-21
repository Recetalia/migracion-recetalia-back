package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationTemplate;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaNotificationTemplateMigrationUseCase extends AbstractMigrationUseCase<RecetaNotificationTemplate> {

    public static final String MIGRATION_NAME = "receta-notification-template";

    private final SourceReader<RecetaNotificationTemplate> sourceReader;
    private final TargetWriter<RecetaNotificationTemplate> targetWriter;

    public RecetaNotificationTemplateMigrationUseCase(SourceReader<RecetaNotificationTemplate> sourceReader,
                                                       TargetWriter<RecetaNotificationTemplate> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaNotificationTemplate> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaNotificationTemplate> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaNotificationTemplate record) { return record.id(); }
}
