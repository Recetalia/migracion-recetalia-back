package com.previsora.migracion_recetalia.model.domain.receta;

public record RecetaCountry(
        String id,
        String iso,
        String iso3,
        String name,
        String nativeName,
        String capital,
        String latlng,
        String flag,
        String currency,
        String currencySymbol,
        Integer numcode,
        Integer phonecode,
        Integer sort
) {
}
