package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.EquipoSeleccionDTO;
import com.clubManager.baseDatosClub.dto.LoginRequest;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.servicios.EquipoServicio;

/**
 * Controlador REST para la gestión de equipos.
 * 
 * Todos los métodos utilizan el campo {@code idEquipo} como identificador único.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/equipos")
public class EquipoControlador {

    @Autowired
    private EquipoServicio equipoServicio;
    
    @PostMapping("/validar")
    public ResponseEntity<?> validarEquipo(@RequestBody LoginRequest request) 
    {
        String idEquipo = request.getIdentificador();
        String password = request.getPassword();

        Equipo equipo = equipoServicio.buscarPorId(idEquipo).orElse(null);
        if (equipo != null && equipo.getPassword().equals(password)) 
        {
            return ResponseEntity.ok(true);
        } 
        else 
        {
            return ResponseEntity.status(401).body("Identificador o contraseña incorrectos");
        }
    }

    /**
     * Busca un equipo por su identificador único {@code idEquipo}.
     * 
     * @param idEquipo identificador del equipo
     * @return {@link ResponseEntity} con el equipo encontrado o 404 si no existe
     */
    
    @GetMapping("/{idEquipo}")
    @Transactional
    public ResponseEntity<Equipo> buscarPorId(@PathVariable String idEquipo) {
        Optional<Equipo> optEquipo = equipoServicio.buscarPorId(idEquipo);
        if (optEquipo.isPresent()) {
            Equipo equipo = optEquipo.get();
            equipo.getJugadores().size();
            return ResponseEntity.ok(equipo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo equipo a partir de los datos enviados como JSON en el cuerpo de la petición.
     * 
     * @param equipo objeto {@link Equipo} con todos los datos requeridos
     */
    
    @PostMapping("/registrar")
    public ResponseEntity<?> crearEquipo(@RequestBody Equipo equipo, Authentication authentication) {
        equipoServicio.crearEquipo(
            equipo.getIdEquipo(),
            equipo.getNombreEquipo(),
            equipo.getCategoria(),
            equipo.getEscudoEquipo(),
            equipo.getPassword()
        );

        String idEntrenador = (String) authentication.getPrincipal(); 

        try
        {
            equipoServicio.asignarEquipoAEntrenador(idEntrenador, equipo.getIdEquipo());
            return ResponseEntity.ok("Equipo creado y asignado correctamente al entrenador.");
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar el equipo al entrenador: " + e.getMessage());
        }
    }


    /**
     * Modifica un equipo existente a partir de su {@code idEquipo} y los datos enviados como JSON.
     * 
     * @param idEquipo identificador del equipo a modificar
     * @param equipo objeto {@link Equipo} con los nuevos datos
     */
    
    @PutMapping("/{idEquipo}")
    public void modificarEquipo(@PathVariable String idEquipo, @RequestBody Equipo equipo) {
        equipoServicio.modificarEquipo(
            idEquipo,
            equipo.getNombreEquipo(),
            equipo.getCategoria(),
            equipo.getEscudoEquipo(),
            equipo.getPassword(),
            equipo.getFotoEquipo()
        );
    }

    /**
     * Elimina un equipo por su identificador único {@code idEquipo}.
     * 
     * @param idEquipo identificador del equipo a eliminar
     */
    
    @DeleteMapping("/{idEquipo}")
    public void eliminarEquipo(@PathVariable String idEquipo) 
    {
        equipoServicio.eliminarEquipo(idEquipo);
    }

    /**
     * Verifica si existe un equipo con el identificador proporcionado.
     *
     * @param idEquipo identificador del equipo
     * @return {@link ResponseEntity} con {@code true} si existe, {@code false} si no
     */
    
    @GetMapping("/existe/{idEquipo}")
    public ResponseEntity<Boolean> existeEquipo(@PathVariable String idEquipo) 
    {
        boolean existe = equipoServicio.buscarPorId(idEquipo).isPresent();
        return ResponseEntity.ok(existe);
    }

    /**
     * Agrega un jugador a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @param idJugador identificador del jugador a agregar
     * @return {@link ResponseEntity} con mensaje de éxito o error
     */
    
    @PostMapping("/{idEquipo}/jugadores/{idJugador}")
    public ResponseEntity<?> agregarJugador
    		(
            @PathVariable String idEquipo,
            @PathVariable String idJugador
            ) 
    {
        try 
        {
            equipoServicio.agregarJugadorAEquipo(idEquipo, idJugador);
            return ResponseEntity.ok("Jugador agregado correctamente");
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Devuelve la lista de jugadores de un equipo.
     * Inicializa la colección de jugadores para evitar LazyInitializationException.
     * 
     * @param idEquipo identificador del equipo
     * @return {@link ResponseEntity} con la lista de jugadores o 404 si el equipo no existe
     */
    
    @GetMapping("/{idEquipo}/jugadores")
    @Transactional
    public ResponseEntity<List<Jugador>> verJugadoresDelEquipo(@PathVariable String idEquipo) 
    {
        try 
        {
            List<Jugador> jugadores = equipoServicio.obtenerJugadoresDeEquipo(idEquipo);
            return ResponseEntity.ok(jugadores);
        } 
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Agrega un padre a un equipo existente.
     *
     * @param idEquipo identificador único del equipo
     * @param idPadre identificador único del padre
     * @return 200 OK si se agrega correctamente, 400 Bad Request si ocurre un error
     */
    
    @PostMapping("/{idEquipo}/padres/{idPadre}")
    public ResponseEntity<?> agregarPadre(
            @PathVariable String idEquipo,
            @PathVariable String idPadre) 
    {
        try 
        {
            equipoServicio.agregarPadreAEquipo(idEquipo, idPadre);
            return ResponseEntity.ok("Padre agregado correctamente");
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Devuelve la lista de padres asociados a un equipo.
     *
     * @param idEquipo identificador único del equipo
     * @return lista de padres o 404 Not Found si ocurre un error
     */
    
    @GetMapping("/{idEquipo}/padres")
    @Transactional
    public ResponseEntity<List<Padre>> verPadresDelEquipo(@PathVariable String idEquipo) 
    {
        try 
        {
            List<Padre> padres = equipoServicio.obtenerPadresDeEquipo(idEquipo);
            return ResponseEntity.ok(padres);
        } 
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Obtiene los equipos asociados a un padre concreto.
     *
     * @param idPadre identificador único del padre
     * @return lista de equipos en formato DTO
     */
    
    @GetMapping("/porPadre/{idPadre}")
    public ResponseEntity<List<EquipoSeleccionDTO>> obtenerEquiposPorPadre(@PathVariable String idPadre) 
    {
        List<EquipoSeleccionDTO> equipos = equipoServicio.obtenerEquiposPorPadre(idPadre);
        return ResponseEntity.ok(equipos);
    }
    
    /**
     * Establece la clasificación de un equipo.
     *
     * @param idEquipo identificador único del equipo
     * @param clasificacion nueva clasificación a asignar
     * @return 200 OK tras actualizar la clasificación
     */
    
    @PutMapping("/{idEquipo}/clasificacion")
    public ResponseEntity<Void> establecerClasificacion
    (
            @PathVariable String idEquipo,
            @RequestBody String clasificacion
    ) 
    {
        equipoServicio.establecerClasificacion(idEquipo, clasificacion);
        return ResponseEntity.ok().build();
    }
}