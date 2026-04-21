package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.ffa")
public class ProdDnmaFfaEntity {

    @Id
    @Column("FFA_Id")
    private String ffaId;

    @Column("DESCRIPCION")
    private String descripcion;

    @Column("ESTADO_VAL")
    private String estadoVal;

    @Column("OBSERVACIONES")
    private String observaciones;

    @Column("SNOMEDId")
    private String snomedId;

    @Column("SNOMEDDSC")
    private String snomedDsc;

    @Column("ESTADO")
    private String estado;

    public String getFfaId() { return ffaId; }
    public void setFfaId(String ffaId) { this.ffaId = ffaId; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstadoVal() { return estadoVal; }
    public void setEstadoVal(String estadoVal) { this.estadoVal = estadoVal; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getSnomedId() { return snomedId; }
    public void setSnomedId(String snomedId) { this.snomedId = snomedId; }
    public String getSnomedDsc() { return snomedDsc; }
    public void setSnomedDsc(String snomedDsc) { this.snomedDsc = snomedDsc; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
