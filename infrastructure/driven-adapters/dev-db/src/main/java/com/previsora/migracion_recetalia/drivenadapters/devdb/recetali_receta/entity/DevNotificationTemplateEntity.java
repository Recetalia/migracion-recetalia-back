package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_receta.notification_template")
public class DevNotificationTemplateEntity implements Persistable<String> {

    @Id
    private String id;

    private String subject;

    private String template;

    @Column("sendBySms")
    private Boolean sendBySms;

    @Column("sendByWhatsapp")
    private Boolean sendByWhatsapp;

    @Column("sendByEmail")
    private Boolean sendByEmail;

    @Column("typeId")
    private String typeId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Transient
    @Override
    public boolean isNew() {
        return true;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getSendBySms() {
        return sendBySms;
    }

    public void setSendBySms(Boolean sendBySms) {
        this.sendBySms = sendBySms;
    }

    public Boolean getSendByWhatsapp() {
        return sendByWhatsapp;
    }

    public void setSendByWhatsapp(Boolean sendByWhatsapp) {
        this.sendByWhatsapp = sendByWhatsapp;
    }

    public Boolean getSendByEmail() {
        return sendByEmail;
    }

    public void setSendByEmail(Boolean sendByEmail) {
        this.sendByEmail = sendByEmail;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
