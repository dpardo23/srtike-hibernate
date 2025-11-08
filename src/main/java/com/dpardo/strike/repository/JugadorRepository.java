package com.dpardo.strike.repository;

import com.dpardo.strike.entity.Equipo;
import com.dpardo.strike.entity.Jugador;
import com.dpardo.strike.entity.Pais;
import com.dpardo.strike.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones de la entidad Jugador.
 * REFACTORIZADO para usar JPA/Hibernate.
 */
public class JugadorRepository {

    /**
     * Inserta un nuevo jugador en la base de datos.
     * La firma del método se mantiene igual para no romper el controlador.
     *
     * @param id El identificador del jugador.
     * @param nombre El nombre completo del jugador.
     * @param fechaNacimiento La fecha de nacimiento del jugador.
     * @param sexo El sexo del jugador ('M' o 'F').
     * @param paisCodFifa El código FIFA del país de nacionalidad.
     * @param posicion La posición de juego del jugador.
     * @param equipoId El ID del equipo al que pertenece el jugador.
     * @param estadisticas Un valor numérico para estadísticas (puede ser nulo).
     * @param altura La altura del jugador en centímetros.
     * @param peso El peso del jugador en kilogramos.
     * @param foto Un arreglo de bytes que representa la imagen del jugador (puede ser nulo).
     * @throws Exception Si ocurre un error (ej. FK no encontrada).
     */
    public void insertarJugador(int id, String nombre, LocalDate fechaNacimiento, char sexo,
                                String paisCodFifa, String posicion, int equipoId,
                                Integer estadisticas, int altura, BigDecimal peso, byte[] foto) throws Exception {

        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Buscar las entidades foráneas
            Pais paisEntidad = em.find(Pais.class, paisCodFifa);
            Equipo equipoEntidad = em.find(Equipo.class, equipoId);

            // 2. Validar las entidades foráneas
            if (paisEntidad == null) {
                throw new Exception("Error de clave foránea: El país con código '" + paisCodFifa + "' no existe.");
            }
            if (equipoEntidad == null) {
                throw new Exception("Error de clave foránea: El equipo con ID '" + equipoId + "' no existe.");
            }

            // 3. Crear y poblar la nueva entidad Jugador
            Jugador nuevoJugador = new Jugador();
            nuevoJugador.setIdJugador(id);
            nuevoJugador.setNombre(nombre);
            nuevoJugador.setfNacimiento(fechaNacimiento);
            nuevoJugador.setSexo(sexo);
            nuevoJugador.setPosicion(posicion);
            nuevoJugador.setAltura(altura);
            nuevoJugador.setPeso(peso);
            nuevoJugador.setFoto(foto); // (null si no se proporcionó)
            nuevoJugador.setEstadisticas(estadisticas); // (null si no se proporcionó)

            // 4. Asignar las RELACIONES (los objetos)
            nuevoJugador.setPais(paisEntidad);
            nuevoJugador.setEquipo(equipoEntidad);

            // 5. Persistir la entidad
            em.persist(nuevoJugador);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al insertar el jugador: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista de todos los nombres de los jugadores.
     * @return Lista de Strings con los nombres.
     */
    public List<String> obtenerTodosLosNombresDeJugadores() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {

            String jpql = "SELECT j.nombre FROM Jugador j ORDER BY j.nombre"; // "Jugador" es la Entidad

            TypedQuery<String> query = em.createQuery(jpql, String.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Elimina un jugador por su nombre.
     * @param nombre El nombre del jugador a eliminar.
     * @throws Exception Si el jugador no se encuentra o no se puede eliminar.
     */
    public void eliminarJugadorPorNombre(String nombre) throws Exception {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();

            // Usamos una consulta JPQL DELETE para eficiencia.
            // No necesitamos un SELECT previo.
            int deletedCount = em.createQuery("DELETE FROM Jugador j WHERE j.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .executeUpdate();

            if (deletedCount == 0) {
                // Si no se borró nada, lanzamos una excepción
                throw new Exception("No se encontró ningún jugador con el nombre: " + nombre);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error al eliminar el jugador (¿está en uso?): " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}