package com.clubManager.baseDatosClub.controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Sancion;
import com.clubManager.baseDatosClub.servicios.SancionServicio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Sancion.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/sancion")
public class SancionControlador {

    @Autowired
    private SancionServicio sancionServicio;

    /**
     * Busca una sanción por su identificador único.
     * 
     * @param id ID de la sanción a buscar
     * @return la sanción encontrada (lanza excepción si no existe)
     */
    
    @GetMapping("/{id}")
    public Sancion buscarPorId(@PathVariable Long id) 
    {
        return sancionServicio.buscarSancionId(id);
    }

    /**
     * Crea una nueva sanción a partir de parámetros individuales enviados por formulario o URL.
     * 
     * @param fecha fecha en la que se impone la sanción (formato yyyy-MM-dd)
     * @param tipoSancion tipo o categoría de la sanción
     * @param descripcion descripción o motivo de la sanción
     * @param duracion duración de la sanción
     * @param jugadorId identificador del jugador al que se le aplica la sanción
     * @return sanción creada
     */
    
    @PostMapping("/crear-form")
    public Sancion crearSancionPorParametros(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam String tipoSancion,
        @RequestParam String descripcion,
        @RequestParam String duracion,
        @RequestParam String jugadorId
    ) 
    {
        return sancionServicio.crearSancion(fecha, tipoSancion, descripcion, duracion, jugadorId);
    }

    /**
     * Crea una nueva sanción a partir de un objeto JSON recibido en el cuerpo de la petición.
     * 
     * @param sancion objeto Sancion con todos los datos necesarios
     * @return sanción creada
     */
    
    @PostMapping("/crear")
    public Sancion crearSancion(@RequestBody Sancion sancion) 
    {
        return sancionServicio.crearSancion(sancion);
    }

    /**
     * Modifica una sanción existente a partir de parámetros individuales.
     * 
     * @param id ID de la sanción a modificar
     * @param fecha nueva fecha en la que se impone la sanción
     * @param tipoSancion nuevo tipo o categoría de la sanción
     * @param descripcion nueva descripción o motivo de la sanción
     * @param duracion nueva duración de la sanción
     * @param jugadorId nuevo identificador del jugador sancionado
     * @return sanción modificada
     */
    
    @PutMapping("/modificar-form/{id}")
    public Sancion modificarSancionPorParametros(
        @PathVariable Long id,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam String tipoSancion,
        @RequestParam String descripcion,
        @RequestParam String duracion,
        @RequestParam String jugadorId
    ) 
    {
        return sancionServicio.modificarSancion(id, fecha, tipoSancion, descripcion, duracion, jugadorId);
    }

    /**
     * Modifica una sanción existente a partir de un objeto JSON recibido.
     * El objeto debe contener un ID válido ya existente.
     * 
     * @param sancion objeto Sancion con los datos a actualizar
     * @return sanción modificada
     */
    
    @PutMapping("/modificar")
    public Sancion modificarSancion(@RequestBody Sancion sancion) 
    {
        return sancionServicio.modificarSancion(sancion);
    }

    /**
     * Elimina una sanción por su ID.
     * 
     * @param id ID de la sanción a eliminar
     */
    
    @DeleteMapping("/eliminar/{id}")
    public void eliminarSancion(@PathVariable Long id) 
    {
        sancionServicio.eliminarSancion(id);
    }
    
    /**
     * Lista los jugadores sancionados de un equipo.
     * 
     * @param idEquipo ID del equipo
     * @return lista de sancionados
     */
	
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<Jugador>> listarSancionadosPorEquipo(@PathVariable String idEquipo) {
        // El servicio devuelve jugadores sancionados activos de ese equipo
        List<Jugador> sancionados = sancionServicio.listarSancionadosPorEquipo(idEquipo);
        return ResponseEntity.ok(sancionados);
    }

    /**
     * Registra una nueva sanción y desactiva al jugador.
     * 
     * @param idJugador ID del jugador sancionado
     */
    
    @PostMapping("/{idJugador}")
    public ResponseEntity<String> registrarSanción(@PathVariable String idJugador) 
    {
        try 
        {
            sancionServicio.registrarSancionYDesactivarJugador(idJugador);
            return ResponseEntity.ok("Sanción registrada y jugador desactivado correctamente");
        } 
        catch (EntityNotFoundException e) 
        {
            return ResponseEntity.badRequest().body("❌ Jugador no encontrado: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("⚠️ Error al registrar la sanción: " + e.getMessage());
        }
    }

    /**
     * Elimina la sanción del jugador y lo reactiva.
     * 
     * @param idJugador ID del jugador
     */
    
    @DeleteMapping("/{idJugador}")
    public ResponseEntity<String> eliminarSancion(@PathVariable String idJugador) 
    {
        try {
            sancionServicio.eliminarSancionYReactivarJugador(idJugador);
            return ResponseEntity.ok("Sanción eliminada y jugador reactivado correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Jugador no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar la sanción: " + e.getMessage());
        }
    }
}