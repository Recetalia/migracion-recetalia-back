package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * R2DBC entity mapped to prod {@code recetali_receta.dispensation}.
 * <p>
 * READ-ONLY — this entity must never be persisted back to prod.
 */
@Table("recetali_receta.dispensation")
public class ProdDispensationEntity {

    @Id
    private String id;

    private Integer qty;

    @Column("createdAt")
    private LocalDateTime createdAt;

    @Column("updatedAt")
    private LocalDateTime updatedAt;

    @Column("deletedAt")
    private LocalDateTime deletedAt;

    @Column("prescriptionId")
    private String prescriptionId;

    @Column("pharmacyId")
    private String pharmacyId;

    private String status;

    private String substitute;

    @Column("loteNumber")
    private String loteNumber;

    @Column("loteExpireAt")
    private LocalDateTime loteExpireAt;

    @Column("dispensedToName")
    private String dispensedToName;

    @Column("dispensedToLastname")
    private String dispensedToLastname;

    @Column("dispensedToDocument")
    private String dispensedToDocument;

    @Column("dispensedToAddressCity")
    private String dispensedToAddressCity;

    @Column("dispensedToAddressStreet")
    private String dispensedToAddressStreet;

    @Column("dispensedToAddressCountryId")
    private String dispensedToAddressCountryId;

    @Column("dispensedById")
    private String dispensedById;

    @Column("productId")
    private String productId;

    @Column("productType")
    private String productType;

    @Column("dispensedCancelledById")
    private String dispensedCancelledById;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }

    public String getPharmacyId() { return pharmacyId; }
    public void setPharmacyId(String pharmacyId) { this.pharmacyId = pharmacyId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSubstitute() { return substitute; }
    public void setSubstitute(String substitute) { this.substitute = substitute; }

    public String getLoteNumber() { return loteNumber; }
    public void setLoteNumber(String loteNumber) { this.loteNumber = loteNumber; }

    public LocalDateTime getLoteExpireAt() { return loteExpireAt; }
    public void setLoteExpireAt(LocalDateTime loteExpireAt) { this.loteExpireAt = loteExpireAt; }

    public String getDispensedToName() { return dispensedToName; }
    public void setDispensedToName(String dispensedToName) { this.dispensedToName = dispensedToName; }

    public String getDispensedToLastname() { return dispensedToLastname; }
    public void setDispensedToLastname(String dispensedToLastname) { this.dispensedToLastname = dispensedToLastname; }

    public String getDispensedToDocument() { return dispensedToDocument; }
    public void setDispensedToDocument(String dispensedToDocument) { this.dispensedToDocument = dispensedToDocument; }

    public String getDispensedToAddressCity() { return dispensedToAddressCity; }
    public void setDispensedToAddressCity(String dispensedToAddressCity) { this.dispensedToAddressCity = dispensedToAddressCity; }

    public String getDispensedToAddressStreet() { return dispensedToAddressStreet; }
    public void setDispensedToAddressStreet(String dispensedToAddressStreet) { this.dispensedToAddressStreet = dispensedToAddressStreet; }

    public String getDispensedToAddressCountryId() { return dispensedToAddressCountryId; }
    public void setDispensedToAddressCountryId(String dispensedToAddressCountryId) { this.dispensedToAddressCountryId = dispensedToAddressCountryId; }

    public String getDispensedById() { return dispensedById; }
    public void setDispensedById(String dispensedById) { this.dispensedById = dispensedById; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public String getDispensedCancelledById() { return dispensedCancelledById; }
    public void setDispensedCancelledById(String dispensedCancelledById) { this.dispensedCancelledById = dispensedCancelledById; }
}
