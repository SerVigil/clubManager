package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.EntrenamientoDTO;
import com.clubManager.baseDatosClub.entidades.Entrenamiento;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con los entrenamientos.
 * 
 * Autor: Sergio Vigil Soto
 */

public interface EntrenamientoServicio {

    /**
     * Busca un entrenamiento por su identificador y por el id del equipo asociado.
     * 
     * @param idEntrenamiento id del entrenamiento
     * @param idEquipo id del equipo al que pertenece el entrenamiento
     * @return un {@link Optional} con el entrenamiento encontrado, o vacío si no existe o no pertenece al equipo
     */
	
	Optional<EntrenamientoDTO> buscarPorIdYEquipo(Long idEntrenamiento, String idEquipo);

    /**
     * Crea un nuevo entrenamiento a partir de un objeto {@link EntrenamientoDTO}.
     * 
     * @param entrenamientoDTO objeto con los datos del entrenamiento a crear
     * @return el {@link Entrenamiento} creado
     */
    
    Entrenamiento crearEntrenamiento(EntrenamientoDTO entrenamientoDTO);

    /**
     * Modifica un entrenamiento existente a partir de un objeto {@link EntrenamientoDTO}.
     * 
     * @param entrenamientoDTO objeto con los datos actualizados del entrenamiento
     * @return el {@link Entrenamiento} actualizado
     */
    
    Entrenamiento modificarEntrenamiento(EntrenamientoDTO entrenamientoDTO);

    /**
     * Elimina un entrenamiento por su id y asegurando que pertenece al equipo.
     * 
     * @param idEntrenamiento id del entrenamiento a eliminar
     * @param idEquipo id del equipo al que debe pertenecer el entrenamiento
     */
    
    void eliminarEntrenamientoPorIdYEquipo(Long idEntrenamiento, String idEquipo);

    /**
     * Busca todos los entrenamientos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de entrenamientos vinculados a ese equipo
     */
    
    List<Entrenamiento> listarPorEquipo(String idEquipo);

    /**
     * Elimina todos los entrenamientos de un equipo específico.
     * 
     * @param idEquipo id del equipo cuyos entrenamientos se eliminarán
     */
    
    void eliminarTodosPorEquipo(String idEquipo);
}


