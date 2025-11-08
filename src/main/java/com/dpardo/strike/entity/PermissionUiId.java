package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'permission_ui'.
 */
@Embeddable
public class PermissionUiId implements Serializable {

    @Column(name = "id_ui", nullable = false)
    private Integer idUi; // Mapea a la FK id_ui

    @Column(name = "id_permission", nullable = false)
    private Integer idPermission; // Mapea a la FK id_permission

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public PermissionUiId() {
    }

    public PermissionUiId(Integer idUi, Integer idPermission) {
        this.idUi = idUi;
        this.idPermission = idPermission;
    }

    // --- Getters y Setters ---

    public Integer getIdUi() {
        return idUi;
    }

    public void setIdUi(Integer idUi) {
        this.idUi = idUi;
    }

    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    // --- equals() y hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionUiId that = (PermissionUiId) o;
        return Objects.equals(idUi, that.idUi) &&
                Objects.equals(idPermission, that.idPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUi, idPermission);
    }
}