package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Clase Entidad que mapea la tabla 'equipo' de la base de datos.
 *
 * Incluye una relación @ManyToOne con la entidad Pais.
 */
@Entity
@Table(name = "equipo")
public class Equipo {

    /**
     * Clave Primaria (PK). Mapea 'id_equipo'.
     */
    @Id
    @Column(name = "id_equipo")
    private Integer idEquipo; // Usamos Integer, un objeto, que es más flexible.

    /**
     * Mapea la columna 'nombre'.
     * - NOT NULL
     * - varchar(20)
     */
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    /**
     * Mapea la columna 'ciudad'.
     * - NOT NULL
     * - varchar(20)
     */
    @Column(name = "ciudad", nullable = false, length = 20)
    private String ciudad;

    /**
     * Mapea la columna 'f_fundacion'.
     * - Es NULLEABLE (no tiene la 'x' en el esquema)
     */
    @Column(name = "f_fundacion")
    private LocalDate fFundacion; // Usamos java.time.LocalDate para 'date'

    /**
     * Mapea la columna 'director_tecnico'.
     * - NOT NULL
     * - varchar(50)
     */
    @Column(name = "director_tecnico", nullable = false, length = 50)
    private String directorTecnico;

    // --- (1) LA RELACIÓN DE CLAVE FORÁNEA ---
    /**
     * Relación Muchos-a-Uno con la entidad Pais.
     *
     * @ManyToOne: Le dice a Hibernate que muchos 'Equipo' se
     * relacionan con un 'Pais'.
     *
     * @JoinColumn: Especifica CÓMO se hace la unión.
     * - name = "pais": Es el nombre de la columna de clave foránea
     * en la tabla 'equipo'.
     * - nullable = false: Indica que esta relación es obligatoria
     * (un equipo DEBE tener un país).
     */
    @ManyToOne(fetch = FetchType.LAZY) // (fetch = FetchType.LAZY es una optimización)
    @JoinColumn(name = "pais", nullable = false)
    private Pais pais;

    // --- Constructores, Getters y Setters (Obligatorios) ---

    public Equipo() {
    }

    // --- Getters y Setters ---

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getfFundacion() {
        return fFundacion;
    }

    public void setfFundacion(LocalDate fFundacion) {
        this.fFundacion = fFundacion;
    }

    public String getDirectorTecnico() {
        return directorTecnico;
    }

    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}