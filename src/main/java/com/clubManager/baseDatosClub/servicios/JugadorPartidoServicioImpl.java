package com.clubManager.baseDatosClub.servicios;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.JugadorPartido;
import com.clubManager.baseDatosClub.entidades.JugadorPartidoPK;
import com.clubManager.baseDatosClub.entidades.Partido;
import com.clubManager.baseDatosClub.repositorios.JugadorPartidoRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.PartidoRepositorio;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Implementación del servicio {@link JugadorPartidoServicio}.
 * 
 * Proporciona la lógica de negocio para gestionar la relación entre jugadores y partidos.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class JugadorPartidoServicioImpl implements JugadorPartidoServicio {
	
	//Area de Datos

    @Autowired
    private JugadorPartidoRepositorio jugadorPartidoRepositorio;
    
    @Autowired
    private PartidoRepositorio partidoRepositorio;
    
    @Autowired
    private JugadorRepositorio jugadorRepositorio;
    
    //Métodos principales

    /**
     * {@inheritDoc}
     */
    
    @Override
    public JugadorPartido save(JugadorPartido jugadorPartido) 
    {
        return jugadorPartidoRepositorio.save(jugadorPartido);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Optional<JugadorPartido> findById(JugadorPartidoPK id) 
    {
        return jugadorPartidoRepositorio.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void deleteById(JugadorPartidoPK id) 
    {
        jugadorPartidoRepositorio.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public List<JugadorPartido> findAll() 
    {
        return jugadorPartidoRepositorio.findAll();
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public List<JugadorPartido> findByIdJugador(String idJugador) 
    {
        return jugadorPartidoRepositorio.findById_IdJugador(idJugador);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public List<JugadorPartido> findByIdPartido(Long idPartido) 
    {
        return jugadorPartidoRepositorio.findById_IdPartido(idPartido);
    }
    
    /**
     * {@inheritDoc}
     */
    
    @Override
    @Transactional
    public void asignarJugadoresAPartido(Long idPartido, List<String> idsJugadores) 
    {
        Partido partido = partidoRepositorio.findById(idPartido)
                .orElseThrow(() -> 
                {
                    return new IllegalArgumentException("Partido con ID " + idPartido + " no encontrado");
                });

        for (String idJugador : idsJugadores) 
        {
            Optional<Jugador> jugadorOpt = jugadorRepositorio.findById(idJugador);
            if (jugadorOpt.isEmpty()) 
            {
                continue;
            }

            Jugador jugador = jugadorOpt.get();
            JugadorPartidoPK pk = new JugadorPartidoPK(idJugador, idPartido);

            try 
            {
                jugadorPartidoRepositorio.deleteById(pk);    
            } 
            catch (Exception e) 
            {
                e.getMessage();
            }

            JugadorPartido jugadorPartido = new JugadorPartido();
            jugadorPartido.setId(pk);
            jugadorPartido.setJugador(jugador);
            jugadorPartido.setPartido(partido);

            try 
            {
                jugadorPartidoRepositorio.save(jugadorPartido);
            } 
            catch (Exception e) 
            {
                e.getMessage();
            }
        }
    }
    
    /**
     * Asigna una puntuación a un jugador en un partido.
     */
    
    @Transactional
    @Override
    public JugadorPuntuacionDTO asignarPuntuacion(String idJugador, Long idPartido, Integer puntuacion) 
    {
        if (puntuacion == null || puntuacion <= 0) 
        {
            throw new IllegalArgumentException("La puntuación debe ser positiva.");
        }

        JugadorPartidoPK pk = new JugadorPartidoPK(idJugador, idPartido);
        JugadorPartido jp = jugadorPartidoRepositorio.findById(pk)
                .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado en este partido"));

        int puntuacionActual = jp.getPuntuacion() != null ? jp.getPuntuacion() : 0;
        jp.setPuntuacion(puntuacionActual + puntuacion);
        jugadorPartidoRepositorio.save(jp);

        Jugador jugador = jp.getJugador();
        int puntosTotales = jugador.getPuntosTotales() != null ? jugador.getPuntosTotales() : 0;
        jugador.setPuntosTotales(puntosTotales + puntuacion);
        jugadorRepositorio.save(jugador);

        return new JugadorPuntuacionDTO
        		(
                jugador.getIdJugador(),
                jugador.getNombre(),
                jugador.getPuntosTotales(),
                jp.getPuntuacion()
        		);
    }
}