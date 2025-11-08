package com.dpardo.strike.repository;

import com.dpardo.strike.domain.EquipoComboItem;
import com.dpardo.strike.entity.Equipo;
import com.dpardo.strike.entity.Pais; // ¡Importamos la entidad Pais!
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Equipo.
 * REFACTORIZADO para usar JPA/Hibernate.
 */
public class EquipoRepository {

    /**
     * Obtiene una lista de equipos (ID y nombre) para ser usada en ComboBoxes.
     * @return Una lista de objetos EquipoComboItem.
     */
    public List<EquipoComboItem> obtenerEquiposParaCombo() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // Usamos un "Constructor Expression" de JPQL
            String jpql = "SELECT new com.dpardo.strike.domain.EquipoComboItem(e.idEquipo, e.nombre) " +
                    "FROM Equipo e ORDER BY e.nombre"; // "Equipo" es la Entidad

            TypedQuery<EquipoComboItem> query = em.createQuery(jpql, EquipoComboItem.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Inserta un nuevo equipo en la base de datos.
     * La firma del método no cambia, por lo que la UI (FormEquipoController)
     * no necesita ser modificada.
     *
     * @param id El identificador del equipo.
     * @param nombre El nombre del equipo.
     * @param paisCodFifa El CÓDIGO FIFA del país (ej: "ARG").
     * @param ciudad La ciudad del equipo.
     * @param fechaFundacion La fecha de fundación del equipo.
     * @param dt El nombre del director técnico.
     * @throws Exception
     */
    public void insertarEquipo(int id, String nombre, String paisCodFifa, String ciudad, LocalDate fechaFundacion, String dt) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // --- ¡Paso Clave! ---
            // 1. Antes de crear el equipo, buscamos la entidad Pais
            //    usando el paisCodFifa que recibimos.
            Pais paisEntidad = em.find(Pais.class, paisCodFifa);

            // 2. Verificamos si el país existe
            if (paisEntidad == null) {
                throw new Exception("Error de clave foránea: El país con código '" + paisCodFifa + "' no existe.");
            }

            // 3. Creamos la nueva entidad Equipo
            Equipo nuevoEquipo = new Equipo();
            nuevoEquipo.setIdEquipo(id);
            nuevoEquipo.setNombre(nombre);
            nuevoEquipo.setCiudad(ciudad);
            nuevoEquipo.setfFundacion(fechaFundacion);
            nuevoEquipo.setDirectorTecnico(dt);

            // 4. Asignamos el OBJETO Pais completo, no solo el ID.
            nuevoEquipo.setPais(paisEntidad);

            // 5. Persistimos el nuevo equipo
            em.persist(nuevoEquipo);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Relanzamos para que el controlador muestre la alerta
            throw new Exception("Error al insertar el equipo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    // NOTA: El método 'obtenerCodigosPaises()' fue movido a 'PaisRepository',
    // donde pertenece, así que no lo incluimos aquí.
}