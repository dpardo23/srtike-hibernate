package com.dpardo.strike.entity;

import jakarta.persistence.*;

/**
 * Clase Entidad que mapea la tabla 'session'.
 *
 * VERSIÓN CORREGIDA:
 * Mapea la clave primaria compuesta (id_session, id_user)
 * usando @EmbeddedId y @MapsId.
 */
@Entity
@Table(name = "session")
public class Session {

    /**
     * La Clave Primaria Compuesta.
     */
    @EmbeddedId
    private SessionId id;

    /**
     * Mapea la columna 'user_addr' (tipo 'inet' en PG)
     * a un String en Java.
     */
    @Column(name = "user_addr", nullable = false)
    private String userAddr; // 'inet' se mapea a String

    @Column(name = "user_port", nullable = false)
    private Integer userPort;

    /**
     * Mapea 'pid'. Es NULLEABLE.
     */
    @Column(name = "pid")
    private Integer pid;

    // --- (1) RELACIÓN CON USER (que es parte de la PK) ---
    /**
     * @MapsId("idUser"): Conecta esta relación
     * con el campo 'idUser' dentro de la clave SessionId.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUser") // "idUser" debe coincidir con el nombre de la variable en SessionId.java
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    // --- Constructor, Getters y Setters ---

    public Session() {
    }

    // --- Getters y Setters ---

    public SessionId getId() {
        return id;
    }

    public void setId(SessionId id) {
        this.id = id;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public Integer getUserPort() {
        return userPort;
    }

    public void setUserPort(Integer userPort) {
        this.userPort = userPort;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}