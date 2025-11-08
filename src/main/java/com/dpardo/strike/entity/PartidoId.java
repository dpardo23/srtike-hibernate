package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa la Clave Primaria Compuesta (CPK) de la tabla 'partido'.
 *
 * Una clase de ID compuesta DEBE:
 * 1. Ser 'public'.
 * 2. Tener un constructor público sin argumentos.
 * 3. Implementar 'java.io.Serializable'.
 * 4. Implementar los métodos 'equals()' y 'hashCode()'.
 */
@Embeddable // Indica que esta clase se puede incrustar en otra entidad
public class PartidoId implements Serializable {

    // Nota: Los nombres aquí deben coincidir con los de las columnas
    // o con los @MapsId de la entidad Partido.
    // Usamos los tipos de Java, no los de la BD.

    @Column(name = "equipo_local", nullable = false)
    private Integer equipoLocalId; // Mapea a equipo_local

    @Column(name = "equipo_visitante", nullable = false)
    private Integer equipoVisitanteId; // Mapea a equipo_visitante

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // Mapea a fecha

    // --- Constructor, equals y hashCode (Obligatorios) ---

    public PartidoId() {
    }

    // Constructor para facilidad de uso
    public PartidoId(Integer equipoLocalId, Integer equipoVisitanteId, LocalDate fecha) {
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.fecha = fecha;
    }

    // --- Getters y Setters ---

    public Integer getEquipoLocalId() {
        return equipoLocalId;
    }

    public void setEquipoLocalId(Integer equipoLocalId) {
        this.equipoLocalId = equipoLocalId;
    }

    public Integer getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public void setEquipoVisitanteId(Integer equipoVisitanteId) {
        this.equipoVisitanteId = equipoVisitanteId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // --- equals() y hashCode() (Obligatorios para CPK) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidoId partidoId = (PartidoId) o;
        return Objects.equals(equipoLocalId, partidoId.equipoLocalId) &&
                Objects.equals(equipoVisitanteId, partidoId.equipoVisitanteId) &&
                Objects.equals(fecha, partidoId.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipoLocalId, equipoVisitanteId, fecha);
    }
}