package com.clubManager.baseDatosClub.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.EquipoSeleccionDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.repositorios.EntrenadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.PadreRepositorio;

import jakarta.transaction.Transactional;

@Service
public class EquipoServicioImpl implements EquipoServicio {
	
	//Area de datos

    @Autowired
    private EquipoRepositorio equipoRepo;

    @Autowired
    private JugadorRepositorio jugadorRepo;

    @Autowired
    private PadreRepositorio padreRepo;
    
    @Autowired
    private EntrenadorRepositorio entrenadorRepo;

    /**
	 * {@inheritDoc}
	 */

    @Override
    public Optional<Equipo> buscarPorId(String idEquipo) 
    {
        return equipoRepo.findById(idEquipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    public void crearEquipo(String idEquipo, String nombreEquipo, String categoria, String escudo, String password) 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(idEquipo);
        equipo.setNombreEquipo(nombreEquipo);
        equipo.setCategoria(categoria);
        equipo.setEscudoEquipo(escudo);
        equipo.setPassword(password);
        equipo.setJugadores(new ArrayList<>());
        equipo.setPadres(new ArrayList<>());
        equipoRepo.save(equipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    public void crearEquipo(Equipo equipo) 
    {
        if (equipo.getJugadores() == null) equipo.setJugadores(new ArrayList<>());
        if (equipo.getPadres() == null) equipo.setPadres(new ArrayList<>());
        equipoRepo.save(equipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    public void modificarEquipo(String idEquipo, String nombreEquipo, String categoria, String escudoEquipo,
                                String password, String fotoEquipo) {
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe."));

        equipo.setNombreEquipo(nombreEquipo);
        equipo.setCategoria(categoria);
        equipo.setEscudoEquipo(escudoEquipo);
        equipo.setPassword(password);
        equipo.setFotoEquipo(fotoEquipo);
        equipoRepo.save(equipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    public void modificarEquipo(Equipo equipo) 
    {
        equipoRepo.findById(equipo.getIdEquipo())
                .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + equipo.getIdEquipo() 
                + " no existe."));
        equipoRepo.save(equipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    public void eliminarEquipo(String idEquipo) 
    {
        equipoRepo.deleteById(idEquipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    @Transactional
    public void agregarJugadorAEquipo(String idEquipo, String idJugador) 
    {
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Jugador jugador = jugadorRepo.findById(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        if (equipo.getJugadores() == null) equipo.setJugadores(new ArrayList<>());

        if (equipo.getJugadores().contains(jugador)) 
        {
            throw new RuntimeException("El jugador ya está en el equipo");
        }

        equipo.getJugadores().add(jugador);
        jugador.setEquipo(equipo);

        jugadorRepo.save(jugador);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    @Transactional
    public List<Jugador> obtenerJugadoresDeEquipo(String idEquipo) 
    {
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        if (equipo.getJugadores() == null) equipo.setJugadores(new ArrayList<>());
        equipo.getJugadores().size(); 
        return equipo.getJugadores();
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    @Transactional
    public void agregarPadreAEquipo(String idEquipo, String idPadre) 
    {
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Padre padre = padreRepo.findById(idPadre)
                .orElseThrow(() -> new RuntimeException("Padre no encontrado"));

        if (equipo.getPadres() == null) equipo.setPadres(new ArrayList<>());

        if (equipo.getPadres().contains(padre)) {
            throw new RuntimeException("El padre ya está en el equipo");
        }

        equipo.getPadres().add(padre);
   
        equipoRepo.save(equipo);
    }
    
    /**
	 * {@inheritDoc}
	 */

    @Override
    @Transactional
    public List<Padre> obtenerPadresDeEquipo(String idEquipo) 
    {
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado: " + idEquipo));

        if (equipo.getPadres() == null) equipo.setPadres(new ArrayList<>());
        equipo.getPadres().size();
        return equipo.getPadres();
    }
    
    /**
	 * {@inheritDoc}
	 */
    
    public List<EquipoSeleccionDTO> obtenerEquiposPorPadre(String idPadre) 
    {
        List<Equipo> equipos = equipoRepo.findEquiposByIdPadre(idPadre);
        return equipos.stream()
            .map(EquipoSeleccionDTO::new)
            .collect(Collectors.toList());
    }
    
    /**
	 * {@inheritDoc}
	 */
    
    public void asignarEquipoAEntrenador(String idEntrenador, String idEquipo) 
    {
        Entrenador entrenador = entrenadorRepo.findById(idEntrenador)
            .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado"));
        
        Equipo equipo = equipoRepo.findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));

        entrenador.setEquipo(equipo);
        entrenadorRepo.save(entrenador);
    }
    
    /**
	 * {@inheritDoc}
	 */
    
    @Override
    @Transactional
    public void establecerClasificacion(String idEquipo, String Clasificacion) 
    {
        equipoRepo.actualizarClasificacion(idEquipo, Clasificacion);
    }
}