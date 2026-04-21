package com.previsora.migracion_recetalia.model.domain.receta;

public record RecetaLocality(
        String id,
        String regionId,
        String name,
        String slug,
        String lat,
        String lng
) {
}
