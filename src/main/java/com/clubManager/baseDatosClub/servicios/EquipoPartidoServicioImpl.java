package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.EquipoPartidoDTO;
import com.clubManager.baseDatosClub.dto.EquipoPartidoResultadoDTO;
import com.clubManager.baseDatosClub.entidades.EquipoPartido;
import com.clubManager.baseDatosClub.entidades.EquipoPartidoPK;
import com.clubManager.baseDatosClub.entidades.Partido;
import com.clubManager.baseDatosClub.repositorios.EquipoPartidoRepositorio;
import com.clubManager.baseDatosClub.repositorios.PartidoRepositorio;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


/**
 * Implementación de la interfaz {@link EquipoPartidoServicio}.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class EquipoPartidoServicioImpl implements EquipoPartidoServicio {
	
	//Area de Datos

	@Autowired
    private final EquipoPartidoRepositorio equipoPartidoRepositorio;
    
	@Autowired
    private PartidoRepositorio partidoRepositorio;
    
    //Métodos principales
    
    /**
	 * {@inheritDoc}
	 */

    @Autowired
    public EquipoPartidoServicioImpl(EquipoPartidoRepositorio equipoPartidoRepositorio) 
    {
        this.equipoPartidoRepositorio = equipoPartidoRepositorio;
    }

    /**
	 * {@inheritDoc}
	 */
    
    @Override
    public EquipoPartido save(EquipoPartido equipoPartido) 
    {
        return equipoPartidoRepositorio.save(equipoPartido);
    }

    /**
	 * {@inheritDoc}
	 */
    
    @Override
    public Optional<EquipoPartido> findById(EquipoPartidoPK id) 
    {
        return equipoPartidoRepositorio.findById(id);
    }

    /**
	 * {@inheritDoc}
	 */
    
    @Override
    public void deleteById(EquipoPartidoPK id) 
    {
        equipoPartidoRepositorio.deleteById(id);
    }

    /**
	 * {@inheritDoc}
	 */
    
    @Override
    public List<EquipoPartidoDTO> listarTodos() {
        return equipoPartidoRepositorio.findAll().stream()
            .map(ep -> new EquipoPartidoDTO(
                ep.getId().getIdEquipo(),
                ep.getId().getIdPartido(),
                ep.getPartido()
            ))
            .collect(Collectors.toList());
    }

    /**
	 * {@inheritDoc}
	 */
    
    @Override
    public List<EquipoPartido> findByIdEquipo(String idEquipo) 
    {
        return equipoPartidoRepositorio.findByEquipo_IdEquipo(idEquipo);
    }
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public EquipoPartidoResultadoDTO asignarResultado(EquipoPartidoPK id, String resultado) 
	{
	    Partido partido = partidoRepositorio.findByIdPartidoAndEquipo_IdEquipo(id.getIdPartido(), id.getIdEquipo())
	            .orElseThrow(() -> new EntityNotFoundException("No se encontró el partido con ID: " + id.getIdPartido()));

	    partido.setResultado(resultado);
	    partidoRepositorio.save(partido);

	    String nombreLocal = partido.getLocal();
	    String nombreVisitante = partido.getVisitante();

	    return new EquipoPartidoResultadoDTO(partido.getIdPartido(), nombreLocal, nombreVisitante, partido.getResultado());
	}

	@Override
	@Transactional
	public void deleteByPartido_Equipo_IdEquipo(String idEquipo) {

	    if (idEquipo == null || idEquipo.isEmpty()) 
	    {
	        throw new IllegalArgumentException("El ID del equipo no puede ser nulo o vacío");
	    }

	    equipoPartidoRepositorio.deleteByPartido_Equipo_IdEquipo(idEquipo);
	}

}