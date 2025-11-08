package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase Entidad que mapea la tabla 'jugador' de la base de datos.
 *
 * Esta entidad incluye:
 * - Mapeo de tipos de datos (date -> LocalDate, bytea -> byte[], dominios -> String/Integer)
 * - Dos relaciones @ManyToOne (con Pais y Equipo).
 */
@Entity
@Table(name = "jugador")
public class Jugador {

    @Id
    @Column(name = "id_jugador")
    private Integer idJugador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Mapea la columna 'f_nacimiento'.
     * Es NULLEABLE (según el esquema).
     * Usamos java.time.LocalDate para el tipo 'date' de SQL.
     */
    @Column(name = "f_nacimiento")
    private LocalDate fNacimiento;

    /**
     * Mapea la columna 'sexo' (que era un dominio 'public.sexo').
     * Lo mapeamos a un Character (char) de Java.
     * Asumimos length = 1 (para 'M' o 'F').
     */
    @Column(name = "sexo", nullable = false, length = 1)
    private Character sexo;

    /**
     * Mapea la columna 'posicion' (dominio 'public.posicion_jugador').
     * Lo mapeamos a un String.
     */
    @Column(name = "posicion", nullable = false, length = 50) // 50 es una longitud segura
    private String posicion;

    /**
     * Mapea la columna 'altura' (dominio 'public.altura_cm').
     * Lo mapeamos a un Integer.
     */
    @Column(name = "altura", nullable = false)
    private Integer altura;

    /**
     * Mapea la columna 'peso' (dominio 'public.peso_kg').
     * Usamos BigDecimal para precisión decimal.
     */
    @Column(name = "peso", nullable = false)
    private BigDecimal peso;

    /**
     * Mapea la columna 'foto' de tipo 'bytea'.
     * @Lob indica a Hibernate que este es un "Large Object" (BLOB/bytea).
     * Es NULLEABLE.
     */
    @Lob
    @Column(name = "foto")
    private byte[] foto;

    /**
     * Mapea la columna 'estadisticas'.
     * Es NULLEABLE.
     */
    @Column(name = "estadisticas")
    private Integer estadisticas;


    // --- (1) RELACIÓN CON PAIS ---
    /**
     * Relación Muchos-a-Uno con la entidad Pais.
     * La columna 'pais' en la tabla 'jugador' es la clave foránea.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais", nullable = false)
    private Pais pais;

    // --- (2) RELACIÓN CON EQUIPO ---
    /**
     * Relación Muchos-a-Uno con la entidad Equipo.
     * La columna 'equipo' en la tabla 'jugador' es la clave foránea.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo", nullable = false)
    private Equipo equipo;

    // --- Constructor, Getters y Setters (Obligatorios) ---

    public Jugador() {
    }

    // --- Getters y Setters ---

    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Integer estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}