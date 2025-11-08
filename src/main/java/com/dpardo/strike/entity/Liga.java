package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'liga' de la base de datos.
 *
 * Incluye una relación @ManyToOne con la entidad Pais.
 */
@Entity
@Table(name = "liga")
public class Liga {

    @Id
    @Column(name = "id_liga")
    private Integer idLiga;

    /**
     * Mapea la columna 'nombre'.
     * - NOT NULL
     * - varchar(20)
     */
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    /**
     * Mapea la columna 'tipo' (dominio 'public.tipo_liga').
     * Lo mapeamos a un String. Es NULLEABLE.
     * Le damos una longitud segura.
     */
    @Column(name = "tipo", length = 50)
    private String tipo;

    /**
     * Relación Muchos-a-Uno con la entidad Pais.
     * La columna 'id_pais' en la tabla 'liga' es la clave foránea.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;

    // --- Constructor, Getters y Setters (Obligatorios) ---

    public Liga() {
    }

    // --- Getters y Setters ---

    public Integer getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Integer idLiga) {
        this.idLiga = idLiga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}