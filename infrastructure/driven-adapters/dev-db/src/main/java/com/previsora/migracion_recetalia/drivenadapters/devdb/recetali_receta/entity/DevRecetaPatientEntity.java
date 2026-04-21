package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("recetali_receta.patient")
public class DevRecetaPatientEntity {

    @Id
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String document;
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
    @Column("user")
    private String user;
    private String password;
    private String birthdate;
    @Column("createdAt")
    private LocalDateTime createdAt;
    @Column("updatedAt")
    private LocalDateTime updatedAt;
    @Column("deletedAt")
    private LocalDateTime deletedAt;
    @Column("avatarId")
    private String avatarId;
    private String sex;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }
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
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public String getAvatarId() { return avatarId; }
    public void setAvatarId(String avatarId) { this.avatarId = avatarId; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
}
