package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'action'.
 * Define un tipo de acción de auditoría (ej: "INSERT", "UPDATE").
 */
@Entity
@Table(name = "action")
public class Action {

    @Id
    @Column(name = "id_action", nullable = false)
    private Integer idAction;

    /**
     * Mapea la columna 'nombre_accion'.
     * Es NOT NULL y ÚNICA (según el esquema).
     */
    @Column(name = "nombre_accion", nullable = false, unique = true, length = 255)
    private String nombreAccion;

    /**
     * Mapea el tipo 'text' de PostgreSQL.
     * Es NULLEABLE.
     */
    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    // --- Constructor, Getters y Setters ---

    public Action() {
    }

    // --- Getters y Setters ---

    public Integer getIdAction() {
        return idAction;
    }

    public void setIdAction(Integer idAction) {
        this.idAction = idAction;
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}