package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Lesion;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.LesionRepositorio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación de la interfaz {@link LesionServicio} que gestiona
 * las operaciones CRUD relacionadas con las lesiones de los jugadores.
 * 
 * Utiliza {@link LesionRepositorio} y {@link JugadorRepositorio} para el acceso a datos.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class LesionServicioImpl implements LesionServicio {
	
	//Area de datos

    @Autowired
    private LesionRepositorio lesionRepositorio;

    @Autowired
    private JugadorRepositorio jugadorRepositorio;
    
    //Métodos principales

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Optional<Lesion> buscarPorId(Long idLesion) 
    {
        return lesionRepositorio.findById(idLesion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void crearLesion(String tipo, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, 
    		String idJugador) 
    {
        Optional<Jugador> jugadorOptional = jugadorRepositorio.findById(idJugador);

        if (jugadorOptional.isPresent()) 
        {
            Lesion lesion = new Lesion();
            lesion.setTipo(tipo);
            lesion.setFechaInicio(fechaInicio);
            lesion.setFechaFin(fechaFin);
            lesion.setDescripcion(descripcion);
            lesion.setJugador(jugadorOptional.get());

            lesionRepositorio.save(lesion); 
        } 
        else 
        {
            throw new RuntimeException("Jugador no encontrado con ID: " + idJugador);
        }
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void modificarLesion(Long idLesion, String tipo, LocalDate fechaInicio, LocalDate fechaFin,
    		String descripcion) 
    {
        Optional<Lesion> optionalLesion = lesionRepositorio.findById(idLesion);

        if (optionalLesion.isEmpty()) 
        {
            throw new IllegalArgumentException("No se encontró la lesión con el ID: " + idLesion);
        }

        Lesion lesion = optionalLesion.get();
        lesion.setTipo(tipo);
        lesion.setFechaInicio(fechaInicio);
        lesion.setFechaFin(fechaFin);
        lesion.setDescripcion(descripcion);

        lesionRepositorio.save(lesion);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void eliminarLesion(Long idLesion) 
    {
        lesionRepositorio.deleteById(idLesion);
    }

    /**
     * Elimina todas las lesiones de la base de datos.
     */
    
    @Override
    public void eliminarLesiones() 
    {
        lesionRepositorio.deleteAll();
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public List<Jugador> listarLesionadosPorEquipo(String idEquipo) 
    {
        List<Lesion> lesiones = lesionRepositorio.findByJugador_Equipo_IdEquipo(idEquipo);
        return lesiones.stream()
                       .map(Lesion::getJugador) 
                       .toList();
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void registrarLesionYDesactivarJugador(String idJugador) {
        Jugador jugador = jugadorRepositorio.findById(idJugador)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con ID: " + idJugador));

        Lesion lesion = new Lesion();
        lesion.setJugador(jugador);
        lesionRepositorio.save(lesion);
        jugador.setActivo(false);
        jugadorRepositorio.save(jugador);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
	public void eliminarLesionYReactivarJugador(String idJugador) 
    {
	    Jugador jugador = jugadorRepositorio.findById(idJugador)
	            .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con ID: " + idJugador));

	    Optional<Lesion> lesionActiva = lesionRepositorio.findFirstByJugador_IdJugador(idJugador);

	    lesionActiva.ifPresent(lesion -> eliminarLesion(lesion.getIdLesion())); 

	    jugador.setActivo(true);
	    jugadorRepositorio.save(jugador);
	}

	@Override
	public void crearLesion(Lesion lesion) 
	{
		lesionRepositorio.save(lesion);	
	}
}