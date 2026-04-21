package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.tf (Tipo Farmaceutico).
 * <p>
 * No FK dependencies — migration order 4.
 * Business key: tfId (primary key).
 */
public record DnmaTf(
        String tfId,
        String tfDsc,
        String generica,
        String tfgId,
        String tfEstado,
        String tfEstValidacion,
        String comercializado,
        String descripciones
) {
}
