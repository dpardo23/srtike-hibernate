package com.dpardo.strike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase Entidad que mapea la tabla 'pais' de la base de datos.
 *
 * VERSIÓN ACTUALIZADA:
 * Se añaden las restricciones 'nullable = false' (NOT NULL) y 'length'
 * para que coincida 100% con el esquema de la BD.
 */
@Entity
@Table(name = "pais")
public class Pais {

    /**
     * Clave Primaria (PK). Mapea 'cod_fifa'.
     * @Id ya implica que no puede ser nulo (nullable = false).
     * El tipo "cod_fifa" era un varchar(3) en el esquema original.
     * Si tu tipo personalizado era diferente, puedes cambiar el 'length'.
     */
    @Id
    @Column(name = "cod_fifa", length = 3) // Longitud de 3 para códigos FIFA (ej. "ARG")
    private String codFifa;

    /**
     * Mapea la columna 'nombre'.
     * - nullable = false (es NOT NULL, como marcan las 'x')
     * - length = 50     (es varchar(50) en el esquema)
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    /**
     * Mapea la columna 'continente'.
     * - nullable = false (es NOT NULL)
     * - length = 50     (es varchar(50) en el esquema)
     */
    @Column(name = "continente", nullable = false, length = 50)
    private String continente;

    // --- Constructores, Getters y Setters (Obligatorios) ---

    /**
     * Constructor vacío OBLIGATORIO.
     * Hibernate lo necesita para crear instancias de esta clase.
     */
    public Pais() {
    }

    // --- Getters y Setters OBLIGATORIOS ---

    public String getCodFifa() {
        return codFifa;
    }

    public void setCodFifa(String codFifa) {
        this.codFifa = codFifa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "codFifa='" + codFifa + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}