package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.dto.PadreRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.servicios.PadreServicio;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de padres.
 * Proporciona endpoints para operaciones CRUD: crear, listar, modificar y eliminar padres.
 * Todos los métodos utilizan {@code idPadre} como identificador único.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/padres")
public class PadreControlador {
	
	//Area de datos

    @Autowired
    private PadreServicio padreServicio;

    /**
     * Busca un padre por su {@code idPadre}.
     * 
     * @param idPadre identificador del padre
     * @return objeto Optional con el padre encontrado (vacío si no existe)
     */
    
    @GetMapping("/{idPadre}")
    public Optional<Padre> buscarPorId(@PathVariable String idPadre) 
    {
        return padreServicio.buscarPorId(idPadre);
    }

    /**
     * Crea un nuevo padre a partir de los datos recibidos en formato JSON.
     * 
     * @param padre objeto {@link Padre} con los datos a registrar
     */
    
    @PostMapping
    public void crearPadre(@Valid @RequestBody Padre padre) 
    {
        padreServicio.crearPadre(padre);
    }

    /**
     * Modifica los datos de un padre existente identificado por {@code idPadre}.
     * 
     * @param idPadre identificador del padre a modificar
     * @param padre objeto {@link Padre} con los nuevos datos
     */
    
    @PutMapping("/{idPadre}")
    public void modificarPadre
    		(
    		@PathVariable String idPadre,
    		@Valid @RequestBody Padre padre
    		) 
    {
        padre.setIdPadre(idPadre);
        padreServicio.modificarPadre(padre);
    }

    /**
     * Elimina un padre por su {@code idPadre}.
     * 
     * @param idPadre identificador del padre a eliminar
     */
    
    @DeleteMapping("/{idPadre}")
    public void eliminarPadre(@PathVariable String idPadre) 
    {
        padreServicio.eliminarPadre(idPadre);
    }

    /**
     * Busca un padre a partir de su DNI.
     * 
     * @param dni documento nacional de identidad
     * @return objeto Optional con el padre encontrado (vacío si no existe)
     */
    
    @GetMapping("/dni/{dni}")
    public Optional<Padre> buscarPorDni(@PathVariable String dni) 
    {
        return padreServicio.buscarPorDni(dni);
    }
    
    /**
     * Obtiene la lista de padres registrados en el equipo indicado.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de padres en formato DTO
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public List<PadreRelacionDTO> obtenerPadresPorEquipo(@PathVariable String idEquipo) 
    {
        return padreServicio.listarPadresPorEquipo(idEquipo);
    }
}