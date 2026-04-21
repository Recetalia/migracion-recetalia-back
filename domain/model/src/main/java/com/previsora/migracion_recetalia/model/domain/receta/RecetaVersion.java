package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaVersion(
        String id,
        String version,
        LocalDateTime createdAt
) {
}
