package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'historial'.
 *
 * Esta entidad demuestra cómo enlazar a otra entidad (@OneToOne)
 * que utiliza una clave primaria compuesta (Partido).
 */
@Entity
@Table(name = "historial")
public class Historial {

    @Id
    @Column(name = "id_historial")
    private Integer idHistorial;

    @Column(name = "jornada")
    private Integer jornada;

    @Column(name = "estadio", length = 20)
    private String estadio;

    @Column(name = "arbitro", length = 20)
    private String arbitro;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles_local", nullable = false)
    private Integer golesLocal;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles_visitante", nullable = false)
    private Integer golesVisitante;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "penales_local", nullable = false)
    private Integer penalesLocal;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "penales_visitante", nullable = false)
    private Integer penalesVisitante;

    /** Mapea el dominio 'public.minuto' */
    @Column(name = "minuto", nullable = false)
    private Integer minuto;

    /** Mapea el dominio 'public.minuto' */
    @Column(name = "minuto_adicional", nullable = false)
    private Integer minutoAdicional;

    /**
     * Relación Uno-a-Uno con Partido.
     * Un historial pertenece exactamente a un partido.
     *
     * @JoinColumns: Se usa porque la entidad 'Partido' tiene una
     * clave primaria compuesta. Debemos especificar CADA columna
     * que compone la clave foránea.
     *
     * - name = "partido_local": Nombre de la columna en la tabla 'historial'.
     * - referencedColumnName = "equipo_local": Nombre de la columna
     * en la clave primaria de la tabla 'partido'.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "partido_local", referencedColumnName = "equipo_local", nullable = false),
            @JoinColumn(name = "partido_visitante", referencedColumnName = "equipo_visitante", nullable = false),
            @JoinColumn(name = "partido_fecha", referencedColumnName = "fecha", nullable = false)
    })
    private Partido partido;

    // --- Constructor, Getters y Setters ---

    public Historial() {
    }

    // --- Getters y Setters ---

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Integer getJornada() {
        return jornada;
    }

    public void setJornada(Integer jornada) {
        this.jornada = jornada;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public Integer getPenalesLocal() {
        return penalesLocal;
    }

    public void setPenalesLocal(Integer penalesLocal) {
        this.penalesLocal = penalesLocal;
    }

    public Integer getPenalesVisitante() {
        return penalesVisitante;
    }

    public void setPenalesVisitante(Integer penalesVisitante) {
        this.penalesVisitante = penalesVisitante;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public Integer getMinutoAdicional() {
        return minutoAdicional;
    }

    public void setMinutoAdicional(Integer minutoAdicional) {
        this.minutoAdicional = minutoAdicional;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}