package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.dto.JugadorRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Lesion;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.entidades.Sancion;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.LesionRepositorio;
import com.clubManager.baseDatosClub.repositorios.PadreRepositorio;
import com.clubManager.baseDatosClub.repositorios.SancionRepositorio;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz {@link JugadorServicio}.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class JugadorServicioImpl implements JugadorServicio {

	//Area de datos
	
    @Autowired
    private JugadorRepositorio jugadorRepositorio;
    
    @Autowired
    private PadreRepositorio padreRepositorio;
    
    @Autowired
    private EquipoRepositorio equipoRepositorio;
    
    @Autowired
    private SancionRepositorio sancionRepo;
    
    @Autowired
    private LesionRepositorio lesionRepo;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern NOMBRE_APELLIDOS_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    
    //Métodos principales

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Jugador> buscarPorId(String idJugador) 
    {
        return jugadorRepositorio.findById(idJugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void crearJugador(String idJugador, String nombre, String apellidos, String dni, String direccion, 
    						 String tipoUsuario, String password, String telefono, String email,
                             String posicion, LocalDate fechaNacimiento) 
    {
        validarDatosJugador(idJugador, nombre, apellidos, dni, direccion, tipoUsuario, password, 
        		telefono, email, posicion, fechaNacimiento);

        Jugador jugador = new Jugador();
        jugador.setIdJugador(idJugador);
        jugador.setNombre(nombre);
        jugador.setApellidos(apellidos);
        jugador.setDni(dni);
        jugador.setDireccion(direccion);
        jugador.setTipoUsuario(tipoUsuario);
        jugador.setPassword(password);
        jugador.setTelefono(telefono);
        jugador.setEmail(email);
        jugador.setPosicion(posicion);
        jugador.setFechaNacimiento(fechaNacimiento);
        jugador.setActivo(true);
        jugadorRepositorio.save(jugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void modificarJugador(String idJugador, String nombre, String apellidos, String dni, String direccion, 
    							 String tipoUsuario, String password, String foto, String telefono, String email,
                                 String posicion, LocalDate fechaNacimiento) 
    {
        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con el id: " + idJugador));

        validarDatosJugador(idJugador, nombre, apellidos, dni, direccion, tipoUsuario, password,
        		telefono, email, posicion, fechaNacimiento);

        jugador.setNombre(nombre);
        jugador.setApellidos(apellidos);
        jugador.setDni(dni);
        jugador.setDireccion(direccion);
        jugador.setTipoUsuario(tipoUsuario);
        jugador.setPassword(password);
        jugador.setFoto(foto);
        jugador.setTelefono(telefono);
        jugador.setEmail(email);
        jugador.setPosicion(posicion);
        jugador.setFechaNacimiento(fechaNacimiento);

        jugadorRepositorio.save(jugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    public boolean eliminarJugador(String idJugador, String idEquipo) 
    {
        Optional<Jugador> jugadorOpt = jugadorRepositorio.findById(idJugador);

        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();

            if (jugador.getEquipo() != null && 
                jugador.getEquipo().getIdEquipo().equals(idEquipo)) {

                jugadorRepositorio.delete(jugador);
                return true;
            }
        }
        return false;
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador crearJugador(Jugador jugador) 
    {
        validarDatosJugador(jugador.getIdJugador(), jugador.getNombre(), jugador.getApellidos(), jugador.getDni(), jugador.getDireccion(),
                jugador.getTipoUsuario(), jugador.getPassword(), jugador.getTelefono(), jugador.getEmail(),
                jugador.getPosicion(), jugador.getFechaNacimiento());
        return jugadorRepositorio.save(jugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Jugador> obtenerJugadorPorId(String idJugador) 
    {
        return jugadorRepositorio.findById(idJugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador actualizarJugador(Jugador jugador) 
    {
        if (jugador.getIdJugador() == null || jugadorRepositorio.findById(jugador.getIdJugador()).isEmpty()) 
        {
            throw new IllegalArgumentException("No existe jugador con ese ID.");
        }

        validarDatosJugador(jugador.getIdJugador(), jugador.getNombre(), jugador.getApellidos(), jugador.getDni(), jugador.getDireccion(),
                jugador.getTipoUsuario(), jugador.getPassword(), jugador.getTelefono(), jugador.getEmail(),
                jugador.getPosicion(), jugador.getFechaNacimiento());

        return jugadorRepositorio.save(jugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void eliminarJugadorPorId(String idJugador) 
    {
        jugadorRepositorio.deleteById(idJugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public List<Jugador> listarJugadoresActivosPorEquipo(String idEquipo) 
    {
        return jugadorRepositorio.findByActivoTrueAndEquipo_idEquipo(idEquipo);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador cambiarEstadoActivo(String idJugador, boolean activo) 
    {
        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setActivo(activo);
        return jugadorRepositorio.save(jugador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador incrementarGoles(String idJugador, Integer goles) 
    {
        if (goles <= 0) 
        {
            throw new IllegalArgumentException("Los goles a incrementar deben ser positivos.");
        }

        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setGoles(jugador.getGoles() + goles);
        return jugadorRepositorio.save(jugador);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador incrementarGolesEncajados(String idJugador, Integer golesEncajados) 
    {
        if (golesEncajados <= 0) 
        {
            throw new IllegalArgumentException("Los goles a incrementar deben ser positivos.");
        }

        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setGolesEncajados(jugador.getGolesEncajados() + golesEncajados);
        return jugadorRepositorio.save(jugador);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
	public Jugador incrementarRojas(String idJugador, Integer tarjetasRojas) 
    {
    	if (tarjetasRojas <= 0) 
        {
            throw new IllegalArgumentException("Las tarjetas rojas a incrementar deben ser positivas.");
        }

        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setTarjetasRojas(jugador.getTarjetasRojas() + tarjetasRojas);
        return jugadorRepositorio.save(jugador);
	}

	@Override
	public Jugador incrementarAmarillas(String idJugador, Integer tarjetasAmarillas) 
	{
		if (tarjetasAmarillas <= 0) 
        {
            throw new IllegalArgumentException("Las tarjetas amarillas a incrementar deben ser positivas.");
        }

        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setTarjetasAmarillas(jugador.getTarjetasAmarillas() + tarjetasAmarillas);
        return jugadorRepositorio.save(jugador);
	}
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    @Transactional
    public Jugador vincularPadreAJugador(String idJugador, String idPadre) 
    {
        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + idJugador));

        Padre padre = padreRepositorio.findById(idPadre)
            .orElseThrow(() -> new IllegalArgumentException("Padre no encontrado: " + idPadre));
        
        if (jugador.getPadres().contains(padre)) {
            throw new IllegalStateException("El padre ya está vinculado al jugador");
        }

        jugador.getPadres().add(padre);
        return jugadorRepositorio.save(jugador);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public List<JugadorRelacionDTO> listarJugadoresPorEquipo(String idEquipo) 
    {
        List<Jugador> jugadores = jugadorRepositorio.findByEquipo_IdEquipo(idEquipo);
        return jugadores.stream()
            .map(j -> new JugadorRelacionDTO(j.getIdJugador(), j.getNombre(), j.getApellidos()))
            .toList();
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    @Transactional
    public void eliminarJugadoresPorEquipo(String idEquipo) 
    {
        if (!equipoRepositorio.existsById(idEquipo)) {
            throw new IllegalArgumentException("El equipo con ID '" + idEquipo + "' no existe.");
        }
        jugadorRepositorio.deleteByEquipo_IdEquipo(idEquipo);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    @Transactional
    public void reactivarJugadorYEliminarEstado(String idJugador) {
        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado con ID: " + idJugador));

        Optional<Sancion> sancion = sancionRepo.findFirstByJugador_IdJugador(idJugador);
        sancion.ifPresent(s -> sancionRepo.deleteById(s.getIdSancion()));

        Optional<Lesion> lesion = lesionRepo.findFirstByJugador_IdJugador(idJugador);
        lesion.ifPresent(l -> lesionRepo.deleteById(l.getIdLesion()));

        jugador.setActivo(true);
        jugadorRepositorio.save(jugador);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Jugador> obtenerJugadorPorEquipo(String idJugador, String idEquipo) 
    {
        return jugadorRepositorio.findByIdJugadorAndEquipo_IdEquipo(idJugador, idEquipo);
    }
    
    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Jugador incrementarPuntos(String idJugador, Integer puntos) 
    {
        if (puntos == null || puntos <= 0) 
        {
            throw new IllegalArgumentException("Los puntos a incrementar deben ser un valor positivo.");
        }

        Jugador jugador = jugadorRepositorio.findById(idJugador)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el jugador con id: " + idJugador));

        int puntosPrevios = jugador.getPuntosTotales() != null ? jugador.getPuntosTotales() : 0;
        int nuevosPuntos = puntosPrevios + puntos;

        jugador.setPuntosTotales(nuevosPuntos);

        return jugadorRepositorio.save(jugador);
    }
    
    /** 
     * {@inheritDoc} 
     */

    @Override
    public List<JugadorPuntuacionDTO> obtenerRankingPorEquipo(String idEquipo) 
    {
        List<Jugador> jugadores = jugadorRepositorio.findByEquipo_IdEquipoOrderByPuntosTotalesDesc(idEquipo);

        List<JugadorPuntuacionDTO> ranking = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            JugadorPuntuacionDTO dto = new JugadorPuntuacionDTO();
            dto.setIdJugador(jugador.getIdJugador());
            dto.setNombre(jugador.getNombre());
            dto.setPuntosTotales(jugador.getPuntosTotales());
            ranking.add(dto);
        }
        return ranking;
    }
    
    /**
     * Valida los datos obligatorios de un jugador y lanza una excepción si alguno es incorrecto.
     */
    
    private void validarDatosJugador(String idJugador, String nombre, String apellidos, String dni, String direccion, 
    								 String tipoUsuario,
                                     String password, String telefono, String email,
                                     String posicion, LocalDate fechaNacimiento) 
    {

    	if (idJugador == null || idJugador.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El id es obligatorio.");
        }
        if (nombre == null || !NOMBRE_APELLIDOS_PATTERN.matcher(nombre).matches()) 
        {
            throw new IllegalArgumentException("El nombre es obligatorio y solo debe contener letras y espacios.");
        }
        if (apellidos == null || !NOMBRE_APELLIDOS_PATTERN.matcher(apellidos).matches()) 
        {
            throw new IllegalArgumentException("Los apellidos son obligatorios y solo deben contener letras y espacios.");
        }
        if (dni == null || !DNI_PATTERN.matcher(dni).matches()) 
        {
            throw new IllegalArgumentException("El DNI no es válido. Debe tener 8 números seguidos de una letra.");
        }
        if (direccion == null || direccion.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio.");
        }
        if (password == null || password.length() < 6) 
        {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (telefono == null || !TELEFONO_PATTERN.matcher(telefono).matches()) 
        {
            throw new IllegalArgumentException("El teléfono no es válido.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) 
        {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (posicion == null || posicion.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("La posición es obligatoria.");
        }
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) 
        {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida.");
        }
    }
}