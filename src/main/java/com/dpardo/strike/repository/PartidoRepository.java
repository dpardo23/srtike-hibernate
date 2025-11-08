package com.dpardo.strike.repository;

import com.dpardo.strike.entity.Equipo;
import com.dpardo.strike.entity.Liga;
import com.dpardo.strike.entity.Partido;
import com.dpardo.strike.entity.PartidoId; // ¡Importamos la Clave Compuesta!
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Repositorio para gestionar las operaciones de la entidad Partido.
 * REFACTORIZADO para usar JPA/Hibernate.
 *
 * Este repositorio maneja la inserción en una entidad
 * con una Clave Primaria Compuesta (@EmbeddedId).
 */
public class PartidoRepository {

    /**
     * Inserta un nuevo partido en la base de datos.
     * La firma del método se mantiene igual para no romper el controlador.
     *
     * @param equipoLocalId El ID del equipo local.
     * @param equipoVisitanteId El ID del equipo visitante.
     * @param fecha La fecha en que se juega el partido.
     * @param hora La hora de inicio del partido.
     * @param ligaId El ID de la liga a la que pertenece el partido.
     * @param historial Un número único que identifica el historial del partido.
     * @throws Exception Si ocurre un error (ej. FK no encontrada).
     */
    public void insertarPartido(int equipoLocalId, int equipoVisitanteId, LocalDate fecha, LocalTime hora, int ligaId, int historial) throws Exception {

        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Buscar las entidades foráneas (Equipos y Liga)
            Equipo equipoLocal = em.find(Equipo.class, equipoLocalId);
            Equipo equipoVisitante = em.find(Equipo.class, equipoVisitanteId);
            Liga liga = em.find(Liga.class, ligaId);

            // 2. Validar que las entidades existan
            if (equipoLocal == null) {
                throw new Exception("Error de clave foránea: El Equipo local con ID '" + equipoLocalId + "' no existe.");
            }
            if (equipoVisitante == null) {
                throw new Exception("Error de clave foránea: El Equipo visitante con ID '" + equipoVisitanteId + "' no existe.");
            }
            if (liga == null) {
                throw new Exception("Error de clave foránea: La Liga con ID '" + ligaId + "' no existe.");
            }

            // 3. Crear la Clave Primaria Compuesta
            //    (Usamos el constructor que definimos en PartidoId.java)
            PartidoId nuevoPartidoId = new PartidoId(equipoLocalId, equipoVisitanteId, fecha);

            // 4. Crear la nueva entidad Partido
            Partido nuevoPartido = new Partido();

            // 5. Asignar la clave compuesta y los campos simples
            nuevoPartido.setId(nuevoPartidoId);
            nuevoPartido.setHora(hora);
            nuevoPartido.setHistorial(historial);

            // 6. Asignar las RELACIONES (los objetos)
            //    Esto es crucial para que @MapsId funcione.
            nuevoPartido.setEquipoLocal(equipoLocal);
            nuevoPartido.setEquipoVisitante(equipoVisitante);
            nuevoPartido.setLiga(liga);

            // 7. Persistir la nueva entidad
            em.persist(nuevoPartido);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            // Relanzamos para que el controlador muestre la alerta
            throw new Exception("Error al insertar el partido: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}