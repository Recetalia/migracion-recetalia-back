package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.via_admin")
public class DevDnmaViaAdminEntity {

    @Id
    @Column("VIA_ADMIN_Id")
    private String viaAdminId;

    @Column("VIA_ADMIN_DSC")
    private String viaAdminDsc;

    @Column("ESTADOVAL")
    private String estadoVal;

    @Column("OBSERVACION")
    private String observacion;

    @Column("SNOMED_Id")
    private String snomedId;

    @Column("SNOMED_DSC")
    private String snomedDsc;

    @Column("ESTADO")
    private String estado;

    public String getViaAdminId() { return viaAdminId; }
    public void setViaAdminId(String viaAdminId) { this.viaAdminId = viaAdminId; }
    public String getViaAdminDsc() { return viaAdminDsc; }
    public void setViaAdminDsc(String viaAdminDsc) { this.viaAdminDsc = viaAdminDsc; }
    public String getEstadoVal() { return estadoVal; }
    public void setEstadoVal(String estadoVal) { this.estadoVal = estadoVal; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getSnomedId() { return snomedId; }
    public void setSnomedId(String snomedId) { this.snomedId = snomedId; }
    public String getSnomedDsc() { return snomedDsc; }
    public void setSnomedDsc(String snomedDsc) { this.snomedDsc = snomedDsc; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
