package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.ffa (Forma Farmaceutica Agrupada).
 * <p>
 * No FK dependencies — migration order 1.
 * Business key: ffaId (primary key).
 */
public record DnmaFfa(
        String ffaId,
        String descripcion,
        String estadoVal,
        String observaciones,
        String snomedId,
        String snomedDsc,
        String estado
) {
}
