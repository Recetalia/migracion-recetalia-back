package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaPharmacyDispenser(
        String id,
        String name,
        String lastname,
        String document,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String pharmacyId
) {

    public RecetaPharmacyDispenser withPharmacyId(String newPharmacyId) {
        return new RecetaPharmacyDispenser(id, name, lastname, document, createdAt, updatedAt, deletedAt, newPharmacyId);
    }
}
