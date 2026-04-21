package com.previsora.migracion_recetalia.model.domain.receta;

public record RecetaNotificationTemplate(
        String id,
        String subject,
        String template,
        Boolean sendBySms,
        Boolean sendByWhatsapp,
        Boolean sendByEmail,
        String typeId
) {
}
