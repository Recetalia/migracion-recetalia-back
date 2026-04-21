package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.sustancia.
 * <p>
 * No FK dependencies — migration order 3.
 * Business key: sustanciaId (primary key).
 */
public record DnmaSustancia(
        String sustanciaId,
        String sustanciaDsc,
        String dci,
        String riesgoTerato,
        String sustanciaEstado,
        String sustanciaEstValidacion,
        String comercializado,
        String descripciones
) {
}
