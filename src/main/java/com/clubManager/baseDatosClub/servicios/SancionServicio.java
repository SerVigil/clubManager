package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Sancion;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con las sanciones.
 * 
 * @author Sergio Vigil Soto
 */

public interface SancionServicio {

    /**
     * Busca una sanción por su identificador único.
     * 
     * @param id el id de la sanción que queremos buscar
     * @return un {@link Optional} con la sanción encontrada, o vacío si no existe
     */
    
    Sancion buscarSancionId(Long id);

    /**
     * Crea una nueva sanción.
     * 
     * @param fecha fecha en la que se impone la sanción
     * @param tipoSancion tipo de la sanción
     * @param descripcion descripción o motivo de la sanción
     * @param duracion duración de la sanción
     * @param jugadorId identificador del jugador al que se le aplica la sanción
     */
    
    Sancion crearSancion(LocalDate fecha, String tipoSancion, String descripcion, String duracion, String jugadorId);

    /**
     * Crea una nueva sanción a partir de un objeto {@link Sancion}.
     * 
     * @param sancion el objeto Sancion a registrar
     */
    
    Sancion crearSancion(Sancion sancion);

    /**
     * Modifica los datos de una sanción.
     * 
     * @param idSancion el id de la sanción que se desea modificar
     * @param fecha nueva fecha en la que se impone la sanción
     * @param tipoSancion nuevo tipo de la sanción
     * @param descripcion nueva descripción o motivo de la sanción
     * @param duracion nueva duración de la sanción
     * @param jugadorId nuevo identificador del jugador sancionado
     * @return la sanción modificada
     */
    
    Sancion modificarSancion(Long idSancion, LocalDate fecha, String tipoSancion, String descripcion, 
    		String duracion, String jugadorId);

    /**
     * Modifica una sanción a partir de un objeto {@link Sancion}.
     * 
     * @param sancion el objeto Sancion a modificar
     * @return la sanción modificada
     */
    
    Sancion modificarSancion(Sancion sancion);

    /**
     * Elimina una sanción por su ID.
     * 
     * @param id el ID de la sanción a eliminar
     */
    
    void eliminarSancion(Long id);
    
    /**
     * Lista las sanciones activas (jugadores actualmente sancionados)
     * pertenecientes a un equipo específico.
     *
     * @param idEquipo el identificador del equipo
     * @return una lista de objetos {@link Sancion} con los jugadores sancionados del equipo
     */
    
    List<Jugador> listarSancionadosPorEquipo(String idEquipo);

    /**
     * Marca al jugador como sancionado, crea el registro en la tabla de sanciones
     * y cambia su estado activo a false.
     *
     * @param idJugador el ID del jugador
     */
    
    void registrarSancionYDesactivarJugador(String idJugador);

    /**
     * Elimina la sancion activa de un jugador
     * y vuelve a marcarlo como activo.
     *
     * @param idJugador el ID del jugador
     */
    
    void eliminarSancionYReactivarJugador(String idJugador);
}