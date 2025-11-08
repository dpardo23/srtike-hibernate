package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * Clase Entidad que mapea la tabla 'partido'.
 *
 * Esta es la entidad más compleja:
 * 1. Usa @EmbeddedId para una Clave Primaria Compuesta (PartidoId).
 * 2. Usa @MapsId para mapear partes de la clave compuesta
 * directamente a relaciones @ManyToOne (equipoLocal, equipoVisitante).
 * 3. Tiene una relación @ManyToOne "normal" (liga).
 */
@Entity
@Table(name = "partido")
public class Partido {

    /**
     * La Clave Primaria Compuesta.
     */
    @EmbeddedId
    private PartidoId id;

    /**
     * Mapea la columna 'hora'.
     * - NOT NULL
     * - Usamos java.time.LocalTime para el tipo 'time' de SQL.
     */
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    /**
     * Mapea la columna 'historial'.
     * - NOT NULL
     * - Es ÚNICA (según el esquema 'ung_partido_num_partido')
     */
    @Column(name = "historial", nullable = false, unique = true)
    private Integer historial;

    // --- (1) RELACIÓN CLAVE (FK que es parte de la PK) ---
    /**
     * Relación con Equipo (Local).
     * @MapsId("equipoLocalId"): Conecta esta relación
     * con el campo 'equipoLocalId' dentro de la clase PartidoId.
     * Le decimos a Hibernate: "Esta relación ES parte de la clave".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipoLocalId")
    @JoinColumn(name = "equipo_local", nullable = false)
    private Equipo equipoLocal;

    // --- (2) RELACIÓN CLAVE (FK que es parte de la PK) ---
    /**
     * Relación con Equipo (Visitante).
     * @MapsId("equipoVisitanteId"): Conecta esta relación
     * con el campo 'equipoVisitanteId' dentro de la clase PartidoId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipoVisitanteId")
    @JoinColumn(name = "equipo_visitante", nullable = false)
    private Equipo equipoVisitante;

    // --- (3) RELACIÓN NO CLAVE (FK normal) ---
    /**
     * Relación con Liga.
     * Esta es una relación @ManyToOne estándar, igual que
     * las que vimos en Jugador o Equipo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_liga", nullable = false)
    private Liga liga;

    // --- Constructor, Getters y Setters ---

    public Partido() {
    }

    // --- Getters y Setters ---

    public PartidoId getId() {
        return id;
    }

    public void setId(PartidoId id) {
        this.id = id;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getHistorial() {
        return historial;
    }

    public void setHistorial(Integer historial) {
        this.historial = historial;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }
}