package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'clasificacion'.
 *
 * Utiliza @EmbeddedId para una Clave Primaria Compuesta (ClasificacionId)
 * y @MapsId para enlazar parte de esa clave a la entidad Equipo.
 */
@Entity
@Table(name = "clasificacion")
public class Clasificacion {

    @EmbeddedId
    private ClasificacionId id;

    @Column(name = "partidos_jugados", nullable = false)
    private Integer partidosJugados;

    /** Mapea el dominio 'public.resultado' */
    @Column(name = "victorias", nullable = false)
    private Integer victorias;

    /** Mapea el dominio 'public.resultado' */
    @Column(name = "empates", nullable = false)
    private Integer empates;

    /** Mapea el dominio 'public.resultado' */
    @Column(name = "derrotas", nullable = false)
    private Integer derrotas;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles_a_favor", nullable = false)
    private Integer golesAFavor;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles_en_contra", nullable = false)
    private Integer golesEnContra;

    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    /**
     * Relación clave (FK que es parte de la PK).
     * @MapsId("idEquipo"): Conecta esta relación
     * con el campo 'idEquipo' dentro de la clase ClasificacionId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEquipo")
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    // --- Constructor, Getters y Setters ---

    public Clasificacion() {
    }

    // --- Getters y Setters ---

    public ClasificacionId getId() {
        return id;
    }

    public void setId(ClasificacionId id) {
        this.id = id;
    }

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getVictorias() {
        return victorias;
    }

    public void setVictorias(Integer victorias) {
        this.victorias = victorias;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer empates) {
        this.empates = empates;
    }

    public Integer getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }

    public Integer getGolesAFavor() {
        return golesAFavor;
    }

    public void setGolesAFavor(Integer golesAFavor) {
        this.golesAFavor = golesAFavor;
    }

    public Integer getGolesEnContra() {
        return golesEnContra;
    }

    public void setGolesEnContra(Integer golesEnContra) {
        this.golesEnContra = golesEnContra;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}