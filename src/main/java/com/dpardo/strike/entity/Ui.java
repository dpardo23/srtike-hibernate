package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'ui'.
 * Define un componente de la interfaz de usuario (ej. un botón, una ventana).
 */
@Entity
@Table(name = "ui")
public class Ui {

    @Id
    @Column(name = "id_ui", nullable = false)
    private Integer idUi;

    @Column(name = "cod_componente", nullable = false, length = 100)
    private String codComponente;

    @Column(name = "tipo_componente", nullable = false, length = 100)
    private String tipoComponente;

    @Column(name = "descripcion", length = 255) // Nulleable
    private String descripcion;

    // --- Constructor, Getters y Setters ---

    public Ui() {
    }

    // --- Getters y Setters ---

    public Integer getIdUi() {
        return idUi;
    }

    public void setIdUi(Integer idUi) {
        this.idUi = idUi;
    }

    public String getCodComponente() {
        return codComponente;
    }

    public void setCodComponente(String codComponente) {
        this.codComponente = codComponente;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}