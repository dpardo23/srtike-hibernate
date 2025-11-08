package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase Entidad que mapea la tabla 'rol'.
 */
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @Column(name = "id_rol", nullable = false)
    private Integer idRol;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    /**
     * Mapea el tipo 'text' de PostgreSQL.
     */
    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    /**
     * Mapea el tipo 'timestamp' de PostgreSQL a 'java.time.LocalDateTime'.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    // --- Relaciones (se añadirán después) ---
    // @OneToMany(mappedBy = "rol")
    // private Set<UserRol> userRoles;
    //
    // @OneToMany(mappedBy = "rol")
    // private Set<RolPermission> rolPermissions;

    // --- Constructor, Getters y Setters ---

    public Rol() {
    }

    // --- Getters y Setters ---

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}