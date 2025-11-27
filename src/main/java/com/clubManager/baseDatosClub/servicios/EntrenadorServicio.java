package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Entrenador;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con los entrenadores.
 * 
 * La clave primaria de esta entidad es el ID (tipo String).
 * 
 * @author Sergio Vigil Soto
 */
public interface EntrenadorServicio {

    /**
     * Lista todos los entrenadores registrados en el sistema.
     * 
     * @return lista completa de entrenadores
     */
	
    List<Entrenador> listarEntrenadores();

    /**
     * Busca un entrenador por su ID.
     * 
     * @param id ID del entrenador
     * @return un Optional que puede contener el entrenador si existe
     */
    
    Optional<Entrenador> buscarPorId(String idEntrenador);

    /**
     * Crea un nuevo entrenador en el sistema a partir de los campos individuales.
     * 
     * @param idEntrenador identificador del entrenador
     * @param nombre nombre del entrenador
     * @param apellidos apellidos del entrenador
     * @param dni DNI del entrenador
     * @param direccion dirección del entrenador
     * @param tipoUsuario tipo de usuario asignado
     * @param password contraseña del entrenador
     * @param foto URL o ruta de la foto del entrenador
     * @param telefono número de teléfono del entrenador
     * @param email correo electrónico del entrenador
     */
    
    void crearEntrenador(String idEntrenador, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                         String password, String foto, String telefono, String email);

    /**
     * Crea un nuevo entrenador a partir de un objeto Entrenador.
     * 
     * @param entrenador objeto entrenador a guardar
     */
    
    void crearEntrenador(Entrenador entrenador);

    /**
     * Modifica los datos de un entrenador existente a partir de los campos individuales.
     * 
     * @param idEntrenador ID del entrenador a modificar
     * @param nombre nuevo nombre
     * @param apellidos nuevos apellidos
     * @param dni nuevo DNI
     * @param direccion nueva dirección
     * @param tipoUsuario nuevo tipo de usuario
     * @param password nueva contraseña
     * @param foto nueva foto
     * @param telefono nuevo teléfono
     * @param email nuevo email
     */
    
    void modificarEntrenador(String idEntrenador, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                             String password, String foto, String telefono, String email);

    /**
     * Modifica los datos de un entrenador existente a partir de un objeto Entrenador.
     * 
     * @param entrenador objeto entrenador con los datos actualizados
     */
    
    void modificarEntrenador(Entrenador entrenador);

    /**
     * Elimina un entrenador del sistema.
     * 
     * @param idEntrenador ID del entrenador a eliminar
     */
    
    void eliminarEntrenador(String idEntrenador);

    /**
     * Busca un entrenador por su DNI.
     * 
     * @param dni DNI del entrenador
     * @return un Optional que puede contener el entrenador si existe
     */
    
    Optional<Entrenador> buscarPorDni(String dni);
}
