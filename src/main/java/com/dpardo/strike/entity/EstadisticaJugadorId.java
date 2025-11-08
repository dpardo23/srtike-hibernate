package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'estadistica_jugador'.
 *
 * Implementa Serializable y los métodos equals() y hashCode().
 */
@Embeddable
public class EstadisticaJugadorId implements Serializable {

    @Column(name = "id_estadistica", nullable = false)
    private Integer idEstadistica;

    @Column(name = "id_jugador", nullable = false)
    private Integer idJugador; // Mapea a la FK id_jugador

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public EstadisticaJugadorId() {
    }

    public EstadisticaJugadorId(Integer idEstadistica, Integer idJugador) {
        this.idEstadistica = idEstadistica;
        this.idJugador = idJugador;
    }

    // --- Getters y Setters ---

    public Integer getIdEstadistica() {
        return idEstadistica;
    }

    public void setIdEstadistica(Integer idEstadistica) {
        this.idEstadistica = idEstadistica;
    }

    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    // --- equals() y hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadisticaJugadorId that = (EstadisticaJugadorId) o;
        return Objects.equals(idEstadistica, that.idEstadistica) &&
                Objects.equals(idJugador, that.idJugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEstadistica, idJugador);
    }
}