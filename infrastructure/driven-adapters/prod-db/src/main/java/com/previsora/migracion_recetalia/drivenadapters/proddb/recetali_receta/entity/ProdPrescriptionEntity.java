package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("recetali_receta.prescription")
public class ProdPrescriptionEntity {

    @Id
    private String id;

    @Column("expireAt")
    private LocalDateTime expireAt;

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("updatedAt")
    private LocalDateTime updatedAt;

    @Column("deletedAt")
    private LocalDateTime deletedAt;

    @Column("medicId")
    private String medicId;

    @Column("patientId")
    private String patientId;

    private String code;

    private String status;

    private BigDecimal dose;

    @Column("doseUnit")
    private String doseUnit;

    private Integer frecuency;

    @Column("frecuencyUnit")
    private String frecuencyUnit;

    @Column("medicalHistory")
    private String medicalHistory;

    private String affections;

    private Integer duration;

    @Column("durationUnit")
    private String durationUnit;

    @Column("productType")
    private String productType;

    @Column("productId")
    private String productId;

    @Column("doseType")
    private String doseType;

    @Column("dispensationPendingReminderSended")
    private Boolean dispensationPendingReminderSended;

    @Column("isCronic")
    private Boolean isCronic;

    @Column("dateTimeToSend")
    private LocalDateTime dateTimeToSend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getMedicId() {
        return medicId;
    }

    public void setMedicId(String medicId) {
        this.medicId = medicId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public Integer getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(Integer frecuency) {
        this.frecuency = frecuency;
    }

    public String getFrecuencyUnit() {
        return frecuencyUnit;
    }

    public void setFrecuencyUnit(String frecuencyUnit) {
        this.frecuencyUnit = frecuencyUnit;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAffections() {
        return affections;
    }

    public void setAffections(String affections) {
        this.affections = affections;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDoseType() {
        return doseType;
    }

    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    public Boolean getDispensationPendingReminderSended() {
        return dispensationPendingReminderSended;
    }

    public void setDispensationPendingReminderSended(Boolean dispensationPendingReminderSended) {
        this.dispensationPendingReminderSended = dispensationPendingReminderSended;
    }

    public Boolean getIsCronic() {
        return isCronic;
    }

    public void setIsCronic(Boolean isCronic) {
        this.isCronic = isCronic;
    }

    public LocalDateTime getDateTimeToSend() {
        return dateTimeToSend;
    }

    public void setDateTimeToSend(LocalDateTime dateTimeToSend) {
        this.dateTimeToSend = dateTimeToSend;
    }
}
