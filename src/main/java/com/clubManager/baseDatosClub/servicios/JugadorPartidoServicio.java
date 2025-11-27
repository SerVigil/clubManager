package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.entidades.JugadorPartido;
import com.clubManager.baseDatosClub.entidades.JugadorPartidoPK;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con la entidad {@link JugadorPartido}.
 * 
 * La clave primaria de esta entidad está compuesta y representada por {@link JugadorPartidoPK}.
 * 
 * @author Sergio Vigil Soto
 */

public interface JugadorPartidoServicio {

    /**
     * Guarda o actualiza un registro de jugador-partido.
     * 
     * @param jugadorPartido La entidad a guardar.
     * @return La entidad guardada.
     */
	
    JugadorPartido save(JugadorPartido jugadorPartido);

    /**
     * Busca un registro de jugador-partido por su clave primaria compuesta.
     * 
     * @param id Clave compuesta de tipo {@code JugadorPartidoPK}.
     * @return Un {@code Optional} con el resultado, si existe.
     */
    
    Optional<JugadorPartido> findById(JugadorPartidoPK id);

    /**
     * Elimina un registro de jugador-partido por su clave primaria.
     * 
     * @param id Clave compuesta de tipo {@code JugadorPartidoPK}.
     */
    
    void deleteById(JugadorPartidoPK id);

    /**
     * Obtiene todos los registros de jugador-partido existentes.
     * 
     * @return Lista de {@code JugadorPartido}.
     */
    
    List<JugadorPartido> findAll();

    /**
     * Obtiene todos los registros de jugador-partido asociados a un jugador específico.
     * 
     * @param idJugador Identificador del jugador.
     * @return Lista de {@code JugadorPartido} del jugador.
     */
    
    List<JugadorPartido> findByIdJugador(String idJugador);

    /**
     * Obtiene todos los registros de jugador-partido asociados a un partido específico.
     * 
     * @param idPartido Identificador del partido.
     * @return Lista de {@code JugadorPartido} del partido.
     */
    
    List<JugadorPartido> findByIdPartido(Long idPartido);
    
    /**
     * Asocia varios jugadores a un partido.
     *
     * @param idPartido ID del partido.
     * @param idsJugadores Lista de IDs de jugadores a asociar.
     */
    
    void asignarJugadoresAPartido(Long idPartido, List<String> idsJugadores);
    
    /**
     * Asigna una puntuación a un jugador en un partido específico.
     *
     * @param idJugador   Identificador del jugador.
     * @param idPartido   Identificador del partido.
     * @param puntuacion  Puntos a asignar al jugador.
     * @return Un {@link JugadorPuntuacionDTO} con la información actualizada del jugador.
     */
    
    public JugadorPuntuacionDTO asignarPuntuacion(String idJugador, Long idPartido, Integer puntuacion);
}