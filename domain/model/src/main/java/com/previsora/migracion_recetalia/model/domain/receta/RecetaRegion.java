package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaRegion(
        String id,
        String name,
        String slug,
        String lat,
        String lng,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
