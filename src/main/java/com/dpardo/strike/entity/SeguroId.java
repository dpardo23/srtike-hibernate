package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'seguro'.
 *
 * Implementa Serializable y los métodos equals() y hashCode().
 */
@Embeddable
public class SeguroId implements Serializable {

    @Column(name = "num_poliza", nullable = false)
    private Integer numPoliza;

    @Column(name = "id_jugador", nullable = false)
    private Integer idJugador; // Mapea a la FK id_jugador

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public SeguroId() {
    }

    public SeguroId(Integer numPoliza, Integer idJugador) {
        this.numPoliza = numPoliza;
        this.idJugador = idJugador;
    }

    // --- Getters y Setters ---

    public Integer getNumPoliza() {
        return numPoliza;
    }

    public void setNumPoliza(Integer numPoliza) {
        this.numPoliza = numPoliza;
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
        SeguroId seguroId = (SeguroId) o;
        return Objects.equals(numPoliza, seguroId.numPoliza) &&
                Objects.equals(idJugador, seguroId.idJugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numPoliza, idJugador);
    }
}