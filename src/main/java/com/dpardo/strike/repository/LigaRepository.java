package com.dpardo.strike.repository;

import com.dpardo.strike.domain.LigaComboItem;
import com.dpardo.strike.entity.Liga;
import com.dpardo.strike.entity.Pais; // ¡Importamos la entidad Pais!
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Liga.
 * REFACTORIZADO para usar JPA/Hibernate.
 */
public class LigaRepository {

    /**
     * Obtiene una lista de ligas (ID y nombre) para ser usada en ComboBoxes.
     * @return Una lista de objetos LigaComboItem.
     */
    public List<LigaComboItem> obtenerLigasParaCombo() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // Usamos un "Constructor Expression" de JPQL
            String jpql = "SELECT new com.dpardo.strike.domain.LigaComboItem(l.idLiga, l.nombre) " +
                    "FROM Liga l ORDER BY l.nombre"; // "Liga" es la Entidad

            TypedQuery<LigaComboItem> query = em.createQuery(jpql, LigaComboItem.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Inserta una nueva liga en la base de datos.
     * La firma del método no cambia, por lo que la UI (FormLigaController)
     * no necesita ser modificada.
     *
     * @param id El identificador de la liga.
     * @param nombre El nombre de la liga.
     * @param paisCodFifa El CÓDIGO FIFA del país asociado.
     * @param tipo El tipo de liga (ej: "Profesional").
     * @throws Exception
     */
    public void insertarLiga(int id, String nombre, String paisCodFifa, String tipo) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Buscamos la entidad Pais usando el paisCodFifa
            Pais paisEntidad = em.find(Pais.class, paisCodFifa);

            // 2. Verificamos si el país existe
            if (paisEntidad == null) {
                throw new Exception("Error de clave foránea: El país con código '" + paisCodFifa + "' no existe.");
            }

            // 3. Creamos la nueva entidad Liga
            Liga nuevaLiga = new Liga();
            nuevaLiga.setIdLiga(id);
            nuevaLiga.setNombre(nombre);
            nuevaLiga.setTipo(tipo);

            // 4. Asignamos el OBJETO Pais completo
            nuevaLiga.setPais(paisEntidad);

            // 5. Persistimos la nueva liga
            em.persist(nuevaLiga);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al insertar la liga: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}