package com.dpardo.strike.repository;

import com.dpardo.strike.domain.PaisComboItem;
// Importamos la Entidad explícitamente
import com.dpardo.strike.entity.Pais;
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // <--- Importante para la conversión

/**
 * Repositorio para la entidad Pais.
 * Migrado 100% a Hibernate.
 */
public class PaisRepository {

    /**
     * Obtiene una lista completa de todos los países.
     * CORREGIDO: Convierte de 'Entity' a 'Domain' para evitar el error de tipos.
     * @return Una lista de objetos com.dpardo.strike.domain.Pais
     */
    public List<com.dpardo.strike.domain.Pais> obtenerTodosLosPaises() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            // 1. Obtenemos las ENTIDADES de la base de datos
            List<Pais> entidades = em.createQuery("SELECT p FROM Pais p ORDER BY p.nombre", Pais.class).getResultList();

            // 2. Las convertimos a OBJETOS DE DOMINIO (Records) que es lo que pide el Controller
            return entidades.stream()
                    .map(p -> new com.dpardo.strike.domain.Pais(p.getNombre(), p.getCodFifa()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PaisComboItem> obtenerPaisesParaCombo() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            // Proyección directa al record DTO usando constructor expression
            String jpql = "SELECT new com.dpardo.strike.domain.PaisComboItem(p.codFifa, p.nombre) " +
                    "FROM Pais p ORDER BY p.nombre";
            return em.createQuery(jpql, PaisComboItem.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void insertarPais(String codFifa, String nombre, String continente) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            HibernateUtil.setAuditUser(em); // Auditoría

            Pais pais = new Pais();
            pais.setCodFifa(codFifa);
            pais.setNombre(nombre);
            pais.setContinente(continente);

            em.persist(pais);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new Exception("Error al insertar país: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<String> obtenerTodosLosCodigosFifa() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT p.codFifa FROM Pais p ORDER BY p.codFifa", String.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void actualizarNombrePais(String codFifa, String nuevoNombre) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            HibernateUtil.setAuditUser(em); // Auditoría

            Pais pais = em.find(Pais.class, codFifa);
            if (pais == null) {
                throw new Exception("No se encontró el país con código: " + codFifa);
            }
            pais.setNombre(nuevoNombre);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new Exception("Error al actualizar país: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void eliminarPais(String codFifa) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            HibernateUtil.setAuditUser(em); // Auditoría

            Pais pais = em.find(Pais.class, codFifa);
            if (pais != null) {
                em.remove(pais);
            } else {
                throw new Exception("No existe el país para eliminar: " + codFifa);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new Exception("Error al eliminar país (posible restricción de FK): " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}