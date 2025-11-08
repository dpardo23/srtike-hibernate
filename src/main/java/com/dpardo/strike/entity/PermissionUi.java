package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'permission_ui'.
 *
 * Esta es la entidad "intermedia" que maneja la relación
 * Muchos-a-Muchos entre Permission y Ui, añadiendo la columna 'activo'.
 */
@Entity
@Table(name = "permission_ui")
public class PermissionUi {

    @EmbeddedId
    private PermissionUiId id;

    /**
     * Mapea el tipo 'boolean' de PostgreSQL.
     * Es NULLEABLE.
     */
    @Column(name = "activo")
    private Boolean activo;

    // --- (1) RELACIÓN CON UI ---
    /**
     * @MapsId("idUi"): Conecta esta relación
     * con el campo 'idUi' dentro de la clave PermissionUiId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUi")
    @JoinColumn(name = "id_ui", nullable = false)
    private Ui ui;

    // --- (2) RELACIÓN CON PERMISSION ---
    /**
     * @MapsId("idPermission"): Conecta esta relación
     * con el campo 'idPermission' dentro de la clave PermissionUiId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPermission")
    @JoinColumn(name = "id_permission", nullable = false)
    private Permission permission;

    // --- Constructor, Getters y Setters ---

    public PermissionUi() {
    }

    // --- Getters y Setters ---

    public PermissionUiId getId() {
        return id;
    }

    public void setId(PermissionUiId id) {
        this.id = id;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Ui getUi() {
        return ui;
    }

    public void setUi(Ui ui) {
        this.ui = ui;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}