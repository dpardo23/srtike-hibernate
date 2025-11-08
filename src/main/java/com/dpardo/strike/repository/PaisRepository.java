package com.dpardo.strike.repository;

// --- Imports del Dominio (DTOs) ---
// ¡NOTA! Usaremos el nombre completo "com.dpardo.strike.domain.Pais"
// cuando lo necesitemos para evitar conflictos de nombre.
import com.dpardo.strike.domain.PaisComboItem;

// --- ¡NUEVOS IMPORTS DE HIBERNATE/JPA! ---
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

// --- Imports de Entidades ---
// Esta vez, importamos la entidad Pais directamente.
// Ahora, "Pais" dentro de esta clase se referirá a "com.dpardo.strike.entity.Pais"
import com.dpardo.strike.entity.Pais;

// --- Imports de Java (¡ADIÓS A java.sql.*!) ---
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio para gestionar las operaciones de la entidad País.
 * REFACTORIZADO para usar JPA/Hibernate en lugar de JDBC.
 *
 * (VERSIÓN CORREGIDA - Se corrigieron los errores de sintaxis)
 */
public class PaisRepository {

    /**
     * Obtiene una lista completa de todos los países.
     * @return Una lista de objetos Pais (del paquete 'domain').
     */
    public List<com.dpardo.strike.domain.Pais> obtenerTodosLosPaises() {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // CORRECCIÓN: "Pais" ahora se refiere a la entidad importada
            TypedQuery<Pais> query = em.createQuery("SELECT p FROM Pais p ORDER BY p.nombre", Pais.class);
            List<Pais> paisesEntity = query.getResultList();

            // Convertimos la lista de Entidades (entity) a una lista de DTOs (domain)
            return paisesEntity.stream()
                    // CORRECCIÓN: Usamos el nombre completo del Record (DTO) para evitar conflicto
                    .map(p -> new com.dpardo.strike.domain.Pais(p.getNombre(), p.getCodFifa()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene una lista de países (código FIFA y nombre) para ser usada en ComboBoxes.
     * Esta es la forma más eficiente usando un "Constructor Expression" de JPQL.
     * @return Una lista de objetos PaisComboItem.
     */
    public List<PaisComboItem> obtenerPaisesParaCombo() {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // Este JPQL crea el objeto 'PaisComboItem' directamente en la consulta.
            String jpql = "SELECT new com.dpardo.strike.domain.PaisComboItem(p.codFifa, p.nombre) " +
                    "FROM Pais p ORDER BY p.nombre"; // "Pais" aquí es la entidad

            TypedQuery<PaisComboItem> query = em.createQuery(jpql, PaisComboItem.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Inserta un nuevo país en la base de datos.
     *
     * @param codFifa El código FIFA del país (ej: "BOL").
     * @param nombre El nombre del país.
     * @param continente El continente al que pertenece.
     * @throws Exception (Relanzamos la excepción para que el controlador la muestre)
     */
    public void insertarPais(String codFifa, String nombre, String continente) throws Exception {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // CORRECCIÓN: "Pais" ahora es la entidad
            Pais nuevoPais = new Pais();
            nuevoPais.setCodFifa(codFifa);
            nuevoPais.setNombre(nombre);
            nuevoPais.setContinente(continente);

            em.persist(nuevoPais);
            transaction.commit();
            System.out.println("País '" + nombre + "' insertado (Hibernate).");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al insertar el país con Hibernate: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista de todos los códigos FIFA de la tabla de países.
     * @return Lista de Strings con los códigos FIFA.
     */
    public List<String> obtenerTodosLosCodigosFifa() {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // CORRECCIÓN: "Pais" es la entidad
            TypedQuery<String> query = em.createQuery("SELECT p.codFifa FROM Pais p ORDER BY p.codFifa", String.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Actualiza el nombre de un país.
     * @param codFifa El código del país a actualizar.
     * @param nuevoNombre El nuevo nombre para el país.
     * @throws Exception
     */
    public void actualizarNombrePais(String codFifa, String nuevoNombre) throws Exception {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // CORRECCIÓN: "Pais.class" se refiere a la entidad
            Pais pais = em.find(Pais.class, codFifa);

            if (pais != null) {
                pais.setNombre(nuevoNombre);
                transaction.commit();
            } else {
                throw new Exception("No se encontró el país con código: " + codFifa);
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al actualizar el país: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un país de la base de datos.
     * @param codFifa El código del país a eliminar.
     * @throws Exception
     */
    public void eliminarPais(String codFifa) throws Exception {
        // CORRECCIÓN: Llamamos a HibernateUtil.getEntityManager()
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // CORRECCIÓN: "Pais.class" se refiere a la entidad
            Pais pais = em.find(Pais.class, codFifa);

            if (pais != null) {
                em.remove(pais);
                transaction.commit();
            } else {
                throw new Exception("No se encontró el país con código: " + codFifa);
            }

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al eliminar el país (¿está en uso?): " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}