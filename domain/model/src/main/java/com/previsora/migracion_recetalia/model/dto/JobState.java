package com.previsora.migracion_recetalia.model.dto;

import com.previsora.migracion_recetalia.model.enums.JobStatus;

import java.time.Instant;
import java.util.List;

public record JobState(
        String jobId,
        String migrationName,
        JobStatus status,
        Instant startedAt,
        Instant finishedAt,
        List<MigrationSummary> summaries,
        String error
) {
}
