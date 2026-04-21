package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("recetali_receta.medical_provider")
public class DevRecetaMedicalProviderEntity implements Persistable<String> {

    @Id
    private String id;

    @Column("medicalProviderTypeId")
    private String medicalProviderTypeId;

    private String name;

    @Column("addressCountryId")
    private String addressCountryId;

    @Column("addressLocalityId")
    private String addressLocalityId;

    @Column("addressStreet")
    private String addressStreet;

    @Column("addressNumber")
    private String addressNumber;

    @Column("addressComments")
    private String addressComments;

    private String phone;

    private String email;

    private String password;

    @Column("businessName")
    private String businessName;

    private String rut;

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("updatedAt")
    private LocalDateTime updatedAt;

    @Column("deletedAt")
    private LocalDateTime deletedAt;

    @Column("logoId")
    private String logoId;

    @Column("passwordForgotCode")
    private String passwordForgotCode;

    private String status;

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

    public String getMedicalProviderTypeId() {
        return medicalProviderTypeId;
    }

    public void setMedicalProviderTypeId(String medicalProviderTypeId) {
        this.medicalProviderTypeId = medicalProviderTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressCountryId() {
        return addressCountryId;
    }

    public void setAddressCountryId(String addressCountryId) {
        this.addressCountryId = addressCountryId;
    }

    public String getAddressLocalityId() {
        return addressLocalityId;
    }

    public void setAddressLocalityId(String addressLocalityId) {
        this.addressLocalityId = addressLocalityId;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressComments() {
        return addressComments;
    }

    public void setAddressComments(String addressComments) {
        this.addressComments = addressComments;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
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

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getPasswordForgotCode() {
        return passwordForgotCode;
    }

    public void setPasswordForgotCode(String passwordForgotCode) {
        this.passwordForgotCode = passwordForgotCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
