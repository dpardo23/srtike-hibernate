package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'rol_permission'.
 *
 * Esta es la entidad "intermedia" que maneja la relación
 * Muchos-a-Muchos entre Rol y Permission, añadiendo la columna 'activo'.
 */
@Entity
@Table(name = "rol_permission")
public class RolPermission {

    @EmbeddedId
    private RolPermissionId id;

    /**
     * Mapea el tipo 'boolean' de PostgreSQL.
     * Es NULLEABLE.
     */
    @Column(name = "activo")
    private Boolean activo;

    // --- (1) RELACIÓN CON ROL ---
    /**
     * @MapsId("idRol"): Conecta esta relación
     * con el campo 'idRol' dentro de la clave RolPermissionId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    // --- (2) RELACIÓN CON PERMISSION ---
    /**
     * @MapsId("idPermission"): Conecta esta relación
     * con el campo 'idPermission' dentro de la clave RolPermissionId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermission")
    @JoinColumn(name = "id_permission", nullable = false)
    private Permission permission;

    // --- Constructor, Getters y Setters ---

    public RolPermission() {
    }

    // --- Getters y Setters ---

    public RolPermissionId getId() {
        return id;
    }

    public void setId(RolPermissionId id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}