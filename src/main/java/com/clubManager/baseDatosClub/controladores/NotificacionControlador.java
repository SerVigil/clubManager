package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubManager.baseDatosClub.dto.NotificacionDTO;
import com.clubManager.baseDatosClub.entidades.Notificacion;
import com.clubManager.baseDatosClub.servicios.NotificacionServicio;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Notificacion.
 * Proporciona endpoints para listar, buscar, crear, modificar y eliminar notificaciones.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionControlador {

    @Autowired
    private NotificacionServicio notificacionServicio;
    
    /**
     * Lista todas las notificaciones asociadas a un equipo específico.
     *
     * @param idEquipo ID del equipo
     * @return lista de notificaciones del equipo
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public List<Notificacion> listarPorEquipo(@PathVariable String idEquipo) 
    {
        return notificacionServicio.listarNotificacionesPorEquipo(idEquipo);
    }
    
    /**
     * Busca una notificación por su ID, validando que pertenezca al equipo indicado.
     *
     * @param idNotificacion ID de la notificación
     * @param idEquipo ID del equipo
     * @return Optional con la notificación si pertenece al equipo
     */
    
    @GetMapping("/{idNotificacion}/equipo/{idEquipo}")
    public Optional<Notificacion> buscarPorIdYEquipo
    		(
            @PathVariable Long idNotificacion,
            @PathVariable String idEquipo
            )
    {
        return notificacionServicio.buscarPorIdYEquipo(idNotificacion, idEquipo);
    }
    
    /**
     * Busca la última notificación emitida para un equipo.
     *
     * @param idEquipo ID del equipo
     * @return NotificacionDTO los datos de la última notificación
     */
    
    @GetMapping("/ultima/equipo/{idEquipo}")
    public ResponseEntity<NotificacionDTO> obtenerUltimaPorEquipo(@PathVariable String idEquipo) 
    {
        Optional<Notificacion> opt = notificacionServicio.obtenerUltimaPorEquipo(idEquipo);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Notificacion n = opt.get();
        NotificacionDTO dto = new NotificacionDTO();
        dto.setIdNotificacion(n.getIdNotificacion());
        dto.setTitulo(n.getTitulo());
        dto.setMensaje(n.getMensaje());
        dto.setFecha(n.getFecha());
        dto.setIdEquipo(n.getEquipo().getIdEquipo());
        return ResponseEntity.ok(dto);
    }

    /**
     * Elimina una notificación por su ID, validando que pertenezca al equipo indicado.
     *
     * @param idNotificacion ID de la notificación
     * @param idEquipo ID del equipo
     */
    
    @DeleteMapping("/eliminar/{idNotificacion}/equipo/{idEquipo}")
    public void eliminarPorIdYEquipo
    		(
            @PathVariable Long idNotificacion,
            @PathVariable String idEquipo
            ) 
    {
        notificacionServicio.eliminarNotificacionPorEquipo(idNotificacion, idEquipo);
    }
    
    /**
     * Elimina todas las notificaciones asociadas a un equipo.
     *
     * @param idEquipo ID del equipo
     */
    
    @DeleteMapping("/eliminar-todas/equipo/{idEquipo}")
    public void eliminarTodasPorEquipo(@PathVariable String idEquipo) 
    {
        notificacionServicio.eliminarNotificacionesPorEquipo(idEquipo);
    } 
}