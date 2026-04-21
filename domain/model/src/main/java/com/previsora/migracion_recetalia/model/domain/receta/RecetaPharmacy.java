package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

/**
 * Domain model for recetali_receta.pharmacy.
 * <p>
 * Shared between prod (source) and dev (target). Schema is IDENTICAL in both environments.
 * Business key: email (unique constraint).
 *
 * @param id                  UUID string primary key (varchar 36)
 * @param name                pharmacy name (varchar 150)
 * @param addressCountryId    FK to country.id (nullable)
 * @param addressLocalityId   FK to localities.id (nullable)
 * @param addressStreet       street (varchar 200, nullable)
 * @param addressNumber       number (varchar 200, nullable)
 * @param addressComments     comments (varchar 200, nullable)
 * @param phone               phone number (text)
 * @param email               unique email (varchar 200) — business key for duplicate detection
 * @param password            BCrypt hash (varchar 300) — copied verbatim
 * @param businessName        business name (varchar 150)
 * @param rut                 tax id (varchar 150)
 * @param camera              enum CUFAS/AFI/CFU (nullable)
 * @param createdAt           creation timestamp
 * @param updatedAt           last update timestamp
 * @param deletedAt           soft-delete timestamp (nullable)
 * @param franchiseId         FK to franchise.id (nullable)
 * @param logoId              logo file id (varchar 36, nullable)
 * @param passwordForgotCode  password reset code (varchar 40, nullable)
 * @param managerName         manager first name (varchar 150)
 * @param managerLastname     manager last name (varchar 150)
 * @param managerCJP          manager professional licence (varchar 150)
 * @param status              account status (varchar 255, default INACTIVE)
 * @param managerDocument     manager identity document (text, nullable)
 */
public record RecetaPharmacy(
        String id,
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
        String camera,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String franchiseId,
        String logoId,
        String passwordForgotCode,
        String managerName,
        String managerLastname,
        String managerCJP,
        String status,
        String managerDocument
) {
}
