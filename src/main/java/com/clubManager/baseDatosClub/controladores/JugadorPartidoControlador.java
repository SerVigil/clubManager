package com.clubManager.baseDatosClub.controladores;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.entidades.JugadorPartido;
import com.clubManager.baseDatosClub.entidades.JugadorPartidoPK;
import com.clubManager.baseDatosClub.servicios.JugadorPartidoServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gestionar la entidad {@link JugadorPartido}.
 * 
 * Proporciona endpoints para operaciones CRUD con la relación entre jugadores y partidos.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/jugadorPartidos")
public class JugadorPartidoControlador {

	//Area de datos
	
    @Autowired
    private JugadorPartidoServicio jugadorPartidoServicio;
    
    //Constructor
    
    public JugadorPartidoControlador(JugadorPartidoServicio jugadorPartidoServicio) 
    {
        this.jugadorPartidoServicio = jugadorPartidoServicio;
    }
    
    //Métodos principales

    /**
     * Obtiene la lista de todas las relaciones jugador-partido.
     * 
     * @return lista completa de {@link JugadorPartido}
     */
    
    @GetMapping
    public List<JugadorPartido> listarTodos() 
    {
        return jugadorPartidoServicio.findAll();
    }

    /**
     * Guarda una nueva relación entre jugador y partido.
     * 
     * @param jugadorPartido objeto a guardar
     * @return objeto guardado
     */
    
    @PostMapping
    public JugadorPartido guardar(@RequestBody JugadorPartido jugadorPartido) 
    {
        return jugadorPartidoServicio.save(jugadorPartido);
    }

    /**
     * Elimina una relación jugador-partido por su clave compuesta.
     * 
     * @param idJugador ID del jugador
     * @param idPartido ID del partido
     * @param fecha Fecha del partido
     */
    
    @DeleteMapping
    public void eliminarPorId
    (
        @RequestParam String idJugador,
        @RequestParam Long idPartido
    ) 
    {
        JugadorPartidoPK id = new JugadorPartidoPK(idJugador, idPartido);
        jugadorPartidoServicio.deleteById(id);
    }

    /**
     * Lista todas las relaciones de un jugador concreto.
     * 
     * @param idJugador ID del jugador
     * @return lista de partidos asociados al jugador
     */
    
    @GetMapping("/jugador/{idJugador}")
    public List<JugadorPartido> listarPorJugador(@PathVariable String idJugador) 
    {
        return jugadorPartidoServicio.findByIdJugador(idJugador);
    }

    /**
     * Lista todos los jugadores relacionados con un partido.
     * 
     * @param idPartido ID del partido
     * @return lista de jugadores asociados al partido
     */
    
    @GetMapping("/{idPartido}/jugadores")
    public ResponseEntity<List<JugadorPartido>> obtenerJugadoresDelPartido(@PathVariable Long idPartido) 
    {
        List<JugadorPartido> jugadores = jugadorPartidoServicio.findByIdPartido(idPartido);
        return ResponseEntity.ok(jugadores);
    }
    
    /**
     * Asocia varios jugadores a un partido.
     *
     * @param idPartido    ID del partido.
     * @param idsJugadores Lista de IDs de jugadores a asociar.
     * @return Respuesta HTTP indicando éxito o fallo.
     */
    
    @PutMapping("/{idPartido}/jugadores")
    public ResponseEntity<?> asignarJugadoresAPartido
    	(
            @PathVariable Long idPartido,
            @RequestBody List<String> idsJugadores
        ) 
    {
        try 
        {
            jugadorPartidoServicio.asignarJugadoresAPartido(idPartido, idsJugadores);
            return ResponseEntity.ok("Jugadores asignados correctamente al partido " + idPartido);
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(404).body(e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("Error al asignar jugadores: " + e.getMessage());
        }
    }
    
    @PutMapping("/{idJugador}/{idPartido}/puntuacion")
    public ResponseEntity<JugadorPuntuacionDTO> asignarPuntuacion
    		(
            @PathVariable String idJugador,
            @PathVariable Long idPartido,
            @RequestParam("puntuacion") Integer puntuacion
            ) 
    {
        JugadorPuntuacionDTO dto = jugadorPartidoServicio.asignarPuntuacion(idJugador, idPartido, puntuacion);
        return ResponseEntity.ok(dto);
    }
}