package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.EntrenamientoDTO;
import com.clubManager.baseDatosClub.entidades.Entrenamiento;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.servicios.EntrenamientoServicio;
import com.clubManager.baseDatosClub.servicios.NotificacionPushServicio;

import jakarta.transaction.Transactional;

/**
 * Controlador REST para gestionar las operaciones CRUD sobre la entidad {@link Entrenamiento}.
 * 
 * Proporciona endpoints para listar, buscar, crear, modificar y eliminar entrenamientos,
 * filtrando siempre por el equipo al que pertenecen.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/entrenamientos")
public class EntrenamientoControlador {
	
	//Area de Datos

    @Autowired
    private EntrenamientoServicio entrenamientoServicio;

    @Autowired
    private NotificacionPushServicio notificacionPush;
    
    //Métodos principales

    /**
     * Obtiene un entrenamiento específico asociado a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idEntrenamiento identificador del entrenamiento
     * @return ResponseEntity con el entrenamiento si existe, o 404 si no se encuentra
     */
    
    @GetMapping("/{idEntrenamiento}/equipo/{idEquipo}")
    public ResponseEntity<EntrenamientoDTO> obtenerEntrenamiento
    		(
            @PathVariable Long idEntrenamiento,
            @PathVariable String idEquipo
            ) 
    {
        Optional<EntrenamientoDTO> entrenamiento = entrenamientoServicio.buscarPorIdYEquipo(idEntrenamiento, idEquipo);
        if (entrenamiento.isEmpty()) 
        {
            return ResponseEntity.notFound().build();
        }

        EntrenamientoDTO e = entrenamiento.get();

        EntrenamientoDTO dto = new EntrenamientoDTO
        		(
                e.getIdEntrenamiento(),
                e.getFecha(),
                e.getHora(),
                e.getTipo(),
                e.getObservaciones(),
                e.getIdEquipo(),
                e.getIdModelo(),
                e.getNombre()
        		);
        return ResponseEntity.ok(dto);
    }

    /**
     * Lista todos los entrenamientos pertenecientes a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return lista de entrenamientos del equipo
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<EntrenamientoDTO>> listarPorEquipo(@PathVariable String idEquipo) {
        List<Entrenamiento> lista = entrenamientoServicio.listarPorEquipo(idEquipo);

        List<EntrenamientoDTO> respuesta = lista.stream()
            .map(e -> new EntrenamientoDTO
            		(
                    e.getIdEntrenamiento(),
                    e.getFecha(),
                    e.getHora(),
                    e.getTipo(),
                    e.getObservaciones(),
                    e.getEquipo() != null ? e.getEquipo().getIdEquipo() : null,
                    e.getModeloEntrenamiento() != null ? e.getModeloEntrenamiento().getIdModelo() : null,
                    e.getModeloEntrenamiento() != null ? e.getModeloEntrenamiento().getNombre() : null
            		))
            .toList();

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Crea un nuevo entrenamiento para un equipo y envía una notificación push
     * a los usuarios registrados del equipo.
     *
     * @param dto DTO con los datos del entrenamiento
     * @return ResponseEntity con el entrenamiento creado
     */
    
    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<EntrenamientoDTO> crearEntrenamiento(@RequestBody EntrenamientoDTO dto) 
    {
        Entrenamiento creado = entrenamientoServicio.crearEntrenamiento(dto);

        if (creado.getEquipo() != null) 
        {
            Equipo equipo = creado.getEquipo();
            String titulo = "Nuevo entrenamiento programado";
            String mensaje = String.format("Se ha programado un entrenamiento el %s a las %s.",
                    creado.getFecha(), creado.getHora());
            notificacionPush.enviarNotificacionAEquipo(equipo, titulo, mensaje);
        }

        EntrenamientoDTO respuesta = new EntrenamientoDTO
        		(
                creado.getIdEntrenamiento(),
                creado.getFecha(),
                creado.getHora(),
                creado.getTipo(),
                creado.getObservaciones(),
                creado.getEquipo() != null ? creado.getEquipo().getIdEquipo() : null,
                creado.getModeloEntrenamiento() != null ? creado.getModeloEntrenamiento().getIdModelo() : null,
                creado.getModeloEntrenamiento() != null ? creado.getModeloEntrenamiento().getNombre() : null
        		);
        return ResponseEntity.ok(respuesta);
    }
    
    /**
     * Modifica un entrenamiento existente para un equipo específico.
     * 
     * @param idEntrenamiento identificador del entrenamiento que se desea modificar
     * @param idEquipo identificador del equipo al que pertenece el entrenamiento
     * @param dto objeto DTO con los nuevos datos del entrenamiento
     * @return ResponseEntity con el entrenamiento actualizado, o 404 si no se encuentra
     */
    
    @Transactional
    @PutMapping("/{idEntrenamiento}/equipo/{idEquipo}")
    public ResponseEntity<EntrenamientoDTO> modificarEntrenamiento
    	(
            @PathVariable Long idEntrenamiento,
            @PathVariable String idEquipo,
            @RequestBody EntrenamientoDTO dto
         ) 
    {
        Optional<EntrenamientoDTO> existente = entrenamientoServicio.buscarPorIdYEquipo(idEntrenamiento, idEquipo);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Entrenamiento actualizado = entrenamientoServicio.modificarEntrenamiento(dto);

        if (actualizado.getEquipo() != null) {
            Equipo equipo = actualizado.getEquipo();
            String titulo = "Entrenamiento modificado";
            String mensaje = String.format
            		(
                    "El entrenamiento ha sido actualizado para el %s a las %s.",
                    actualizado.getFecha(), actualizado.getHora()
            		);
            notificacionPush.enviarNotificacionAEquipo(equipo, titulo, mensaje);
        }

        EntrenamientoDTO respuesta = new EntrenamientoDTO
        		(
                actualizado.getIdEntrenamiento(),
                actualizado.getFecha(),
                actualizado.getHora(),
                actualizado.getTipo(),
                actualizado.getObservaciones(),
                actualizado.getEquipo() != null ? actualizado.getEquipo().getIdEquipo() : null,
                actualizado.getModeloEntrenamiento() != null ? actualizado.getModeloEntrenamiento().getIdModelo() : null,
                actualizado.getModeloEntrenamiento() != null ? actualizado.getModeloEntrenamiento().getNombre() : null		
                );

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Elimina un entrenamiento concreto de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param idEntrenamiento identificador del entrenamiento a eliminar
     * @return ResponseEntity si la eliminación fue exitosa
     */
    
    @DeleteMapping("/eliminar/{idEntrenamiento}/equipo/{idEquipo}")
    @Transactional
    public ResponseEntity<Void> eliminarEntrenamiento
    		(
    		@PathVariable Long idEntrenamiento,
    		@PathVariable String idEquipo
    		)       
    {
        entrenamientoServicio.eliminarEntrenamientoPorIdYEquipo(idEntrenamiento, idEquipo);
        return ResponseEntity.noContent().build();
    }

    /**
     * Elimina todos los entrenamientos asociados a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return ResponseEntity  si se eliminan correctamente
     */
    
    @DeleteMapping("/equipo/{idEquipo}")
    public ResponseEntity<Void> eliminarTodosPorEquipo(@PathVariable String idEquipo) 
    {
        entrenamientoServicio.eliminarTodosPorEquipo(idEquipo);
        return ResponseEntity.noContent().build();
    }
}