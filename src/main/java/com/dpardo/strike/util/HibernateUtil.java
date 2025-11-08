package com.dpardo.strike.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de utilidad para gestionar la factoría de EntityManager de JPA (Hibernate).
 * Reemplaza a la antigua clase DatabaseConnection.java.
 *
 * Esta clase sigue el patrón Singleton para asegurar que solo exista
 * una instancia del costoso EntityManagerFactory en toda la aplicación.
 */
public class HibernateUtil {

    // El EntityManagerFactory es el objeto principal de JPA.
    // Es "thread-safe" y costoso de crear, por eso creamos solo uno.
    private static final EntityManagerFactory emf;

    // Bloque estático de inicialización.
    // Esto se ejecuta una sola vez cuando la clase es cargada por primera vez.
    static {
        try {
            // Lee el archivo /META-INF/persistence.xml
            // y busca la unidad de persistencia llamada "strike-pu".
            emf = Persistence.createEntityManagerFactory("strike-pu");
        } catch (Throwable ex) {
            // Si algo sale mal (ej: no se puede conectar a la BD,
            // el XML está mal formado), registra el error y falla.
            System.err.println("¡Error! Fallo al crear el EntityManagerFactory de Hibernate. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Obtiene un nuevo EntityManager (la "conexión" o "sesión")
     * desde la factoría.
     *
     * ¡Importante! El EntityManager NO es thread-safe.
     * Cada hilo (o cada operación) debe pedir uno nuevo.
     *
     * @return Un nuevo EntityManager.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Cierra la factoría de EntityManager.
     * Esto debe ser llamado cuando la aplicación se cierra (ej: en el
     * método stop() de tu MainApplication) para liberar todos los recursos.
     */
    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}