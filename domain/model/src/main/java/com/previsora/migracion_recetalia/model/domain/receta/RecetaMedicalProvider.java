package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaMedicalProvider(
        String id,
        String medicalProviderTypeId,
        String name,
        String addressCountryId,
        String addressLocalityId,
        String addressStreet,
        String addressNumber,
        String addressComments,
        String phone,
        String email,
        String password,
        String businessName,
        String rut,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String logoId,
        String passwordForgotCode,
        String status
) {
}
