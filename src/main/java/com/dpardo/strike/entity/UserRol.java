package com.dpardo.strike.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase Entidad que mapea la tabla 'user_rol'.
 *
 * Esta es la entidad "intermedia" que maneja la relación
 * Muchos-a-Muchos entre User y Rol, añadiendo columnas extra.
 */
@Entity
@Table(name = "user_rol")
public class UserRol {

    @EmbeddedId
    private UserRolId id;

    /**
     * Mapea el tipo 'timestamp' de PostgreSQL.
     */
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    /**
     * Mapea el tipo 'boolean' de PostgreSQL.
     * Es NULLEABLE.
     */
    @Column(name = "activo")
    private Boolean activo;

    // --- (1) RELACIÓN CON USER ---
    /**
     * @MapsId("idUser"): Conecta esta relación
     * con el campo 'idUser' dentro de la clave UserRolId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUser")
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    // --- (2) RELACIÓN CON ROL ---
    /**
     * @MapsId("idRol"): Conecta esta relación
     * con el campo 'idRol' dentro de la clave UserRolId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRol")
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    // --- Constructor, Getters y Setters ---

    public UserRol() {
    }

    // --- Getters y Setters ---

    public UserRolId getId() {
        return id;
    }

    public void setId(UserRolId id) {
        this.id = id;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}