package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'rol_permission'.
 */
@Embeddable
public class RolPermissionId implements Serializable {

    @Column(name = "id_rol", nullable = false)
    private Integer idRol; // Mapea a la FK id_rol

    @Column(name = "id_permission", nullable = false)
    private Integer idPermission; // Mapea a la FK id_permission

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public RolPermissionId() {
    }

    public RolPermissionId(Integer idRol, Integer idPermission) {
        this.idRol = idRol;
        this.idPermission = idPermission;
    }

    // --- Getters y Setters ---

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
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
        RolPermissionId that = (RolPermissionId) o;
        return Objects.equals(idRol, that.idRol) &&
                Objects.equals(idPermission, that.idPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol, idPermission);
    }
}