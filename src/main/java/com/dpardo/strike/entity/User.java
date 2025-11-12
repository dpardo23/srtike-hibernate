package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase Entidad que mapea la tabla 'user'.
 *
 * ¡Importante! Se usa @Table(name = "\"user\"") porque 'user'
 * es una palabra clave reservada en PostgreSQL y debe ser escapada.
 */
@Entity
@Table(name = "\"user\"") // "user" entre comillas para escapar la palabra clave
public class User {

    @Id
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 20)
    private String nombreUsuario;

    /** Mapea 'public.dominio_email', lo aplanamos a String. Es NULLEABLE. */
    // Le decimos a Hibernate que el tipo nativo de la columna es "dominio_email"
    @Column(name = "email", columnDefinition = "dominio_email")
    private String email;

    /** Mapea el tipo 'text' de PostgreSQL. */
    @Column(name = "contrasena", nullable = false, columnDefinition = "text")
    private String contrasena;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 20)
    private String apellidos;

    /**
     * Mapea el tipo 'timestamp' de PostgreSQL a 'java.time.LocalDateTime'.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    // --- Relaciones (se añadirán después) ---
    // @OneToMany(mappedBy = "user")
    // private Set<Session> sessions;
    //
    // @OneToMany(mappedBy = "user")
    // private Set<Log> logs;
    //
    // @OneToMany(mappedBy = "user")
    // private Set<UserRol> userRoles;

    // --- Constructor, Getters y Setters ---

    public User() {
    }

    // --- Getters y Setters ---

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}