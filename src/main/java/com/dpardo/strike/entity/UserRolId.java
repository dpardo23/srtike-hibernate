package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'user_rol'.
 *
 * Implementa Serializable y los métodos equals() y hashCode().
 */
@Embeddable
public class UserRolId implements Serializable {

    @Column(name = "id_user", nullable = false)
    private Integer idUser; // Mapea a la FK id_user

    @Column(name = "id_rol", nullable = false)
    private Integer idRol; // Mapea a la FK id_rol

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public UserRolId() {
    }

    public UserRolId(Integer idUser, Integer idRol) {
        this.idUser = idUser;
        this.idRol = idRol;
    }

    // --- Getters y Setters ---

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    // --- equals() y hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolId userRolId = (UserRolId) o;
        return Objects.equals(idUser, userRolId.idUser) &&
                Objects.equals(idRol, userRolId.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRol);
    }
}