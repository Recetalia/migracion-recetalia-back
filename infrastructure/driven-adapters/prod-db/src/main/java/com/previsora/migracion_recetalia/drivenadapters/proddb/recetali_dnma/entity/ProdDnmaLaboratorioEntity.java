package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.laboratorio")
public class ProdDnmaLaboratorioEntity {

    @Id
    @Column("LAB_Id")
    private String labId;

    @Column("NOMBRE")
    private String nombre;

    @Column("NOMBREABR")
    private String nombreAbr;

    @Column("RUT")
    private String rut;

    @Column("ESTADOVAL")
    private String estadoVal;

    @Column("OBSERVACION")
    private String observacion;

    @Column("URL")
    private String url;

    @Column("ESTADO")
    private String estado;

    public String getLabId() { return labId; }
    public void setLabId(String labId) { this.labId = labId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombreAbr() { return nombreAbr; }
    public void setNombreAbr(String nombreAbr) { this.nombreAbr = nombreAbr; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getEstadoVal() { return estadoVal; }
    public void setEstadoVal(String estadoVal) { this.estadoVal = estadoVal; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
