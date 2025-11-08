package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'clasificacion'.
 *
 * Implementa Serializable y los métodos equals() y hashCode().
 */
@Embeddable
public class ClasificacionId implements Serializable {

    @Column(name = "id_clasificacion", nullable = false)
    private Integer idClasificacion;

    @Column(name = "id_equipo", nullable = false)
    private Integer idEquipo; // Mapea a la FK id_equipo

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public ClasificacionId() {
    }

    public ClasificacionId(Integer idClasificacion, Integer idEquipo) {
        this.idClasificacion = idClasificacion;
        this.idEquipo = idEquipo;
    }

    // --- Getters y Setters ---

    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    // --- equals() y hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasificacionId that = (ClasificacionId) o;
        return Objects.equals(idClasificacion, that.idClasificacion) &&
                Objects.equals(idEquipo, that.idEquipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClasificacion, idEquipo);
    }
}