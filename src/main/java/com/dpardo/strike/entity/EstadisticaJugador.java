package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'estadistica_jugador'.
 *
 * Utiliza @EmbeddedId para una Clave Primaria Compuesta (EstadisticaJugadorId)
 * y @MapsId para enlazar parte de esa clave a la entidad Jugador.
 */
@Entity
@Table(name = "estadistica_jugador")
public class EstadisticaJugador {

    @EmbeddedId
    private EstadisticaJugadorId id;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles", nullable = false)
    private Integer goles;

    @Column(name = "partidos_jugados", nullable = false)
    private Integer partidosJugados;

    @Column(name = "asistencias", nullable = false)
    private Integer asistencias;

    /** Mapea el dominio 'public.tarjeta' */
    @Column(name = "tarjetas_amarillas", nullable = false)
    private Integer tarjetasAmarillas;

    /** Mapea el dominio 'public.tarjeta' */
    @Column(name = "tarjetas_rojas", nullable = false)
    private Integer tarjetasRojas;

    @Column(name = "minutos_jugados", nullable = false)
    private Integer minutosJugados;

    /** Mapea el dominio 'public.gol' */
    @Column(name = "goles_de_penal", nullable = false)
    private Integer golesDePenal;

    @Column(name = "faltas_recibidas", nullable = false)
    private Integer faltasRecibidas;

    @Column(name = "faltas_cometidas", nullable = false)
    private Integer faltasCometidas;

    /** Mapea el dominio 'public.temporada' */
    @Column(name = "temporada", nullable = false, length = 10) // ej: "2024/2025"
    private String temporada;


    /**
     * Relación clave (FK que es parte de la PK).
     * @MapsId("idJugador"): Conecta esta relación
     * con el campo 'idJugador' dentro de la clase EstadisticaJugadorId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idJugador")
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    // --- Constructor, Getters y Setters ---

    public EstadisticaJugador() {
    }

    // --- Getters y Setters ---

    public EstadisticaJugadorId getId() {
        return id;
    }

    public void setId(EstadisticaJugadorId id) {
        this.id = id;
    }

    public Integer getGoles() {
        return goles;
    }

    public void setGoles(Integer goles) {
        this.goles = goles;
    }

    public Integer getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(Integer partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Integer getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public void setTarjetasAmarillas(Integer tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public Integer getTarjetasRojas() {
        return tarjetasRojas;
    }

    public void setTarjetasRojas(Integer tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    public Integer getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(Integer minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public Integer getGolesDePenal() {
        return golesDePenal;
    }

    public void setGolesDePenal(Integer golesDePenal) {
        this.golesDePenal = golesDePenal;
    }

    public Integer getFaltasRecibidas() {
        return faltasRecibidas;
    }

    public void setFaltasRecibidas(Integer faltasRecibidas) {
        this.faltasRecibidas = faltasRecibidas;
    }

    public Integer getFaltasCometidas() {
        return faltasCometidas;
    }

    public void setFaltasCometidas(Integer faltasCometidas) {
        this.faltasCometidas = faltasCometidas;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}