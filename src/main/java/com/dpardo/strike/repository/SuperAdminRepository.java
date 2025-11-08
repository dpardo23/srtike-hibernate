package com.dpardo.strike.repository;

// --- Imports de Dominio (DTOs) ---
import com.dpardo.strike.domain.SessionViewModel;
import com.dpardo.strike.domain.UiComboItem;

// --- ¡NUEVOS IMPORTS DE HIBERNATE/JPA! ---
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

// --- Imports de Java ---
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio para gestionar las operaciones específicas de la vista de Super Administrador.
 * REFACTORIZADO para usar JPA/Hibernate.
 */
public class SuperAdminRepository {

    /**
     * Obtiene una lista detallada de todas las sesiones activas en la base de datos.
     * @return Una lista de objetos SessionViewModel.
     */
    public List<SessionViewModel> obtenerSesionesActivas() {

        try (EntityManager em = HibernateUtil.getEntityManager()) {

            String sql = "SELECT * FROM obtener_sesiones_activas_detalladas()";
            Query query = em.createNativeQuery(sql);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            // Mapeamos la lista de Object[] a una lista de SessionViewModel
            return results.stream()
                    .map(row -> new SessionViewModel(
                            (Integer) row[0],   // pid
                            (String) row[1],    // nombre_usuario
                            (String) row[2],    // correo
                            (Timestamp) row[3], // fec_creacion_usuario
                            (String) row[4],    // nombre_rol
                            (String) row[5],    // cod_componente_ui
                            (String) row[6],    // direccion_ip
                            (Integer) row[7],   // puerto
                            (Timestamp) row[8], // fecha_asignacion_rol
                            (Boolean) row[9]    // rol_activo
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolvemos lista vacía en caso de error
        }
    }

    /**
     * Obtiene una lista de componentes de UI a los que un usuario específico tiene permiso.
     * @param userId El ID del usuario para el cual se consultan los permisos.
     * @return Una lista de objetos UiComboItem.
     */
    public List<UiComboItem> obtenerUis(int userId) {

        try (EntityManager em = HibernateUtil.getEntityManager()) {

            String sql = "SELECT * FROM obtener_uis_por_usuario(?)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, userId);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            // Mapeamos la lista de Object[] a una lista de UiComboItem
            return results.stream()
                    .map(row -> new UiComboItem(
                            (Integer) row[0], // id
                            (String) row[1],  // codigo_componente
                            (String) row[2]   // descripcion_ui
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Devolvemos lista vacía en caso de error
        }
    }
}