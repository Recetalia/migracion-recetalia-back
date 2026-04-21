package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaNotificationOld(
        String id,
        String phone,
        String subject,
        String message,
        String link,
        Boolean sendBySms,
        LocalDateTime sendedSmsAt,
        Boolean sendByWhatsapp,
        LocalDateTime sendedWhatsappAt,
        Boolean sendByEmail,
        LocalDateTime sendedEmailAt,
        LocalDateTime createdAt,
        String status,
        String typeId,
        String patientId,
        String medicId,
        String pharmacyId
) {
}
