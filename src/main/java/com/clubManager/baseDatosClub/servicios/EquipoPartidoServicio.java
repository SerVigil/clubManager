package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.EquipoPartidoDTO;
import com.clubManager.baseDatosClub.dto.EquipoPartidoResultadoDTO;
import com.clubManager.baseDatosClub.entidades.EquipoPartido;
import com.clubManager.baseDatosClub.entidades.EquipoPartidoPK;

/**
 * Interfaz de servicio para gestionar operaciones relacionadas con la entidad EquipoPartido.
 * Representa la participación de un equipo en un partido específico.
 * 
 * @author Sergio Vigil Soto
 */

public interface EquipoPartidoServicio {

    /**
     * Guarda o actualiza una entidad EquipoPartido.
     * 
     * @param equipoPartido La entidad a guardar.
     * @return La entidad guardada.
     */
	
    EquipoPartido save(EquipoPartido equipoPartido);

    /**
     * Busca una entidad EquipoPartido por su clave primaria compuesta.
     * 
     * @param id La clave compuesta (idEquipo, idPartido).
     * @return Optional con la entidad encontrada, si existe.
     */
    
    Optional<EquipoPartido> findById(EquipoPartidoPK id);

    /**
     * Elimina un registro de EquipoPartido por su clave primaria compuesta.
     * 
     * @param id La clave compuesta.
     */
    
    void deleteById(EquipoPartidoPK id);

    /**
     * Devuelve una lista con todos los registros de EquipoPartido.
     * 
     * @return Lista completa de EquipoPartido.
     */
    
    List<EquipoPartidoDTO> listarTodos();

    /**
     * Devuelve todos los partidos en los que ha participado un equipo específico.
     * 
     * @param idEquipo Identificador del equipo.
     * @return Lista de EquipoPartido correspondientes.
     */
    
    List<EquipoPartido> findByIdEquipo(String idEquipo);
    
    /**
     * Asigna el resultado a una entidad EquipoPartido identificada por su clave compuesta.
     *
     * @param id Clave compuesta (idEquipo, idPartido).
     * @param resultado Resultado a asignar.
     * @return DTO con los datos actualizados.
     */
    
    EquipoPartidoResultadoDTO asignarResultado(EquipoPartidoPK id, String resultado);
    
    /**
     * Elimina todas las relaciones de un equipo con un partido
     * @param idEquipo identificador del equipo del que se quieren borrar sus relaciones con partido.
     */

	void deleteByPartido_Equipo_IdEquipo(String idEquipo);


}
