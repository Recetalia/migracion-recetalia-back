package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * R2DBC entity mapped to dev {@code recetali_receta.medic}.
 * <p>
 * READ/WRITE — this entity is used for inserting migrated medic records.
 */
@Table("`recetali_receta`.`medic`")
public class DevRecetaMedicEntity implements Persistable<String> {

    @Id
    private String id;

    private String name;

    private String lastname;

    private String gender;

    private String email;

    private String password;

    private String phone;

    private String document;

    private String birthdate;

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

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("updatedAt")
    private LocalDateTime updatedAt;

    @Column("deletedAt")
    private LocalDateTime deletedAt;

    private String cjp;

    @Column("passwordForgotCode")
    private String passwordForgotCode;

    private String status;

    @Column("especialityId")
    private String especialityId;

    @Column("medicalProviderId")
    private String medicalProviderId;

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Transient
    @Override
    public boolean isNew() { return true; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public String getCjp() { return cjp; }
    public void setCjp(String cjp) { this.cjp = cjp; }

    public String getPasswordForgotCode() { return passwordForgotCode; }
    public void setPasswordForgotCode(String passwordForgotCode) { this.passwordForgotCode = passwordForgotCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEspecialityId() { return especialityId; }
    public void setEspecialityId(String especialityId) { this.especialityId = especialityId; }

    public String getMedicalProviderId() { return medicalProviderId; }
    public void setMedicalProviderId(String medicalProviderId) { this.medicalProviderId = medicalProviderId; }
}
