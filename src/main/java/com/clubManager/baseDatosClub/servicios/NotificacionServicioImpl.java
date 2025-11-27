package com.clubManager.baseDatosClub.servicios;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Notificacion;
import com.clubManager.baseDatosClub.repositorios.NotificacionRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de notificaciones.
 * Gestiona el registro, consulta y eliminación de notificaciones asociadas a equipos.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class NotificacionServicioImpl implements NotificacionServicio {

	//Area de datos
	
    @Autowired
    private NotificacionRepositorio notificacionRepo;
    
    //Métodos principales

    /**
     * {@inheritDoc}
     */

    @Override
    public List<Notificacion> listarNotificacionesPorEquipo(String idEquipo) 
    {
        return notificacionRepo.findByEquipo_IdEquipo(idEquipo);
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public Optional<Notificacion> buscarPorIdYEquipo(Long idNotificacion, String idEquipo) 
    {
        return notificacionRepo.findById(idNotificacion)
                .filter(n -> n.getEquipo() != null && idEquipo.equals(n.getEquipo().getIdEquipo()));
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void crearNotificacion(String titulo, String mensaje, Equipo equipo) 
    {
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(LocalDate.now());
        notificacion.setEquipo(equipo);

        notificacionRepo.save(notificacion);
    }
    
    /**
     * {@inheritDoc}
     */
    
    public Optional<Notificacion> obtenerUltimaPorEquipo(String idEquipo) 
    {
        return notificacionRepo.findTopByEquipo_IdEquipoOrderByFechaDesc(idEquipo);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void eliminarNotificacionPorEquipo(Long idNotificacion, String idEquipo) 
    {
        Optional<Notificacion> notificacion = buscarPorIdYEquipo(idNotificacion, idEquipo);
        if (notificacion.isPresent()) 
        {
            notificacionRepo.deleteById(idNotificacion);
        } 
        else
        {
        	throw new IllegalArgumentException(
        	        "La notificación con ID " + idNotificacion + 
        	        " no existe o no pertenece al equipo " + idEquipo
        	    );
        }
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public void eliminarNotificacionesPorEquipo(String idEquipo) 
    {
        List<Notificacion> notificaciones = listarNotificacionesPorEquipo(idEquipo);
        notificacionRepo.deleteAll(notificaciones);
    }
}