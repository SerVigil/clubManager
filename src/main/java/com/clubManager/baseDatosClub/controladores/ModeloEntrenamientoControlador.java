package com.clubManager.baseDatosClub.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;
import com.clubManager.baseDatosClub.servicios.EquipoServicio;
import com.clubManager.baseDatosClub.servicios.ModeloEntrenamientoServicio;

/**
 * Controlador REST para gestionar modelos de entrenamiento vinculados a un equipo específico.
 * 
 * Permite listar, buscar, crear, modificar y eliminar modelos de entrenamiento predefinidos.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/modeloEntrenamiento")
public class ModeloEntrenamientoControlador {
	
	//Area de datos

    @Autowired
    private ModeloEntrenamientoServicio modeloEntrenamientoServicio;
    
    @Autowired
    private EquipoServicio equipoServicio;
    
    //Métodos principales

    /**
     * Lista todos los modelos de entrenamiento asociados a un equipo.
     * 
     * @param idEquipo Identificador del equipo.
     * @return Lista de modelos de entrenamiento del equipo.
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public List<ModeloEntrenamiento> listarPorEquipo(@PathVariable String idEquipo) 
    {
        return modeloEntrenamientoServicio.listarModelosPorEquipo(idEquipo);
    }

    /**
     * Busca un modelo específico por su ID y el equipo al que pertenece.
     * 
     * @param idEquipo Identificador del equipo.
     * @param idModelo Identificador del modelo.
     * @return Modelo encontrado, si existe y pertenece al equipo.
     */
    
    @GetMapping("/equipo/{idEquipo}/modelo/{idModelo}")
    public ModeloEntrenamiento buscarPorIdYEquipo
    		(
            @PathVariable String idEquipo,
            @PathVariable Long idModelo
            ) 
    {
        return modeloEntrenamientoServicio.buscarPorIdYEquipo(idEquipo, idModelo)
                .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado para el equipo: " + idEquipo));
    }

    /**
     * Crea un nuevo modelo de entrenamiento vinculado a un equipo.
     * 
     * Este método se usa para definir plantillas base reutilizables dentro de los entrenamientos de un equipo.
     * 
     * @param idEquipo Identificador del equipo al que se vincula el modelo.
     * @param nombre Nombre del modelo.
     * @param tipo Tipo de entrenamiento.
     * @param duracion Duración estimada.
     * @param observaciones Comentarios adicionales.
     */
    
    @PostMapping("/crearConEquipo")
    public void crearModeloConEquipo
    		(
            @RequestParam String idEquipo,
            @RequestParam String nombre,
            @RequestParam String tipo,
            @RequestParam Integer duracion,
            @RequestParam String observaciones
            ) 
    {
        Equipo equipo = equipoServicio.buscarPorId(idEquipo)
            .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado: " + idEquipo));

        modeloEntrenamientoServicio.crearModelo(equipo, nombre, tipo, duracion, observaciones);
    }

    /**
     * Modifica un modelo de entrenamiento vinculado a un equipo.
     * 
     * @param idEquipo Identificador del equipo.
     * @param idModelo Identificador del modelo.
     * @param nombre Nuevo nombre.
     * @param tipo Nuevo tipo.
     * @param duracion Nueva duración.
     * @param observaciones Nuevas observaciones.
     */
    
    @PutMapping("/equipo/{idEquipo}/modelo/{idModelo}")
    public void modificarModeloPorEquipo
    		(
            @PathVariable String idEquipo,
            @PathVariable Long idModelo,
            @RequestParam String nombre,
            @RequestParam String tipo,
            @RequestParam Integer duracion,
            @RequestParam String observaciones
            ) 
    {
        modeloEntrenamientoServicio.modificarModeloPorEquipo(idEquipo, idModelo, nombre, tipo, duracion, observaciones);
    }

    /**
     * Elimina un modelo de entrenamiento vinculado a un equipo.
     * 
     * @param idEquipo Identificador del equipo.
     * @param idModelo Identificador del modelo.
     */
    
    @DeleteMapping("/equipo/{idEquipo}/modelo/{idModelo}")
    public void eliminarModeloPorEquipo
    		(
            @PathVariable String idEquipo,
            @PathVariable Long idModelo
            ) 
    {
        modeloEntrenamientoServicio.eliminarModeloPorEquipo(idEquipo, idModelo);
    }

    /**
     * Elimina todos los modelos de entrenamiento asociados a un equipo.
     * 
     * @param idEquipo Identificador del equipo.
     */
    
    @DeleteMapping("/equipo/{idEquipo}/todos")
    public void eliminarTodosLosModelosDeEquipo(@PathVariable String idEquipo) 
    {
        modeloEntrenamientoServicio.eliminarTodosLosModelosDeEquipo(idEquipo);
    }
}