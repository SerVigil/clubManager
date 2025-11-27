package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Notificacion;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con las notificaciones
 * emitidas y recibidas por los usuarios.
 * 
 * @author Sergio Vigil Soto
 */

public interface NotificacionServicio {

    /**
     * Lista todas las notificaciones asociadas a un equipo específico.
     * 
     * @param idEquipo el identificador del equipo
     * @return una lista de notificaciones vinculadas al equipo
     */
	
    List<Notificacion> listarNotificacionesPorEquipo(String idEquipo);

    /**
     * Busca una notificación por su ID, validando que pertenezca al equipo indicado.
     * 
     * @param idNotificacion el ID de la notificación
     * @param idEquipo el ID del equipo al que debe pertenecer
     * @return un {@link Optional} con la notificación si cumple la condición
     */
    
    Optional<Notificacion> buscarPorIdYEquipo(Long idNotificacion, String idEquipo);
    
    /**
     * Busca la última notificación emitida para un equipo.
     * 
     * @param idEquipo el ID del equipo al que debe pertenecer
     * @return un {@link Optional} con la notificación si existe.
     */
    
    Optional<Notificacion> obtenerUltimaPorEquipo(String idEquipo);

    /**
     * Crea y guarda una notificación asociada a un equipo específico.
     * 
     * @param titulo el título de la notificación
     * @param mensaje el contenido del mensaje
     * @param equipo el equipo al que se asocia la notificación
     */
    
    void crearNotificacion(String titulo, String mensaje, Equipo equipo);

    /**
     * Elimina una notificación por su ID, validando que pertenezca al equipo indicado.
     * 
     * @param idNotificacion el ID de la notificación a eliminar
     * @param idEquipo el ID del equipo al que debe pertenecer
     */
    
    void eliminarNotificacionPorEquipo(Long idNotificacion, String idEquipo);

    /**
     * Elimina todas las notificaciones asociadas a un equipo específico.
     * 
     * @param idEquipo el ID del equipo cuyas notificaciones se eliminarán
     */
    
    void eliminarNotificacionesPorEquipo(String idEquipo);
}