package com.clubManager.baseDatosClub.controladores;

import java.util.List;

import org.apache.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clubManager.baseDatosClub.dto.EquipoPartidoDTO;
import com.clubManager.baseDatosClub.dto.EquipoPartidoResultadoDTO;
import com.clubManager.baseDatosClub.entidades.EquipoPartido;
import com.clubManager.baseDatosClub.entidades.EquipoPartidoPK;
import com.clubManager.baseDatosClub.servicios.EquipoPartidoServicio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para gestionar la entidad {@link EquipoPartido}.
 * 
 * Proporciona endpoints para operaciones CRUD relacionadas con la relación entre equipos y partidos.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/equipoPartidos")
public class EquipoPartidoControlador {
	
	//Area de Datos

    @Autowired
    private EquipoPartidoServicio equipoPartidoServicio;

    /**
     * Obtiene la lista de todas las relaciones equipo-partido.
     * 
     * @return lista completa de {@link EquipoPartido}
     */
    
    @GetMapping
    public List<EquipoPartidoDTO> listarTodos()
    {
        return equipoPartidoServicio.listarTodos();
    }

    /**
     * Guarda una relación entre equipo y partido.
     * 
     * @param equipoPartido objeto a guardar
     * @return objeto guardado
     */
    
    @PostMapping
    public EquipoPartido guardar(@RequestBody EquipoPartido equipoPartido) 
    {
        return equipoPartidoServicio.save(equipoPartido);
    }
    
    /**
     * Elimina todas las relaciones equipo_partido para un equipo dado
     * @param idEquipo identificador del equipo
     */
    
    @DeleteMapping("/todos/{idEquipo}")
    public void eliminarTodosPorEquipo(@PathVariable String idEquipo) {
        equipoPartidoServicio.deleteByPartido_Equipo_IdEquipo(idEquipo);
    }


    /**
     * Elimina una relación equipo_partido por su clave compuesta.
     * 
     * @param idEquipo ID del equipo
     * @param idPartido ID del partido
     */
    
    @DeleteMapping
    public void eliminarPorId
    (
        @RequestParam String idEquipo,
        @RequestParam Long idPartido
    ) 
    {
        EquipoPartidoPK id = new EquipoPartidoPK(idEquipo, idPartido);
        equipoPartidoServicio.deleteById(id);
    }

    /**
     * Lista todos los partidos en los que ha participado un equipo.
     * 
     * @param idEquipo ID del equipo
     * @return lista de {@link EquipoPartido} asociados al equipo
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public List<EquipoPartido> listarPorEquipo(@PathVariable String idEquipo) 
    {
        return equipoPartidoServicio.findByIdEquipo(idEquipo);
    }
    
    /**
     * Asigna un resultado a un partido existente.
     *
     * @param idPartido  ID del partido.
     * @param idEquipo   ID del equipo (parte de la clave compuesta).
     * @param resultado  Resultado a asignar (por ejemplo, "2-1").
     * @return DTO con información del partido y equipos.
     */
    
    @PutMapping("/{idPartido}/equipos/{idEquipo}/resultado")
    public ResponseEntity<?> asignarResultado(
            @PathVariable Long idPartido,
            @PathVariable String idEquipo,
            @RequestParam String resultado) {

        try {
            EquipoPartidoPK pk = new EquipoPartidoPK(idEquipo, idPartido);

            EquipoPartidoResultadoDTO dto = equipoPartidoServicio.asignarResultado(pk, resultado);

            if (dto == null) 
            {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                        .body("No se encontró información para el partido o equipo indicado");
            }
            return ResponseEntity.ok(dto);

        } 
        catch (EntityNotFoundException e) 
        {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(e.getMessage());
        } 
        catch (Exception e)
        {
            return ResponseEntity.internalServerError()
                    .body("Error al asignar el resultado: " + e.getMessage());
        }
    }  
}