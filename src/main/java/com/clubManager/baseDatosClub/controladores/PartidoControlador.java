package com.clubManager.baseDatosClub.controladores;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.PartidoDTO;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Partido;
import com.clubManager.baseDatosClub.servicios.EquipoServicio;
import com.clubManager.baseDatosClub.servicios.NotificacionPushServicio;
import com.clubManager.baseDatosClub.servicios.PartidoServicio;

import jakarta.transaction.Transactional;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Partido.
 * Proporciona endpoints para listar, buscar, crear, modificar y eliminar partidos,
 * entre otras opciones
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/partidos")
public class PartidoControlador {

	//Area de datos
	
    @Autowired
    private PartidoServicio partidoServicio;
    
    @Autowired
    private EquipoServicio equipoServicio; 
    
    @Autowired
    private NotificacionPushServicio notificacionPush; 
    
    //Métodos principales

    /**
     * Busca un partido por su identificador único.
     * 
     * @param idPartido ID del partido a buscar
     * @return Optional con el partido encontrado o vacío si no existe
     */
    
    @GetMapping("/{idPartido}/equipo/{idEquipo}")
    public Optional<Partido> buscarPorIdPartidoYIdEquipo
    (
    		@PathVariable Long idPartido,
    		@PathVariable String idEquipo) 
    {
        return partidoServicio.buscarPorIdPartidoYEquipos_IdEquipo(idPartido, idEquipo);
    }

    /**
     * Crea un nuevo partido a partir de un objeto JSON recibido en el cuerpo de la petición.
     * 
     * @param partido objeto Partido con todos los datos necesarios
     */
    
    @PostMapping("/crear")
    @Transactional 
    public ResponseEntity<PartidoDTO> crearPartido(@RequestBody PartidoDTO dto) 
    {
        PartidoDTO respuesta = partidoServicio.crearPartido(dto);

        String idEquipo = respuesta.getIdEquipo();
        
        if (idEquipo != null) 
        {
            Equipo equipo = equipoServicio.buscarPorId(idEquipo).orElse(null);
            if (equipo != null) 
            {
                String titulo = "¡Nuevo partido programado!";
                String mensaje = String.format("Se ha programado un partido el dia %s en %s, entre %s y %s.",
                        respuesta.getFecha(), respuesta.getLugar(), respuesta.getLocal(),
                        respuesta.getVisitante());
                notificacionPush.enviarNotificacionAEquipo(equipo, titulo, mensaje);
            }
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Modifica un partido existente a partir de un objeto JSON recibido.
     * El ID del partido se recibe como parte de la URL y se asigna al objeto antes de actualizar.
     * 
     * @param idPartido ID del partido a modificar
     * @param partido objeto Partido con los datos actualizados
     */

    @Transactional
    @PutMapping("/{idPartido}")
    public ResponseEntity<PartidoDTO> modificarPartidoPorId
    		(
    		@PathVariable Long idPartido, 
    		@RequestBody PartidoDTO dto
    		) 
    {
        dto.setIdPartido(idPartido);

        Partido partidoModificado = partidoServicio.modificarPartido(dto);
        if (partidoModificado == null) 
        {
            return ResponseEntity.notFound().build();
        }

        PartidoDTO respuesta = new PartidoDTO(
            partidoModificado.getIdPartido(),
            dto.getIdEquipo(),
            partidoModificado.getLocal(),
            partidoModificado.getVisitante(),
            partidoModificado.getFecha(),
            partidoModificado.getTipoPartido(),
            partidoModificado.getLugar()
        );

        String idEquipoStr = dto.getIdEquipo(); 
        if (idEquipoStr != null && !idEquipoStr.isBlank()) 
        {
            Equipo equipo = equipoServicio.buscarPorId(idEquipoStr).orElse(null);
            if (equipo != null)
            {
                String titulo = "Partido Modificado";
                String mensaje = String.format
                		(
                    "El partido ha sido modificado. Nueva fecha: %s en %s, frente a %s.",
                    respuesta.getFecha(),
                    respuesta.getLugar(),
                    respuesta.getLocal(),
                    respuesta.getVisitante()
                		);
                notificacionPush.enviarNotificacionAEquipo(equipo, titulo, mensaje);
            } 
            else 
            {
            	System.err.println("No se encontró el equipo con ID: " + idEquipoStr);
            }
        }
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Elimina un partido por su ID.
     * 
     * @param idPartido ID del partido a eliminar
     */
    
    @DeleteMapping("/eliminar/{idPartido}")
    public void eliminarPartido(@PathVariable Long idPartido) 
    {
        partidoServicio.eliminarPartido(idPartido);
    }

    /**
     * Elimina todos los partidos registrados en la base de datos para un equipo dado.
     * @param idEquipo identificador del equipo del que se quieren eliminar los partidos
     */
    
    @DeleteMapping("/eliminar-todos/{idEquipo}")
    public void eliminarTodosLosPartidos(@PathVariable String idEquipo) 
    {
        partidoServicio.eliminarPartidosPorEquipo(idEquipo);
    }

}