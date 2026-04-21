package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.laboratorio.
 * <p>
 * No FK dependencies — migration order 2.
 * Business key: labId (primary key).
 */
public record DnmaLaboratorio(
        String labId,
        String nombre,
        String nombreAbr,
        String rut,
        String estadoVal,
        String observacion,
        String url,
        String estado
) {
}
