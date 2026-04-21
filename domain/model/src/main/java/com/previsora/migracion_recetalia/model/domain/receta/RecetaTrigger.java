package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaTrigger(
        String id,
        String event,
        String name,
        String entity,
        String entityId,
        LocalDateTime createdAt,
        String status
) {
}
