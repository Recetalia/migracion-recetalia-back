package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.unidad")
public class DevDnmaUnidadEntity {

    @Id
    @Column("UNIDAD_ID")
    private String unidadId;

    @Column("UNIDAD_DSC")
    private String unidadDsc;

    @Column("DSC_ABR")
    private String dscAbr;

    @Column("VISIBLEULOG")
    private String visibleULog;

    @Column("VISIBLEUASIST")
    private String visibleUAsist;

    @Column("VISIBLEUVOLUMEN")
    private String visibleUVolumen;

    @Column("VISIBLEUCONCENTR")
    private String visibleUConcentr;

    @Column("VISIBLEUPACK")
    private String visibleUPack;

    @Column("VISIBLEUMEDCANT")
    private String visibleUMedCant;

    @Column("VISIBLEUDOSIFVOL")
    private String visibleUDosifVol;

    @Column("OBSERVACION")
    private String observacion;

    @Column("ESTADO")
    private String estado;

    @Column("ESTADOVAL")
    private String estadoVal;

    @Column("SNOMED_Id")
    private String snomedId;

    @Column("SNOMED_DSC")
    private String snomedDsc;

    public String getUnidadId() { return unidadId; }
    public void setUnidadId(String unidadId) { this.unidadId = unidadId; }
    public String getUnidadDsc() { return unidadDsc; }
    public void setUnidadDsc(String unidadDsc) { this.unidadDsc = unidadDsc; }
    public String getDscAbr() { return dscAbr; }
    public void setDscAbr(String dscAbr) { this.dscAbr = dscAbr; }
    public String getVisibleULog() { return visibleULog; }
    public void setVisibleULog(String visibleULog) { this.visibleULog = visibleULog; }
    public String getVisibleUAsist() { return visibleUAsist; }
    public void setVisibleUAsist(String visibleUAsist) { this.visibleUAsist = visibleUAsist; }
    public String getVisibleUVolumen() { return visibleUVolumen; }
    public void setVisibleUVolumen(String visibleUVolumen) { this.visibleUVolumen = visibleUVolumen; }
    public String getVisibleUConcentr() { return visibleUConcentr; }
    public void setVisibleUConcentr(String visibleUConcentr) { this.visibleUConcentr = visibleUConcentr; }
    public String getVisibleUPack() { return visibleUPack; }
    public void setVisibleUPack(String visibleUPack) { this.visibleUPack = visibleUPack; }
    public String getVisibleUMedCant() { return visibleUMedCant; }
    public void setVisibleUMedCant(String visibleUMedCant) { this.visibleUMedCant = visibleUMedCant; }
    public String getVisibleUDosifVol() { return visibleUDosifVol; }
    public void setVisibleUDosifVol(String visibleUDosifVol) { this.visibleUDosifVol = visibleUDosifVol; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getEstadoVal() { return estadoVal; }
    public void setEstadoVal(String estadoVal) { this.estadoVal = estadoVal; }
    public String getSnomedId() { return snomedId; }
    public void setSnomedId(String snomedId) { this.snomedId = snomedId; }
    public String getSnomedDsc() { return snomedDsc; }
    public void setSnomedDsc(String snomedDsc) { this.snomedDsc = snomedDsc; }
}
