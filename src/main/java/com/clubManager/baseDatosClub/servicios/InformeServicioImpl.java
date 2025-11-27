package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.InformeDTO;
import com.clubManager.baseDatosClub.dto.InformeResumenDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Informe;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.repositorios.EntrenadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.InformeRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;

/**
 * Implementación del servicio {@link InformeServicio}.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class InformeServicioImpl implements InformeServicio {
	
	//Area de Datos

    @Autowired
    private InformeRepositorio informeRepositorio;
    
    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;
    
    @Autowired
    private JugadorRepositorio jugadorRepositorio;
    
    //Métodos Principales

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Informe> buscarPorIdYEquipo(Long idInforme, String idEquipo)
    {
        return informeRepositorio.findByIdInformeAndEquipo_IdEquipo(idInforme, idEquipo);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public Informe crearInforme(InformeDTO dto) 
    {
        if (dto.getFecha() == null || dto.getContenido() == null || dto.getTipo() == null) 
        {
            throw new IllegalArgumentException("Fecha, contenido y tipo son obligatorios.");
        }

        Informe informe = new Informe();
        informe.setFecha(dto.getFecha());
        informe.setContenido(dto.getContenido());
        informe.setTipo(dto.getTipo());

        Entrenador entrenador = entrenadorRepositorio.findById(dto.getIdEntrenador())     
            .orElseThrow(() -> new IllegalArgumentException("Entrenador con ID " + dto.getIdEntrenador() + " no existe."));
        informe.setEntrenador(entrenador);

        if (dto.getIdJugador() != null && !dto.getIdJugador().isEmpty()) 
        {
            Jugador jugador = jugadorRepositorio.findById(dto.getIdJugador())
                .orElseThrow(() -> new IllegalArgumentException("Jugador con ID " + dto.getIdJugador() + " no existe."));
            informe.setJugador(jugador);
        }

        Equipo equipo = entrenador.getEquipo();
        if (equipo == null || !equipo.getIdEquipo().equals(dto.getIdEquipo())) 
        {
            throw new SecurityException("El entrenador no pertenece al equipo indicado.");
        }
        informe.setEquipo(equipo);

        return informeRepositorio.save(informe);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public Informe crearInforme(Informe informe) 
    {
        return informeRepositorio.save(informe);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public void modificarInforme(Long idInforme, LocalDate fecha, String contenido, String tipo,
                                  Entrenador entrenador, Jugador jugador) 
    {
        Informe informe = informeRepositorio.findById(idInforme)
                .orElseThrow(() -> new IllegalArgumentException("Informe no encontrado con ID " + idInforme));

        Equipo equipoInforme = informe.getEquipo();
        Equipo equipoEntrenador = entrenador.getEquipo();

        if (equipoInforme == null || equipoEntrenador == null ||
            !equipoInforme.getIdEquipo().equals(equipoEntrenador.getIdEquipo())) 
        {
            throw new IllegalArgumentException("El informe no pertenece al equipo del entrenador.");
        }

        informe.setFecha(fecha);
        informe.setContenido(contenido);
        informe.setTipo(tipo);
        informe.setEntrenador(entrenador);
        informe.setJugador(jugador);

        informeRepositorio.save(informe);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public void eliminarInforme(Long idInforme, String idEquipo) 
    {
        Informe informe = informeRepositorio.findByIdInformeAndEquipo_IdEquipo(idInforme, idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("Informe no encontrado en el equipo " + idEquipo));
        informeRepositorio.delete(informe);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public void eliminarInformesPorEquipo(String idEquipo) 
    {
        informeRepositorio.eliminarTodosPorIdEquipo(idEquipo);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public List<InformeResumenDTO> listarInformesPorEquipo(String idEquipo) 
    {
        List<Informe> informes = informeRepositorio.findByEquipo_IdEquipo(idEquipo);

        return informes.stream()
                .map(informe -> new InformeResumenDTO
                		(
                        informe.getIdInforme(),
                        informe.getFecha() != null ? informe.getFecha().toString() : "Sin fecha",
                        informe.getTipo() != null ? informe.getTipo() : "Sin tipo",
                        (informe.getEntrenador() != null && informe.getEntrenador().getNombre() != null)
                                ? informe.getEntrenador().getNombre()
                                : "Desconocido"
                ))
                .collect(Collectors.toList());
    }
}