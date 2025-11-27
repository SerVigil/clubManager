package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Sancion;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.SancionRepositorio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación de la interfaz {@link SancionServicio} que gestiona
 * las operaciones CRUD relacionadas con las sanciones.
 * 
 * Utiliza {@link SancionRepositorio} para el acceso a datos.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class SancionServicioImpl implements SancionServicio {
	
	//Area de Datos

    @Autowired
    private SancionRepositorio sancionRepo;

    @Autowired
    private JugadorRepositorio jugadorRepo;
    
    //Métodos principales

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Sancion buscarSancionId(Long idSancion) 
    {
        return sancionRepo.findById(idSancion)
            .orElseThrow(() -> new NoSuchElementException("No se encontró sanción con ID: " + idSancion));
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Sancion crearSancion(LocalDate fecha, String tipoSancion, String descripcion, String duracion, 
    		String jugadorId) 
    {
        Jugador jugador = jugadorRepo.findById(jugadorId)
            .orElseThrow(() -> new NoSuchElementException("No se encontró jugador con ID: " + jugadorId));

        Sancion sancion = new Sancion();
        sancion.setFecha(fecha);
        sancion.setTipoSancion(tipoSancion);
        sancion.setDescripcion(descripcion);
        sancion.setDuracion(duracion);
        sancion.setJugador(jugador);
        return sancionRepo.save(sancion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Sancion crearSancion(Sancion sancion) 
    {
        return sancionRepo.save(sancion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Sancion modificarSancion(Long idSancion, LocalDate fecha, String tipoSancion, 
    		String descripcion, String duracion, String jugadorId)
    {
        Sancion sancion = sancionRepo.findById(idSancion)
            .orElseThrow(() -> new NoSuchElementException("No se encontró sanción con ID: " + idSancion));
        Jugador jugador = jugadorRepo.findById(jugadorId)
            .orElseThrow(() -> new NoSuchElementException("No se encontró jugador con ID: " + jugadorId));

        sancion.setFecha(fecha);
        sancion.setTipoSancion(tipoSancion);
        sancion.setDescripcion(descripcion);
        sancion.setDuracion(duracion);
        sancion.setJugador(jugador);
        return sancionRepo.save(sancion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Sancion modificarSancion(Sancion sancion) 
    {
        Long idSancion = sancion.getIdSancion();

        if (idSancion == null || !sancionRepo.existsById(idSancion)) 
        {
            throw new NoSuchElementException("No se encontró sanción con ID: " + idSancion);
        }
        return sancionRepo.save(sancion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void eliminarSancion(Long idSancion) 
    {
        sancionRepo.deleteById(idSancion);
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public List<Jugador> listarSancionadosPorEquipo(String idEquipo) 
    {
        List<Sancion> sanciones = sancionRepo.findByJugador_Equipo_IdEquipo(idEquipo);
        return sanciones.stream()
                       .map(Sancion::getJugador) 
                       .toList();
    }
	
	/**
     * {@inheritDoc}
     */

	@Override
	public void registrarSancionYDesactivarJugador(String idJugador)
	{
		Jugador jugador = jugadorRepo.findById(idJugador)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con ID: " + idJugador));

        // Crear nueva lesión
        Sancion sancion = new Sancion();
        sancion.setJugador(jugador);

        sancionRepo.save(sancion);

        // Marcar jugador como inactivo
        jugador.setActivo(false);
        jugadorRepo.save(jugador);
	}
	
	/**
     * {@inheritDoc}
     */

	@Override
	public void eliminarSancionYReactivarJugador(String idJugador) 
	{
	    Jugador jugador = jugadorRepo.findById(idJugador)
	            .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con ID: " + idJugador));

	    Optional<Sancion> sancionActiva = sancionRepo.findFirstByJugador_IdJugador(idJugador);

	    sancionActiva.ifPresent(sancion -> eliminarSancion(sancion.getIdSancion())); 

	    jugador.setActivo(true);
	    jugadorRepo.save(jugador);
	}
}