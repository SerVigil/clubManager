package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.JugadorDetalleDTO;
import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.dto.JugadorRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.servicios.JugadorServicio;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Jugador.
 * Proporciona endpoints para listar, buscar, crear, modificar y eliminar jugadores,
 * así como para gestionar su estado, estadísticas...
 * 
 * @author Sergio Vigil Soto
 */
@RestController
@RequestMapping("/api/jugadores")
public class JugadorControlador {
	
    @Autowired
    private JugadorServicio jugadorServicio;
    
    /**
     * Obtiene un jugador por su ID, verificando que pertenezca al equipo indicado.
     *
     * Este método busca un jugador en la base de datos utilizando el identificador del jugador
     * y el del equipo al que debería pertenecer. Si el jugador existe y está asociado al equipo,
     * se devuelve su información.
     *
     * @param idJugador identificador único del jugador
     * @param idEquipo identificador del equipo al que debe pertenecer el jugador
     * @return ResponseEntity con el jugador encontrado o un estado de error
     */
    
    @GetMapping("/{idJugador}/equipo/{idEquipo}")
    public ResponseEntity<Jugador> obtenerJugadorPorEquipo
    		(
            @PathVariable String idJugador,
            @PathVariable String idEquipo
            ) 
    {
        Optional<Jugador> jugadorOpt = jugadorServicio.obtenerJugadorPorEquipo(idJugador, idEquipo);

        if (jugadorOpt.isPresent())
        {
            return ResponseEntity.ok(jugadorOpt.get());
        }
        else 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Obtiene un jugador específico a partir de su {@code idJugador}.
     * 
     * @param idJugador identificador del jugador
     * @return jugador correspondiente, si existe
     */
    
    @GetMapping("/{idJugador}")
    public Optional<Jugador> obtenerJugadorPorId(@PathVariable String idJugador) 
    {
        return jugadorServicio.obtenerJugadorPorId(idJugador);
    }

    /**
     * Crea un nuevo jugador a partir de un objeto JSON recibido.
     * 
     * @param jugador objeto {@link Jugador} con los datos a registrar
     */
    
    @PostMapping
    public void crearJugador(@Valid @RequestBody Jugador jugador) 
    {
        jugadorServicio.crearJugador(jugador);
    }

    /**
     * Modifica los datos de un jugador existente.
     * El ID se obtiene de la URL y solo se actualizan los campos permitidos.
     *
     * @param idJugador ID del jugador a modificar
     * @param jugadorDTO objeto {@link JugadorDetalleDTO} con los nuevos datos
     */
    
    @PutMapping("/{idJugador}")
    public void modificarJugador(@PathVariable String idJugador, @Valid @RequestBody JugadorDetalleDTO jugadorDTO) 
    {
        Jugador jugador = jugadorServicio.obtenerJugadorPorId(idJugador)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setApellidos(jugadorDTO.getApellidos());
        jugador.setDni(jugadorDTO.getDni());
        jugador.setDireccion(jugadorDTO.getDireccion());
        jugador.setTelefono(jugadorDTO.getTelefono());
        jugador.setEmail(jugadorDTO.getEmail());
        jugador.setDorsal(jugadorDTO.getDorsal());
        jugador.setPosicion(jugadorDTO.getPosicion());
        jugador.setFechaNacimiento(jugadorDTO.getFechaNacimiento());

        jugadorServicio.actualizarJugador(jugador);
    }

    /**
     * Elimina un jugador solo si pertenece al equipo indicado.
     * 
     * @param idJugador ID del jugador a eliminar.
     * @param idEquipo ID del equipo al que debe pertenecer el jugador.
     * @return Respuesta con código 200 si se elimina correctamente, 404 si no pertenece o no existe.
     */
    
    @DeleteMapping("/{idJugador}/equipo/{idEquipo}")
    public ResponseEntity<Void> eliminarJugador
    		(
            @PathVariable String idJugador,
            @PathVariable String idEquipo
            )
    {
        boolean eliminado = jugadorServicio.eliminarJugador(idJugador, idEquipo);

        if (eliminado) 
        {
            return ResponseEntity.ok().build();
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Devuelve una lista con todos los jugadores que se encuentran activos
     * y que pertenecen a un equipo.
     * 
     * @return lista de jugadores activos de un equipo dado
     */
    
    @GetMapping("/{idEquipo}/jugadores/activos")
    public ResponseEntity<List<Jugador>> listarJugadoresActivosPorEquipo
    		(
    		@PathVariable String idEquipo
    		) 
    {
        List<Jugador> jugadores = jugadorServicio.listarJugadoresActivosPorEquipo(idEquipo);
        return ResponseEntity.ok(jugadores);
    }
    
    /**
     * Incrementa la cantidad de tarjetas rojas recibidas por un jugador.
     *
     * @param idJugador identificador único del jugador
     * @param tarjetasRojas cantidad de tarjetas rojas a sumar (debe ser positiva)
     * @return jugador actualizado con las tarjetas rojas incrementadas
     * @throws IllegalArgumentException si el jugador no existe o la cantidad es inválida
     */
    
    @PutMapping("/{idJugador}/incrementarRojas")
    public Jugador incrementarRojas
    		(
    		@PathVariable String idJugador, 
    		@RequestParam("tarjetasRojas") Integer tarjetasRojas
    		) 
    {
        if (tarjetasRojas <= 0) 
        {
            throw new IllegalArgumentException("Las tarjetas rojas a incrementar deben ser positivas.");
        }

        Jugador jugador = jugadorServicio.obtenerJugadorPorId(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setTarjetasRojas(jugador.getTarjetasRojas() + tarjetasRojas);
        return jugadorServicio.incrementarRojas(idJugador, tarjetasRojas);
    }

    /**
     * Incrementa la cantidad de tarjetas amarillas recibidas por un jugador.
     *
     * @param idJugador identificador único del jugador
     * @param tarjetasAmarillas cantidad de tarjetas amarillas a sumar (debe ser positiva)
     * @return jugador actualizado con las tarjetas amarillas incrementadas
     * @throws IllegalArgumentException si el jugador no existe o la cantidad es inválida
     */
    
    @PutMapping("/{idJugador}/incrementarAmarillas")
    public Jugador incrementarAmarillas
    		(
    		@PathVariable String idJugador, 
    		@RequestParam("tarjetasAmarillas") Integer tarjetasAmarillas
    		) 
    {
    	
        if (tarjetasAmarillas <= 0) 
        {
            throw new IllegalArgumentException("Las tarjetas amarillas a incrementar deben ser positivas.");
        }

        Jugador jugador = jugadorServicio.obtenerJugadorPorId(idJugador)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el jugador con id: " + idJugador));

        jugador.setTarjetasAmarillas(jugador.getTarjetasAmarillas() + tarjetasAmarillas);
        return jugadorServicio.incrementarAmarillas(idJugador, tarjetasAmarillas);
    }

    /**
     * Cambia el estado activo/inactivo de un jugador.
     * 
     * @param idJugador ID del jugador
     * @param activo nuevo estado booleano (true = activo, false = inactivo)
     * @return jugador actualizado con el nuevo estado
     */
    
    @PutMapping("/{idJugador}/estado")
    public Jugador cambiarEstadoActivo
    		(
    		@PathVariable String idJugador, 
    		@RequestParam("activo") boolean activo
    		)
    {
        return jugadorServicio.cambiarEstadoActivo(idJugador, activo);
    }

    /**
     * Incrementa la cantidad de goles anotados por un jugador.
     * 
     * @param idJugador ID del jugador
     * @param goles cantidad de goles a sumar
     * @return jugador con goles actualizados
     */
    
    @PutMapping("/{idJugador}/incrementarGoles")
    public Jugador incrementarGoles(@PathVariable String idJugador, @RequestParam("goles") Integer goles)
    {
        return jugadorServicio.incrementarGoles(idJugador, goles);
    }
    
    /**
     * Incrementa la cantidad de goles anotados por un jugador.
     * 
     * @param idJugador ID del jugador
     * @param goles cantidad de goles a sumar
     * @return jugador con goles actualizados
     */
    
    @PutMapping("/{idJugador}/incrementarGolesEncajados")
    public Jugador incrementarGolesEncajados
    		(
    		@PathVariable String idJugador, 
    		@RequestParam("golesEncajados") Integer golesEncajados
    		) 
    {
        return jugadorServicio.incrementarGolesEncajados(idJugador, golesEncajados);
    }
    
    /**
     * Incrementa los puntos totales de un jugador.
     *
     * @param idJugador ID del jugador al que se sumarán los puntos.
     * @param puntos    Cantidad de puntos a añadir.
     * @return Jugador actualizado con los nuevos puntos totales.
     */
    
    @PutMapping("/{idJugador}/incrementarPuntos")
    public ResponseEntity<Jugador> incrementarPuntosTotales
    		(
            @PathVariable String idJugador,
            @RequestParam("puntos") Integer puntos
            ) 
    {
        try 
        {
            Jugador jugadorActualizado = jugadorServicio.incrementarPuntos(idJugador, puntos);
            return ResponseEntity.ok(jugadorActualizado);
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().build();
        } 
        catch (EntityNotFoundException e) 
        {
            return ResponseEntity.notFound().build();
        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Obtiene la lista de jugadores que pertenecen a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de jugadores en formato DTO
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public List<JugadorRelacionDTO> obtenerJugadoresPorEquipo(@PathVariable String idEquipo)
    {
        return jugadorServicio.listarJugadoresPorEquipo(idEquipo);
    }

    /**
     * Vincula un padre existente a un jugador, añadiendo la relación en la base de datos.
     * 
     * @param idJugador identificador del jugador
     * @param idPadre identificador del padre
     * @return respuesta vacía con estado 200 si la vinculación fue exitosa
     */
    
    @PostMapping("/{idJugador}/padres/{idPadre}")
    public ResponseEntity<Void> vincularPadreAJugador(@PathVariable String idJugador, @PathVariable String idPadre) 
    {
        jugadorServicio.vincularPadreAJugador(idJugador, idPadre);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina todos los jugadores que pertenecen al equipo indicado.
     * 
     * @param idEquipo identificador del equipo
     * @return respuesta HTTP indicando éxito o error
     */
    
    @DeleteMapping("/eliminarPorEquipo/{idEquipo}")
    public ResponseEntity<?> eliminarJugadoresPorEquipo(@PathVariable String idEquipo) 
    {
        try 
        {
            jugadorServicio.eliminarJugadoresPorEquipo(idEquipo);
            return ResponseEntity.ok("Jugadores del equipo '" + idEquipo + "' eliminados correctamente.");
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("Error inesperado al eliminar jugadores.");
        }
    
    }
    
    @PutMapping(value = "/reactivar/{idJugador}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> reactivarJugador(@PathVariable String idJugador) {
        try {
            jugadorServicio.reactivarJugadorYEliminarEstado(idJugador);
            return ResponseEntity.ok("Jugador reactivado y estado eliminado correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Jugador no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al reactivar jugador: " + e.getMessage());
        }
    }

    
    /**
     * Devuelve el ranking de jugadores ordenado por puntos de forma descendente.
     *
     * @return Lista de JugadorPuntuacionDTO con el ranking
     */
    
    @GetMapping("/ranking/{idEquipo}")
    public List<JugadorPuntuacionDTO> obtenerRankingPorEquipo(@PathVariable String idEquipo) 
    {
        return jugadorServicio.obtenerRankingPorEquipo(idEquipo);
    }
}