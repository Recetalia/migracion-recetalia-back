package com.previsora.migracion_recetalia.model.domain;

import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedic;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaPharmacy;

/**
 * Domain model for securitydb.users (target only — derived from RecetaUser).
 * <p>
 * There is NO securitydb in prod. This model is derived from prod.recetali_receta.user
 * using the {@link #fromRecetaUser(RecetaUser)} factory method.
 * <p>
 * Business key: email (unique constraint).
 *
 * <p><b>ASSUMPTION (MEDIUM RISK):</b> application_id defaults to 2 ("medics-recetalia-app").
 * Verify with the security team whether all migrated users should belong to this application.
 *
 * @param id            auto-increment bigint — null on insert (DB generates)
 * @param email         unique email
 * @param username      set to email (matches existing pattern in seed data)
 * @param password      BCrypt hash — copied verbatim from RecetaUser
 * @param isActive      derived: true if RecetaUser.deletedAt is null
 * @param applicationId FK to applications.id — defaults to 2
 */
public record SecurityUser(
        Long id,
        String email,
        String username,
        String password,
        boolean isActive,
        long applicationId
) {

    /** Default application_id for migrated medic users: "medics-recetalia-app" (id=2). */
    public static final long DEFAULT_APPLICATION_ID = 2L;

    /** Application_id for migrated pharmacy users (id=4). */
    public static final long PHARMACY_APPLICATION_ID = 4L;

    /**
     * Derive a SecurityUser from a RecetaUser.
     * <ul>
     *   <li>email and password are copied directly</li>
     *   <li>username = email (matches existing seed data pattern)</li>
     *   <li>isActive = (deletedAt == null)</li>
     *   <li>id = null (auto-increment)</li>
     *   <li>applicationId = 2 (medics-recetalia-app)</li>
     * </ul>
     */
    public static SecurityUser fromRecetaUser(RecetaUser source) {
        return new SecurityUser(
                null,
                source.email(),
                source.email(),
                source.password(),
                source.deletedAt() == null,
                DEFAULT_APPLICATION_ID
        );
    }

    /**
     * Derive a SecurityUser from a RecetaMedic.
     * <ul>
     *   <li>email and password are copied directly</li>
     *   <li>username = email (matches existing seed data pattern)</li>
     *   <li>isActive = (deletedAt == null)</li>
     *   <li>id = null (auto-increment)</li>
     *   <li>applicationId = 2 (medics-recetalia-app)</li>
     * </ul>
     */
    public static SecurityUser fromRecetaMedic(RecetaMedic source) {
        return new SecurityUser(
                null,
                source.email(),
                source.email(),
                source.password(),
                source.deletedAt() == null,
                DEFAULT_APPLICATION_ID
        );
    }

    /**
     * Derive a SecurityUser from a RecetaPharmacy.
     * <ul>
     *   <li>email and password are copied directly</li>
     *   <li>username = email (matches existing seed data pattern)</li>
     *   <li>isActive = (deletedAt == null)</li>
     *   <li>id = null (auto-increment)</li>
     *   <li>applicationId = 4 (pharmacy-recetalia-app)</li>
     * </ul>
     */
    public static SecurityUser fromRecetaPharmacy(RecetaPharmacy source) {
        return new SecurityUser(
                null,
                source.email(),
                source.email(),
                source.password(),
                source.deletedAt() == null,
                PHARMACY_APPLICATION_ID
        );
    }
}
