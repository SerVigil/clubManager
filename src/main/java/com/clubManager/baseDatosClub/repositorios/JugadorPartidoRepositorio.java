package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.JugadorPartido;
import com.clubManager.baseDatosClub.entidades.JugadorPartidoPK;


/**
 * Repositorio JPA para la entidad {@link JugadorPartido}.
 * 
 * Esta interfaz permite realizar operaciones CRUD sobre la tabla relacionada con la entidad
 * {@code JugadorPartido}, cuya clave primaria está compuesta por {@link JugadorPartidoPK}.
 * 
 * Además de los métodos heredados de {@link JpaRepository}, se definen consultas
 * personalizadas.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface JugadorPartidoRepositorio extends JpaRepository<JugadorPartido, JugadorPartidoPK> {

    /**
     * Busca todos los registros de jugador-partido correspondientes a un jugador específico.
     * 
     * @param idJugador Identificador del jugador.
     * @return Lista de {@code JugadorPartido} asociados a ese jugador.
     */
	
    List<JugadorPartido> findById_IdJugador(String idJugador);

    /**
     * Busca todos los registros de jugador-partido correspondientes a un partido específico.
     * 
     * @param idPartido Identificador del partido.
     * @return Lista de {@code JugadorPartido} asociados a ese partido.
     */
    
    List<JugadorPartido> findById_IdPartido(Long idPartido);
    
    /**
     * Busca un registro de JugadorPartido por el id del jugador y el id del partido.
     *
     * @param idJugador identificador único del jugador
     * @param idPartido identificador único del partido
     * @return Optional con el JugadorPartido si existe, vacío si no se encuentra
     */
    
    Optional<JugadorPartido> findById_IdJugadorAndId_IdPartido(String idJugador, Long idPartido);

    /**
     * Elimina un registro de JugadorPartido usando su clave primaria compuesta.
     *
     * @param id clave primaria compuesta (JugadorPartidoPK)
     */
    
    void deleteById(JugadorPartidoPK id);

    /**
     * Elimina un registro de JugadorPartido por el id del jugador y el id del partido.
     *
     * @param idJugador identificador único del jugador
     * @param idPartido identificador único del partido
     */
    
    void deleteById_IdJugadorAndId_IdPartido(String idJugador, Long idPartido);

    /**
     * Comprueba si existe un registro de JugadorPartido con la clave primaria indicada.
     *
     * @param id clave primaria compuesta (JugadorPartidoPK)
     * @return true si existe, false en caso contrario
     */
    
    boolean existsById(JugadorPartidoPK id);

    /**
     * Calcula la suma total de las puntuaciones obtenidas por un jugador en todos sus partidos.
     *
     * @param idJugador identificador único del jugador
     * @return suma de las puntuaciones del jugador (0 si no tiene registros)
     */
    
    @Query("SELECT COALESCE(SUM(jp.puntuacion), 0) FROM JugadorPartido jp WHERE jp.jugador.idJugador = :idJugador")
    int sumPuntuacionByJugador(@Param("idJugador") String idJugador);
}