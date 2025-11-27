package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;

/**
 * Servicio para la gestión de modelos predefinidos de entrenamiento.
 * 
 * @author Sergio Vigil Soto
 */

public interface ModeloEntrenamientoServicio {

    /**
     * Lista todos los modelos de entrenamiento asociados a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return lista de modelos de entrenamiento pertenecientes a ese equipo
     */
	
    List<ModeloEntrenamiento> listarModelosPorEquipo(String idEquipo);

    /**
     * Busca un modelo de entrenamiento por su identificador único,
     * perteneciente a un equipo determinado.
     *
     * @param idEquipo identificador del equipo
     * @param idModelo identificador del modelo
     * @return un Optional que puede contener el modelo si existe y pertenece al equipo
     */
    
    Optional<ModeloEntrenamiento> buscarPorIdYEquipo(String idEquipo, Long idModelo);
    
    /**
     * Crea un nuevo modelo de entrenamiento con los datos proporcionados.
     * 
     * @param equipo Equipo al que pertenece este modelo de entrenamiento
     * @param nombre nombre identificativo del modelo
     * @param tipo tipo de entrenamiento (ej. físico, técnico, táctico)
     * @param duracion duración estimada en minutos
     * @param observaciones observaciones generales del modelo
     */
    
    void crearModelo(Equipo equipo, String nombre, String tipo, Integer duracion, String observaciones);

    /**
     * Modifica los datos de un modelo existente, siempre que pertenezca al equipo indicado.
     *
     * @param idEquipo identificador del equipo que creó el modelo
     * @param idModelo identificador del modelo a modificar
     * @param nombre nuevo nombre del modelo
     * @param tipo nuevo tipo de entrenamiento
     * @param duracion nueva duración 
     * @param observaciones nuevas observaciones
     */
    
    void modificarModeloPorEquipo(String idEquipo, Long idModelo, String nombre, String tipo, 
    		Integer duracion, String observaciones);

    /**
     * Elimina un modelo de entrenamiento por su identificador y el del equipo que lo creó.
     *
     * @param idEquipo identificador del equipo
     * @param idModelo identificador del modelo a eliminar
     */
    
    void eliminarModeloPorEquipo(String idEquipo, Long idModelo);

    /**
     * Elimina todos los modelos de entrenamiento asociados a un equipo.
     *
     * @param idEquipo identificador del equipo
     */
    
    void eliminarTodosLosModelosDeEquipo(String idEquipo);
}

