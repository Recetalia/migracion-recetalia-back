package com.previsora.migracion_recetalia.model.domain.receta;

public record RecetaMedicEspeciality(
        String id,
        String name,
        String slug,
        String tags,
        String description,
        String createdAt,
        String updatedAt,
        String role
) {
}
