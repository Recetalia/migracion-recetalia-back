package com.previsora.migracion_recetalia.usecase.business.receta;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaNotificationOld;
import com.previsora.migracion_recetalia.usecase.business.AbstractMigrationUseCase;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;

public class RecetaNotificationOldMigrationUseCase extends AbstractMigrationUseCase<RecetaNotificationOld> {

    public static final String MIGRATION_NAME = "receta-notification-old";

    private final SourceReader<RecetaNotificationOld> sourceReader;
    private final TargetWriter<RecetaNotificationOld> targetWriter;

    public RecetaNotificationOldMigrationUseCase(SourceReader<RecetaNotificationOld> sourceReader,
                                                  TargetWriter<RecetaNotificationOld> targetWriter) {
        this.sourceReader = sourceReader;
        this.targetWriter = targetWriter;
    }

    @Override
    public String migrationName() { return MIGRATION_NAME; }

    @Override
    protected SourceReader<RecetaNotificationOld> sourceReader() { return sourceReader; }

    @Override
    protected TargetWriter<RecetaNotificationOld> targetWriter() { return targetWriter; }

    @Override
    protected String recordId(RecetaNotificationOld record) { return record.id(); }
}
