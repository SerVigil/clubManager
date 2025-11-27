package com.clubManager.baseDatosClub.controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.servicios.EntrenadorServicio;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de entrenadores.
 * Proporciona endpoints para crear, modificar, eliminar, listar y consultar entrenadores.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorControlador {

    @Autowired
    private EntrenadorServicio entrenadorServicio;

    /**
     * Devuelve la lista completa de entrenadores registrados.
     * 
     * @return lista de objetos {@link Entrenador}
     */
    
    @GetMapping
    public List<Entrenador> listarEntrenadores() 
    {
        return entrenadorServicio.listarEntrenadores();
    }

    /**
     * Busca un entrenador según su identificador único {@code idEntrenador}.
     * 
     * @param idEntrenador identificador del entrenador
     * @return objeto {@link Optional} con el entrenador encontrado (vacío si no existe)
     */
    
    @GetMapping("/{idEntrenador}")
    public Optional<Entrenador> buscarPorId(@PathVariable String idEntrenador) 
    {
        return entrenadorServicio.buscarPorId(idEntrenador);
    }

    /**
     * Crea un nuevo entrenador a partir de los datos enviados como JSON en el cuerpo de la petición.
     * 
     * @param entrenador objeto {@link Entrenador} con todos los datos.
     */
    
    @PostMapping
    public void crearEntrenador(@RequestBody Entrenador entrenador) 
    {
        entrenadorServicio.crearEntrenador(
            entrenador.getIdEntrenador(),
            entrenador.getNombre(),
            entrenador.getApellidos(),
            entrenador.getDni(),
            entrenador.getDireccion(),
            entrenador.getTipoUsuario(),
            entrenador.getPassword(),
            entrenador.getFoto(),
            entrenador.getTelefono(),
            entrenador.getEmail()
        );
    }

    /**
     * Modifica un entrenador existente a partir de su {@code idEntrenador} y los datos enviados como JSON.
     * 
     * @param idEntrenador identificador del entrenador a modificar
     * @param entrenador objeto {@link Entrenador} con los nuevos datos
     */
    
    @PutMapping("/{idEntrenador}")
    public void modificarEntrenador(@PathVariable String idEntrenador, @RequestBody Entrenador entrenador) 
    {
        entrenadorServicio.modificarEntrenador
        (
            idEntrenador,
            entrenador.getNombre(),
            entrenador.getApellidos(),
            entrenador.getDni(),
            entrenador.getDireccion(),
            entrenador.getTipoUsuario(),
            entrenador.getPassword(),
            entrenador.getFoto(),
            entrenador.getTelefono(),
            entrenador.getEmail()
        );
    }

    /**
     * Elimina un entrenador por su identificador único {@code idEntrenador}.
     * 
     * @param idEntrenador identificador del entrenador a eliminar
     */
    
    @DeleteMapping("/{idEntrenador}")
    public void eliminarEntrenador(@PathVariable String idEntrenador) 
    {
        entrenadorServicio.eliminarEntrenador(idEntrenador);
    }

    /**
     * Busca un entrenador por su número de DNI.
     * 
     * @param dni documento nacional de identidad del entrenador
     * @return objeto {@link Optional} con el entrenador encontrado (vacío si no existe)
     */
    
    @GetMapping("/dni/{dni}")
    public Optional<Entrenador> buscarPorDni(@PathVariable String dni) 
    {
        return entrenadorServicio.buscarPorDni(dni);
    }

    /**
     * Registra un nuevo entrenador en el sistema a partir de los datos enviados en formato JSON.
     *
     * En caso de que la validación sea correcta, se almacena el entrenador en la base de datos.
     * 
     * @param entrenador objeto {@link Entrenador} recibido en el cuerpo de la petición,
     * que contiene todos los datos necesarios para su registro.
     * 
     * @return {@link ResponseEntity} con código de estado {@code 201 Created} y un mapa
     *         que incluye el {@code idEntrenador} registrado.
     */
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarEntrenador(@Valid @RequestBody Entrenador entrenador) 
    {
        entrenadorServicio.crearEntrenador(entrenador);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("idEntrenador", entrenador.getIdEntrenador()));
    }
}