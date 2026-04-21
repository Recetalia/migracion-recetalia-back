package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("recetali_receta.notification")
public class ProdNotificationEntity {

    @Id
    private String id;

    @Column("typeId")
    private String typeId;

    private String email;

    private String phone;

    private String subject;

    private String message;

    @Column("sendBySms")
    private Boolean sendBySms;

    @Column("sendedSmsAt")
    private LocalDateTime sendedSmsAt;

    @Column("sendByWhatsapp")
    private Boolean sendByWhatsapp;

    @Column("sendedWhatsappAt")
    private LocalDateTime sendedWhatsappAt;

    @Column("sendByEmail")
    private Boolean sendByEmail;

    @Column("sendedEmailAt")
    private LocalDateTime sendedEmailAt;

    @Column("createdAt")
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSendBySms() {
        return sendBySms;
    }

    public void setSendBySms(Boolean sendBySms) {
        this.sendBySms = sendBySms;
    }

    public LocalDateTime getSendedSmsAt() {
        return sendedSmsAt;
    }

    public void setSendedSmsAt(LocalDateTime sendedSmsAt) {
        this.sendedSmsAt = sendedSmsAt;
    }

    public Boolean getSendByWhatsapp() {
        return sendByWhatsapp;
    }

    public void setSendByWhatsapp(Boolean sendByWhatsapp) {
        this.sendByWhatsapp = sendByWhatsapp;
    }

    public LocalDateTime getSendedWhatsappAt() {
        return sendedWhatsappAt;
    }

    public void setSendedWhatsappAt(LocalDateTime sendedWhatsappAt) {
        this.sendedWhatsappAt = sendedWhatsappAt;
    }

    public Boolean getSendByEmail() {
        return sendByEmail;
    }

    public void setSendByEmail(Boolean sendByEmail) {
        this.sendByEmail = sendByEmail;
    }

    public LocalDateTime getSendedEmailAt() {
        return sendedEmailAt;
    }

    public void setSendedEmailAt(LocalDateTime sendedEmailAt) {
        this.sendedEmailAt = sendedEmailAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
