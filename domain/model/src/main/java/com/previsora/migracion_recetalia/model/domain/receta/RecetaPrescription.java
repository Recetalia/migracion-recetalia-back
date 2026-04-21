package com.previsora.migracion_recetalia.model.domain.receta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecetaPrescription(
        String id,
        LocalDateTime expireAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String medicId,
        String patientId,
        String code,
        String status,
        BigDecimal dose,
        String doseUnit,
        Integer frecuency,
        String frecuencyUnit,
        String medicalHistory,
        String affections,
        Integer duration,
        String durationUnit,
        String productType,
        String productId,
        String doseType,
        Boolean dispensationPendingReminderSended,
        Boolean isCronic,
        LocalDateTime dateTimeToSend
) {

    public RecetaPrescription withRemappedIds(String newMedicId, String newPatientId) {
        return new RecetaPrescription(id, expireAt, createdAt, updatedAt, deletedAt,
                newMedicId, newPatientId, code, status, dose, doseUnit, frecuency, frecuencyUnit,
                medicalHistory, affections, duration, durationUnit, productType, productId,
                doseType, dispensationPendingReminderSended, isCronic, dateTimeToSend);
    }
}
