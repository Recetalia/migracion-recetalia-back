package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.via_admin.
 * <p>
 * No FK dependencies — migration order 6.
 * Business key: viaAdminId (primary key).
 */
public record DnmaViaAdmin(
        String viaAdminId,
        String viaAdminDsc,
        String estadoVal,
        String observacion,
        String snomedId,
        String snomedDsc,
        String estado
) {
}
