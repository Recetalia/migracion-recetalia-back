package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recetali_dnma.tf")
public class DevDnmaTfEntity {

    @Id
    @Column("TF_Id")
    private String tfId;

    @Column("TF_DSC")
    private String tfDsc;

    @Column("GENERICA")
    private String generica;

    @Column("TFG_Id")
    private String tfgId;

    @Column("TF_Estado")
    private String tfEstado;

    @Column("TF_EstValidacion")
    private String tfEstValidacion;

    @Column("COMERCIALIZADO")
    private String comercializado;

    @Column("DESCRIPCIONES")
    private String descripciones;

    public String getTfId() { return tfId; }
    public void setTfId(String tfId) { this.tfId = tfId; }
    public String getTfDsc() { return tfDsc; }
    public void setTfDsc(String tfDsc) { this.tfDsc = tfDsc; }
    public String getGenerica() { return generica; }
    public void setGenerica(String generica) { this.generica = generica; }
    public String getTfgId() { return tfgId; }
    public void setTfgId(String tfgId) { this.tfgId = tfgId; }
    public String getTfEstado() { return tfEstado; }
    public void setTfEstado(String tfEstado) { this.tfEstado = tfEstado; }
    public String getTfEstValidacion() { return tfEstValidacion; }
    public void setTfEstValidacion(String tfEstValidacion) { this.tfEstValidacion = tfEstValidacion; }
    public String getComercializado() { return comercializado; }
    public void setComercializado(String comercializado) { this.comercializado = comercializado; }
    public String getDescripciones() { return descripciones; }
    public void setDescripciones(String descripciones) { this.descripciones = descripciones; }
}
