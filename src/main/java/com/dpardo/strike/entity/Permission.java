package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'permission'.
 * Define un permiso específico en el sistema.
 */
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @Column(name = "id_permission", nullable = false)
    private Integer idPermission;

    @Column(name = "nombre_permiso", nullable = false, length = 20)
    private String nombrePermiso;

    @Column(name = "descripcion", columnDefinition = "text") // Nulleable
    private String descripcion;

    // --- Constructor, Getters y Setters ---

    public Permission() {
    }

    // --- Getters y Setters ---

    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}