package com.previsora.migracion_recetalia.model.domain.dnma;

/**
 * Domain model for recetali_dnma.unidad.
 * <p>
 * No FK dependencies — migration order 5.
 * Business key: unidadId (primary key).
 */
public record DnmaUnidad(
        String unidadId,
        String unidadDsc,
        String dscAbr,
        String visibleULog,
        String visibleUAsist,
        String visibleUVolumen,
        String visibleUConcentr,
        String visibleUPack,
        String visibleUMedCant,
        String visibleUDosifVol,
        String observacion,
        String estado,
        String estadoVal,
        String snomedId,
        String snomedDsc
) {
}
