package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaPatient(
        String id,
        String name,
        String lastname,
        String email,
        String phone,
        String document,
        String addressCountryId,
        String addressLocalityId,
        String addressStreet,
        String addressNumber,
        String addressComments,
        String user,
        String password,
        String birthdate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String avatarId,
        String sex
) {
}
