package com.clubManager.baseDatosClub.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Lesion;
import com.clubManager.baseDatosClub.servicios.LesionServicio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Lesion.
 * Proporciona endpoints para listar, buscar, crear, modificar y eliminar lesiones.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/lesion")
public class LesionControlador {
	
	@Autowired
	private LesionServicio lesionServicio;

	/**
	 * Busca un lesionado por su identificador único.
	 * 
	 * @param id Identificador de la lesión
	 * @return Optional con la lesión encontrada o vacío si no existe
	 */
	
	@GetMapping("/{id}")
	public Optional<Lesion> buscarPorId(@PathVariable Long id) 
	{
		return lesionServicio.buscarPorId(id);
	}

	/**
	 * Crea una nueva lesión asociada a un jugador.
	 * 
	 * @param tipo tipo de lesión
	 * @param fechaInicio fecha de inicio de la lesión
	 * @param fechaFin fecha de fin de la lesión
	 * @param descripcion descripción de la lesión
	 * @param idJugador ID del jugador lesionado
	 */
	
	@PostMapping("/crear")
	public void crearLesion
	(
		@RequestParam String tipo,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
		@RequestParam String descripcion,
		@RequestParam String idJugador
	) 
	{
		lesionServicio.crearLesion(tipo, fechaInicio, fechaFin, descripcion, idJugador);
	}

	/**
	 * Modifica una lesión existente.
	 * 
	 * @param id ID de la lesión a modificar
	 * @param tipo nuevo tipo de lesión
	 * @param fechaInicio nueva fecha de inicio
	 * @param fechaFin nueva fecha de fin
	 * @param descripcion nueva descripción
	 */
	
	@PutMapping("/modificar/{id}")
	public void modificarLesion
	(
		@PathVariable Long id,
		@RequestParam String tipo,
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
		@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
		@RequestParam String descripcion
	) 
	{
		lesionServicio.modificarLesion(id, tipo, fechaInicio, fechaFin, descripcion);
	}

	/**
	 * Elimina una lesión por su ID.
	 * 
	 * @param id ID de la lesión a eliminar
	 */
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminarLesion(@PathVariable Long id) 
	{
		lesionServicio.eliminarLesion(id);
	}

	/**
	 * Elimina todas las lesiones de la base de datos.
	 */
	
	@DeleteMapping("/eliminar-todos")
	public void eliminarTodasLasLesiones() 
	{
		lesionServicio.eliminarLesiones();
	}
	
	/**
     * Lista los jugadores lesionados de un equipo.
     * 
     * @param idEquipo ID del equipo
     * @return lista de lesiones activas
     */
	
	@GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<Jugador>> listarLesionadosPorEquipo(@PathVariable String idEquipo) 
	{
        List<Jugador> lesionados = lesionServicio.listarLesionadosPorEquipo(idEquipo);
        return ResponseEntity.ok(lesionados);
    }

    /**
     * Registra una nueva lesión y desactiva al jugador.
     * 
     * @param idJugador ID del jugador lesionado
     */
	
    @PostMapping("/{idJugador}")
    public ResponseEntity<String> registrarLesion(@PathVariable String idJugador) 
    {
        try 
        {
            lesionServicio.registrarLesionYDesactivarJugador(idJugador);
            return ResponseEntity.ok("Lesión registrada y jugador desactivado correctamente");
        } 
        catch (EntityNotFoundException e) 
        {
            return ResponseEntity.badRequest().body("Jugador no encontrado: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("Error al registrar la lesión: " + e.getMessage());
        }
    }

    /**
     * Elimina la lesión activa del jugador y lo reactiva.
     * 
     * @param idJugador ID del jugador recuperado
     */
    
    @DeleteMapping("/{idJugador}")
    public ResponseEntity<String> eliminarLesion(@PathVariable String idJugador) 
    {
        try 
        {
            lesionServicio.eliminarLesionYReactivarJugador(idJugador);
            return ResponseEntity.ok("Lesión eliminada y jugador reactivado correctamente");
        } 
        catch (EntityNotFoundException e) 
        {
            return ResponseEntity.badRequest().body("Jugador no encontrado: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("Error al eliminar la lesión: " + e.getMessage());
        }
    }
}