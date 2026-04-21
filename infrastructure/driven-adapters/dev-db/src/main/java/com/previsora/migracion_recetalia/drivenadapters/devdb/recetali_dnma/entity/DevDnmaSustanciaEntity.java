package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.sustancia")
public class DevDnmaSustanciaEntity {

    @Id
    @Column("SUSTANCIA_ID")
    private String sustanciaId;

    @Column("SUSTANCIA_DSC")
    private String sustanciaDsc;

    @Column("DCI")
    private String dci;

    @Column("RIESGO_TERATO")
    private String riesgoTerato;

    @Column("SUSTANCIA_Estado")
    private String sustanciaEstado;

    @Column("SUSTANCIA_EstValidacion")
    private String sustanciaEstValidacion;

    @Column("COMERCIALIZADO")
    private String comercializado;

    @Column("DESCRIPCIONES")
    private String descripciones;

    public String getSustanciaId() { return sustanciaId; }
    public void setSustanciaId(String sustanciaId) { this.sustanciaId = sustanciaId; }
    public String getSustanciaDsc() { return sustanciaDsc; }
    public void setSustanciaDsc(String sustanciaDsc) { this.sustanciaDsc = sustanciaDsc; }
    public String getDci() { return dci; }
    public void setDci(String dci) { this.dci = dci; }
    public String getRiesgoTerato() { return riesgoTerato; }
    public void setRiesgoTerato(String riesgoTerato) { this.riesgoTerato = riesgoTerato; }
    public String getSustanciaEstado() { return sustanciaEstado; }
    public void setSustanciaEstado(String sustanciaEstado) { this.sustanciaEstado = sustanciaEstado; }
    public String getSustanciaEstValidacion() { return sustanciaEstValidacion; }
    public void setSustanciaEstValidacion(String sustanciaEstValidacion) { this.sustanciaEstValidacion = sustanciaEstValidacion; }
    public String getComercializado() { return comercializado; }
    public void setComercializado(String comercializado) { this.comercializado = comercializado; }
    public String getDescripciones() { return descripciones; }
    public void setDescripciones(String descripciones) { this.descripciones = descripciones; }
}
