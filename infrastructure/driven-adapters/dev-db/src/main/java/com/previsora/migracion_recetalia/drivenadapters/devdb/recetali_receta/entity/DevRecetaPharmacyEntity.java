package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * R2DBC entity mapped to dev {@code recetali_receta.pharmacy}.
 * <p>
 * READ/WRITE — this entity is used for inserting migrated pharmacy records.
 */
@Table("`recetali_receta`.`pharmacy`")
public class DevRecetaPharmacyEntity implements Persistable<String> {

    @Id
    private String id;

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

    private String camera;

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("updatedAt")
    private LocalDateTime updatedAt;

    @Column("deletedAt")
    private LocalDateTime deletedAt;

    @Column("franchiseId")
    private String franchiseId;

    @Column("logoId")
    private String logoId;

    @Column("passwordForgotCode")
    private String passwordForgotCode;

    @Column("managerName")
    private String managerName;

    @Column("managerLastname")
    private String managerLastname;

    @Column("managerCJP")
    private String managerCJP;

    private String status;

    @Column("managerDocument")
    private String managerDocument;

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Transient
    @Override
    public boolean isNew() { return true; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddressCountryId() { return addressCountryId; }
    public void setAddressCountryId(String addressCountryId) { this.addressCountryId = addressCountryId; }

    public String getAddressLocalityId() { return addressLocalityId; }
    public void setAddressLocalityId(String addressLocalityId) { this.addressLocalityId = addressLocalityId; }

    public String getAddressStreet() { return addressStreet; }
    public void setAddressStreet(String addressStreet) { this.addressStreet = addressStreet; }

    public String getAddressNumber() { return addressNumber; }
    public void setAddressNumber(String addressNumber) { this.addressNumber = addressNumber; }

    public String getAddressComments() { return addressComments; }
    public void setAddressComments(String addressComments) { this.addressComments = addressComments; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getCamera() { return camera; }
    public void setCamera(String camera) { this.camera = camera; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public String getFranchiseId() { return franchiseId; }
    public void setFranchiseId(String franchiseId) { this.franchiseId = franchiseId; }

    public String getLogoId() { return logoId; }
    public void setLogoId(String logoId) { this.logoId = logoId; }

    public String getPasswordForgotCode() { return passwordForgotCode; }
    public void setPasswordForgotCode(String passwordForgotCode) { this.passwordForgotCode = passwordForgotCode; }

    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

    public String getManagerLastname() { return managerLastname; }
    public void setManagerLastname(String managerLastname) { this.managerLastname = managerLastname; }

    public String getManagerCJP() { return managerCJP; }
    public void setManagerCJP(String managerCJP) { this.managerCJP = managerCJP; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getManagerDocument() { return managerDocument; }
    public void setManagerDocument(String managerDocument) { this.managerDocument = managerDocument; }
}
