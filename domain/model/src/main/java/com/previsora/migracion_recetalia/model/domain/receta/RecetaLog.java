package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaLog(
        String id,
        String classname,
        String title,
        String description,
        String action,
        String link,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String userId
) {
}
