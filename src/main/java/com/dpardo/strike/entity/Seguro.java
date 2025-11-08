package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'seguro'.
 *
 * Utiliza @EmbeddedId para una Clave Primaria Compuesta (SeguroId)
 * y @MapsId para enlazar parte de esa clave a la entidad Jugador.
 */
@Entity
@Table(name = "seguro")
public class Seguro {

    @EmbeddedId
    private SeguroId id;

    /**
     * Mapea el dominio 'public.gpo_sanguineo'.
     * Lo aplanamos a un String. Es NULLEABLE.
     * (Ej: "O+", "A-", "AB+")
     */
    @Column(name = "abo_rh", length = 3)
    private String aboRh;

    /**
     * Relación clave (FK que es parte de la PK).
     * @MapsId("idJugador"): Conecta esta relación
     * con el campo 'idJugador' dentro de la clase SeguroId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idJugador")
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    // --- Constructor, Getters y Setters ---

    public Seguro() {
    }

    // --- Getters y Setters ---

    public SeguroId getId() {
        return id;
    }

    public void setId(SeguroId id) {
        this.id = id;
    }

    public String getAboRh() {
        return aboRh;
    }

    public void setAboRh(String aboRh) {
        this.aboRh = aboRh;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}