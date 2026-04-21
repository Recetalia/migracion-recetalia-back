package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

/**
 * Domain model for recetali_receta.medic.
 * <p>
 * Shared between prod (source) and dev (target). Schema is IDENTICAL in both environments.
 * Business key: email (unique constraint).
 *
 * @param id                  UUID string primary key (varchar 36)
 * @param name                first name (varchar 150)
 * @param lastname            last name (varchar 150)
 * @param gender              gender (varchar 150, nullable)
 * @param email               unique email (varchar 200) — business key for duplicate detection
 * @param password            BCrypt hash (varchar 300) — copied verbatim
 * @param phone               phone number (text)
 * @param document            identity document (text)
 * @param birthdate           birthdate (varchar 150)
 * @param addressCountryId    FK to country.id (nullable)
 * @param addressLocalityId   FK to localities.id (nullable)
 * @param addressStreet       street (varchar 200, nullable)
 * @param addressNumber       number (varchar 200, nullable)
 * @param addressComments     comments (varchar 200, nullable)
 * @param createdAt           creation timestamp
 * @param updatedAt           last update timestamp
 * @param deletedAt           soft-delete timestamp (nullable)
 * @param cjp                 professional licence code (varchar 150)
 * @param passwordForgotCode  password reset code (varchar 40, nullable)
 * @param status              account status (varchar 255, default INACTIVE)
 * @param especialityId       FK to medic_especiality.id (nullable)
 * @param medicalProviderId   FK to medical_provider.id (nullable)
 */
public record RecetaMedic(
        String id,
        String name,
        String lastname,
        String gender,
        String email,
        String password,
        String phone,
        String document,
        String birthdate,
        String addressCountryId,
        String addressLocalityId,
        String addressStreet,
        String addressNumber,
        String addressComments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String cjp,
        String passwordForgotCode,
        String status,
        String especialityId,
        String medicalProviderId
) {
}
