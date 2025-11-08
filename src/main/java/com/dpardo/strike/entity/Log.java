package com.dpardo.strike.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * Clase Entidad que mapea la tabla 'log' (auditoría).
 *
 * Mapea relaciones por clave única (referencedColumnName) y
 * tipos de datos especiales como 'jsonb' y 'timestamptz'.
 */
@Entity
@Table(name = "log")
public class Log {

    /**
     * Mapea la clave primaria 'id_log'
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * le dice a Hibernate que esta columna es auto-incremental
     * (coincide con "GENERATED ALWAYS AS IDENTITY" de PostgreSQL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log", nullable = false)
    private Integer idLog;

    @Column(name = "tabla_afectada", length = 255)
    private String tablaAfectada;

    /**
     * Mapea 'timestamptz' (timestamp CON zona horaria).
     * Usamos OffsetDateTime que preserva la información de la zona.
     * La BD le da un valor por defecto (DEFAULT now()).
     */
    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    /**
     * Mapea 'jsonb' a un String.
     * 'columnDefinition = "jsonb"' le indica a Hibernate
     * el tipo de columna nativo de PG.
     */
    @Type(JsonType.class)
    @Column(name = "dato_old", columnDefinition = "jsonb")
    private Object datoOld;

    @Type(JsonType.class)
    @Column(name = "dato_new", columnDefinition = "jsonb")
    private Object datoNew;

    @Column(name = "id_registro_afectado", length = 255)
    private String idRegistroAfectado;

    // --- RELACIONES (Unidas por Claves Únicas) ---

    /**
     * Relación con User.
     * La FK en 'log' es 'nombre_usuario' (que NO es la PK de 'user').
     * Por eso, usamos 'referencedColumnName' para indicarle
     * que debe unirse contra la columna 'nombre_usuario' de la tabla 'user'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_usuario", referencedColumnName = "nombre_usuario", nullable = false)
    private User user;

    /**
     * Relación con Action.
     * Mismo caso: se une contra la columna 'nombre_accion' (que es UNICA),
     * no contra 'id_action'.
     * Esta relación ES NULLEABLE.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_accion", referencedColumnName = "nombre_accion")
    private Action action;

    // --- Constructor, Getters y Setters ---

    public Log() {
    }

    // --- Getters y Setters ---

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Object getDatoOld() {
        return datoOld;
    }

    public void setDatoOld(Object datoOld) {
        this.datoOld = datoOld;
    }

    public Object getDatoNew() {
        return datoNew;
    }

    public void setDatoNew(Object datoNew) {
        this.datoNew = datoNew;
    }

    public String getIdRegistroAfectado() {
        return idRegistroAfectado;
    }

    public void setIdRegistroAfectado(String idRegistroAfectado) {
        this.idRegistroAfectado = idRegistroAfectado;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}