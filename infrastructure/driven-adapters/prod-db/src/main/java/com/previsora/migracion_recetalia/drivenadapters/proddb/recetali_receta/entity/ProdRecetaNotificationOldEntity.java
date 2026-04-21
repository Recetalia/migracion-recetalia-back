package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("recetali_receta.notification_old")
public class ProdRecetaNotificationOldEntity {

    @Id
    private String id;

    private String phone;

    private String subject;

    private String message;

    private String link;

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

    private String status;

    @Column("typeId")
    private String typeId;

    @Column("patientId")
    private String patientId;

    @Column("medicId")
    private String medicId;

    @Column("pharmacyId")
    private String pharmacyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicId() {
        return medicId;
    }

    public void setMedicId(String medicId) {
        this.medicId = medicId;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
