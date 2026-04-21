package com.previsora.migracion_recetalia.model.domain;

import java.time.LocalDateTime;

/**
 * Domain model for recetali_receta.user.
 * <p>
 * Shared between prod (source) and dev (target). Schema is IDENTICAL in both environments.
 * Business key: email (unique constraint).
 *
 * @param id        UUID string primary key (varchar 36)
 * @param name      first name (varchar 150)
 * @param lastname  last name (varchar 150)
 * @param email     unique email (varchar 200) — business key for duplicate detection
 * @param password  BCrypt hash (varchar 300) — copied verbatim
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 * @param deletedAt soft-delete timestamp (nullable)
 * @param avatarId  FK to file.id (nullable) — file table must be migrated first
 */
public record RecetaUser(
        String id,
        String name,
        String lastname,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        String avatarId
) {
}
