package com.dpardo.strike.repository;

import com.dpardo.strike.domain.SessionInfo;
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.sql.Timestamp; // La función de PG devuelve un Timestamp

/**
 * Repositorio para gestionar las operaciones de la entidad Usuario.
 * REFACTORIZADO para usar JPA/Hibernate.
 */
public class UserRepository {

    /**
     * Autentica a un usuario y registra una nueva sesión en la base de datos.
     * Llama a la función de PostgreSQL 'autenticar_usuario_y_registrar_sesion'
     * usando una consulta nativa de JPA.
     *
     * @param username El nombre de usuario o correo electrónico.
     * @param password La contraseña del usuario.
     * @return un objeto SessionInfo con los datos de la sesión si la autenticación es exitosa, de lo contrario null.
     */
    public SessionInfo authenticateAndRegisterSession(String username, String password) {

        // Usamos try-with-resources para el EntityManager
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            // 1. Creamos una consulta nativa para llamar a la función de PG
            String sql = "SELECT * FROM autenticar_usuario_y_registrar_sesion(?, ?)";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, username);
            query.setParameter(2, password);

            // 2. Ejecutamos la consulta. Esperamos una sola fila.
            Object[] result;
            try {
                // query.getSingleResult() lanza una excepción si no hay resultados
                result = (Object[]) query.getSingleResult();
            } catch (NoResultException e) {
                // Usuario o contraseña incorrectos, la función no devolvió nada.
                System.err.println("Autenticación fallida para: " + username);
                return null;
            }

            // 3. Mapeamos el resultado (Object[]) a nuestro Record 'SessionInfo'
            // NOTA: El orden de los índices [0], [1], etc., debe coincidir
            // exactamente con las columnas que devuelve tu función.
            // id_usuario_out, pid_out, client_address_out, client_port_out, rol_nombre_out
            SessionInfo sessionInfo = new SessionInfo(
                    (Integer) result[0],  // id_usuario_out
                    (Integer) result[1],  // pid_out
                    (String) result[2],   // client_address_out
                    (Integer) result[3],  // client_port_out
                    (String) result[4]    // rol_nombre_out
            );

            // ¡IMPORTANTE!
            // Ya no necesitamos el "SET app.current_user_id = ..." aquí.
            // Ese código ahora vive en 'DatabaseConnection.java' (que no usamos)
            // o en 'HibernateUtil.java'.
            //
            // Lo que SÍ necesitamos es actualizar el SessionManager para que
            // las *futuras* operaciones de este usuario queden registradas.
            // (Esta línea la tenías en LoginController, pero es más seguro
            // que el repositorio que CREA la sesión la ESTABLEZCA).
            // (Si ya lo haces en LoginController, esta línea es opcional
            // pero recomendada).

            return sessionInfo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}