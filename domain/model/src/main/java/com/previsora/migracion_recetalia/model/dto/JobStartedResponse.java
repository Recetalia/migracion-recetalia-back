package com.previsora.migracion_recetalia.model.dto;

import java.time.Instant;

public record JobStartedResponse(
        String jobId,
        String migrationName,
        Instant startedAt,
        String statusUrl
) {
    public static JobStartedResponse of(String jobId, String migrationName, Instant startedAt) {
        return new JobStartedResponse(jobId, migrationName, startedAt, "/v1/migrations/jobs/" + jobId);
    }
}
