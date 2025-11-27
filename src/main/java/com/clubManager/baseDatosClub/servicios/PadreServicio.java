package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.PadreRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Padre;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con la entidad {@link Padre}.
 * 
 * @author Sergio Vigil Soto
 */

public interface PadreServicio {

    /**
     * Busca un padre por su identificador único.
     * 
     * @param id el ID del padre a buscar
     * @return un {@link Optional} que puede contener el padre si existe
     */
    
    Optional<Padre> buscarPorId(String idPadre);
    
    /**
     * Modifica los datos de un padre existente a partir de un objeto Padre.
     * @param padre objeto padre
     */
    
    void modificarPadre(Padre padre);
    
    /**
     * Crea un nuevo padre en el sistema a partir de un objeto {@link Padre}.
     * 
     * @param padre objeto Padre con todos los datos a registrar
     */
    
    void crearPadre(Padre padre);

    /**
     * Crea un nuevo padre en el sistema con los datos proporcionados.
     * 
     * @param idPadre identificador único del padre
     * @param nombre nombre del padre
     * @param apellidos apellidos del padre
     * @param dni documento nacional de identidad
     * @param direccion dirección del padre
     * @param tipoUsuario tipo de usuario 
     * @param password contraseña de acceso
     * @param foto ruta de la imagen del usuario
     * @param telefono número de teléfono
     * @param email correo electrónico
     * @param vinculo vínculo con el jugador
     */
    
    void crearPadre(String idPadre, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                    String password, String foto, String telefono, String email, String vinculo);

    /**
     * Modifica los datos de un padre existente.
     * 
     * @param idPadre identificador del padre a modificar
     * @param nombre nuevo nombre
     * @param apellidos nuevos apellidos
     * @param dni nuevo DNI
     * @param direccion nueva dirección
     * @param tipoUsuario nuevo tipo de usuario
     * @param password nueva contraseña
     * @param foto nueva imagen 
     * @param telefono nuevo número de teléfono
     * @param email nuevo email
     * @param vinculo nuevo vínculo con el jugador
     */
    
    void modificarPadre(String idPadre, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                        String password, String foto, String telefono, String email, String vinculo);

    /**
     * Elimina un padre del sistema por su ID.
     * 
     * @param id identificador del padre a eliminar
     */
    
    void eliminarPadre(String idPadre);

    /**
     * Busca un padre por su número de DNI.
     * 
     * @param dni documento nacional de identidad
     * @return un {@link Optional} que puede contener el padre si existe
     */
    
    Optional<Padre> buscarPorDni(String dni);
    
    /**
     * Lista todos los padres registrados en el equipo indicado.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de padres
     */
    
    List<PadreRelacionDTO> listarPadresPorEquipo(String idEquipo);
}