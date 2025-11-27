package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.InformeDTO;
import com.clubManager.baseDatosClub.dto.InformeDetalleDTO;
import com.clubManager.baseDatosClub.dto.InformeResumenDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Informe;
import com.clubManager.baseDatosClub.servicios.EntrenadorServicio;
import com.clubManager.baseDatosClub.servicios.JugadorServicio;
import com.clubManager.baseDatosClub.servicios.InformeServicio;

import jakarta.transaction.Transactional;

/**
 * Controlador REST para gestionar operaciones sobre la entidad {@link Informe}.
 * 
 * Expone endpoints para consultar, crear, modificar y eliminar informes.
 * 
 * Cada informe está vinculado a un equipo mediante su identificador único.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/informes")
public class InformeControlador {
	
	//Area de datos

    @Autowired
    private InformeServicio informeServicio;

    @Autowired
    private EntrenadorServicio entrenadorServicio;

    @Autowired
    private JugadorServicio jugadorServicio;
    
    //Métodos principales
    
    /**
    * Obtiene un informe concreto de un equipo.
    * 
    * @param idInforme identificador único del informe
    * @param idEquipo identificador único del equipo
    * @return {@link InformeDetalleDTO} con los datos del informe, 
    *         404 si no existe o 500 si ocurre un error
    */
    
    @Transactional
    @GetMapping("/{idInforme}/equipo/{idEquipo}")
    public ResponseEntity<InformeDetalleDTO> obtenerInforme
    (
    		@PathVariable Long idInforme, 
    		@PathVariable String idEquipo) 
    {
        try 
        {
            Optional<Informe> informeOpt = informeServicio.buscarPorIdYEquipo(idInforme, idEquipo);
            if (informeOpt.isEmpty()) 
            {
                return ResponseEntity.notFound().build();
            }

            Informe informe = informeOpt.get();

            InformeDetalleDTO dto = new InformeDetalleDTO();
            dto.setFecha(informe.getFecha() != null ? informe.getFecha().toString() : "Sin fecha");
            dto.setTipo(informe.getTipo() != null ? informe.getTipo() : "Sin tipo");
            dto.setContenido(informe.getContenido() != null ? informe.getContenido() : "Sin contenido");
            dto.setNombre(informe.getEntrenador() != null ? informe.getEntrenador().getNombre() : "Desconocido");

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Lista todos los informes asociados a un equipo.
     * 
     * @param idEquipo identificador único del equipo
     * @return lista de {@link InformeResumenDTO} con los informes encontrados
     */

    @Transactional
    @GetMapping("/equipo/{idEquipo}")
    public List<InformeResumenDTO> listarPorEquipo(@PathVariable String idEquipo)
    {
    	return informeServicio.listarInformesPorEquipo(idEquipo);
    }
    
    /**
     * Crea un nuevo informe a partir de los datos recibidos.
     * 
     * @param dto objeto {@link InformeDTO} con los datos del informe
     * @return {@link InformeDTO} con los datos del informe creado
     */

    @Transactional
    @PostMapping("/crear")
    public InformeDTO crearInforme(@RequestBody InformeDTO dto) 
    {
        Informe informe = informeServicio.crearInforme(dto);
        return new InformeDTO
        		(
            informe.getIdInforme(),
            informe.getFecha(),
            informe.getContenido(),
            informe.getTipo(),
            informe.getEntrenador().getIdEntrenador(),
            informe.getJugador() != null ? informe.getJugador().getIdJugador() : null,
            informe.getEquipo() != null ? informe.getEquipo().getIdEquipo() : null
        		);
    }
    
    /**
     * Modifica un informe existente de un equipo.
     * 
     * @param idInforme identificador único del informe
     * @param idEquipo identificador único del equipo
     * @param dto objeto {@link InformeDTO} con los nuevos datos
     */

    @PutMapping("/{idInforme}/equipo/{idEquipo}")
    public void modificarInforme
    		(
    		@PathVariable Long idInforme, 
    		@PathVariable String idEquipo, 
    		@RequestBody InformeDTO dto
    		) 
    {
        Entrenador entrenador = entrenadorServicio.buscarPorId(dto.getIdEntrenador())
            .orElseThrow(() -> new IllegalArgumentException("Entrenador con ID " + 
            		dto.getIdEntrenador() + " no existe."));

        Jugador jugador = null;
        if (dto.getIdJugador() != null && !dto.getIdJugador().isEmpty()) 
        {
            jugador = jugadorServicio.buscarPorId(dto.getIdJugador())
                .orElseThrow(() -> new IllegalArgumentException("Jugador con ID " + dto.getIdJugador() + " no existe."));
        }
        informeServicio.modificarInforme(idInforme, dto.getFecha(), dto.getContenido(), dto.getTipo(), entrenador, jugador);
    }
    
    /**
     * Elimina un informe concreto de un equipo.
     * 
     * @param idInforme identificador único del informe
     * @param idEquipo identificador único del equipo
     * @return respuesta vacía con código 204 No Content
     */

    @DeleteMapping("/eliminar/{idInforme}/equipo/{idEquipo}")
    @Transactional
    public ResponseEntity<Void> eliminarInforme
    		(
    		@PathVariable Long idInforme,
    		@PathVariable String idEquipo
    		)        
    {
        informeServicio.eliminarInforme(idInforme, idEquipo);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Elimina todos los informes asociados a un equipo.
     * 
     * @param idEquipo identificador único del equipo
     * @return mensaje de confirmación de la eliminación
     */

    @DeleteMapping("/eliminar/equipo/{idEquipo}")
    public String eliminarInformesPorEquipo(@PathVariable String idEquipo) 
    {
        informeServicio.eliminarInformesPorEquipo(idEquipo);
        return "Informes del equipo con ID " + idEquipo + " eliminados correctamente.";
    }
}