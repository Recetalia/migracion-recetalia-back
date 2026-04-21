package com.previsora.migracion_recetalia.model.domain.receta;

import java.time.LocalDateTime;

public record RecetaNotification(
        String id,
        String typeId,
        String email,
        String phone,
        String subject,
        String message,
        Boolean sendBySms,
        LocalDateTime sendedSmsAt,
        Boolean sendByWhatsapp,
        LocalDateTime sendedWhatsappAt,
        Boolean sendByEmail,
        LocalDateTime sendedEmailAt,
        LocalDateTime createdAt
) {
}
