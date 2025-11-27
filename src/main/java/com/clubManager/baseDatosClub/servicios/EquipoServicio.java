package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.EquipoSeleccionDTO;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con los equipos.
 * 
 * La clave primaria de esta entidad es el ID (tipo String).
 * 
 * @author Sergio Vigil Soto
 */

public interface EquipoServicio {

    /**
     * Busca un equipo por su ID.
     * 
     * @param idEquipo ID del equipo
     * @return un Optional que puede contener el equipo si existe
     */
	
    Optional<Equipo> buscarPorId(String idEquipo);

    /**
     * Crea un nuevo equipo en el sistema a partir de los campos individuales.
     * 
     * @param idEquipo identificador del equipo
     * @param nombreEquipo nombre del equipo
     * @param categoria categoría del equipo
     * @param escudoEquipo URL o ruta de la foto del escudo del equipo
     * @param password contraseña del equipo
     */
    
    void crearEquipo(String idEquipo, String nombreEquipo, String categoria, String escudo, String password);

    /**
     * Crea un nuevo equipo a partir de un objeto Equipo.
     * 
     * @param equipo objeto equipo a guardar
     */
    
    void crearEquipo(Equipo equipo);

    /**
     * Modifica los datos de un equipo existente a partir de los campos individuales.
     * 
     * @param idEquipo ID del equipo a modificar
     * @param nombreEquipo nuevo nombre del equipo
     * @param categoria nueva categoría
     * @param escudoEquipo URL o ruta de la foto del escudo del equipo
     * @param password
     * @param fotoEquipo nueva foto
     */
    
    void modificarEquipo(String idEquipo, String nombreEquipo, String categoria, String escudoEquipo, String password, String fotoEquipo);

    /**
     * Modifica los datos de un equipo existente a partir de un objeto Equipo.
     * 
     * @param equipo objeto equipo con los datos actualizados
     */
    
    void modificarEquipo(Equipo equipo);
    
    /**
     * Elimina un equipo dado su identificador.
     * @param idEquipo Identificador del equipo a eliminar.
     */
    
    void eliminarEquipo(String idEquipo);

    /**
     * Agrega un jugador a un equipo específico.
     * @param idEquipo Identificador del equipo.
     * @param idJugador Identificador del jugador a agregar.
     */
    
    void agregarJugadorAEquipo(String idEquipo, String idJugador);

    /**
     * Obtiene la lista de jugadores pertenecientes a un equipo.
     * @param idEquipo Identificador del equipo.
     * @return Lista de jugadores del equipo.
     */
    
    List<Jugador> obtenerJugadoresDeEquipo(String idEquipo);

    /**
     * Agrega un padre a un equipo específico.
     * @param idEquipo Identificador del equipo.
     * @param idPadre Identificador del padre a agregar.
     */
    
    void agregarPadreAEquipo(String idEquipo, String idPadre);

    /**
     * Obtiene la lista de padres asociados a un equipo.
     * @param idEquipo Identificador del equipo.
     * @return Lista de padres del equipo.
     */
    
    List<Padre> obtenerPadresDeEquipo(String idEquipo);

    /**
     * Obtiene los equipos en los que participa un padre.
     * @param idPadre Identificador del padre.
     * @return Lista de equipos asociados al padre.
     */
    
    List<EquipoSeleccionDTO> obtenerEquiposPorPadre(String idPadre);

    /**
     * Asigna un equipo a un entrenador específico.
     * @param idEntrenador Identificador del entrenador.
     * @param idEquipo Identificador del equipo.
     */
    
    void asignarEquipoAEntrenador(String idEntrenador, String idEquipo);

    /**
     * Establece la clasificación de un equipo.
     * @param idEquipo Identificador del equipo.
     * @param Clasificacion Nueva clasificación del equipo.
     */
    
    void establecerClasificacion(String idEquipo, String Clasificacion);
}