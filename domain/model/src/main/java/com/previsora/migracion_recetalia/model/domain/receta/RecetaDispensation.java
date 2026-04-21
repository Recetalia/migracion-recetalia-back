package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaDispensation(
        String id,
        Integer qty,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String prescriptionId,
        String pharmacyId,
        String status,
        String substitute,
        String loteNumber,
        LocalDateTime loteExpireAt,
        String dispensedToName,
        String dispensedToLastname,
        String dispensedToDocument,
        String dispensedToAddressCity,
        String dispensedToAddressStreet,
        String dispensedToAddressCountryId,
        String dispensedById,
        String productId,
        String productType,
        String dispensedCancelledById
) {

    public RecetaDispensation withRemappedIds(String newPrescriptionId,
                                              String newPharmacyId,
                                              String newDispensedById) {
        return new RecetaDispensation(id, qty, createdAt, updatedAt, deletedAt,
                newPrescriptionId, newPharmacyId, status, substitute, loteNumber, loteExpireAt,
                dispensedToName, dispensedToLastname, dispensedToDocument,
                dispensedToAddressCity, dispensedToAddressStreet, dispensedToAddressCountryId,
                newDispensedById, productId, productType, dispensedCancelledById);
    }
}
