package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Lesion;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con las lesiones de los jugadores.
 * @author Sergio Vigil Soto
 */

public interface LesionServicio {

    /**
     * Busca una lesion por su identificador único.
     * 
     * @param idLesion el ID de la lesión
     * @return un {@link Optional} con la lesión encontrada, o vacío si no existe
     */
    
    Optional<Lesion> buscarPorId(Long idLesion);
    
    /**
     * Crea una lesión a través de un objeto.
     * @param lesion Objeto para guardar la lesión.
     */
    
    void crearLesion(Lesion lesion);

    /**
     * Crea una nueva lesión asociada a un jugador.
     * 
     * @param tipo el tipo de lesión (esguince, fractura, etc.)
     * @param fechaInicio la fecha de inicio de la lesión
     * @param fechaFin la fecha de recuperación (puede ser null)
     * @param descripcion una descripción detallada de la lesión
     * @param idJugador el ID del jugador al que se asocia la lesión
     */
    
    void crearLesion(String tipo, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, String idJugador);

    /**
     * Modifica los datos de una lesión existente.
     * 
     * @param idLesion el ID de la lesión a modificar
     * @param tipo el nuevo tipo de lesión
     * @param fechaInicio la nueva fecha de inicio
     * @param fechaFin la nueva fecha de fin
     * @param descripcion la nueva descripción
     */
    
    void modificarLesion(Long idLesion, String tipo, LocalDate fechaInicio, LocalDate fechaFin, String descripcion);

    /**
     * Elimina una lesión según su ID.
     * 
     * @param idLesion el ID de la lesión a eliminar
     */
    
    void eliminarLesion(Long idLesion);

    /**
     * Elimina todas las lesiones
     */
    
    void eliminarLesiones();

    /**
     * Lista las lesiones activas (jugadores actualmente lesionados)
     * pertenecientes a un equipo específico.
     *
     * @param idEquipo el identificador del equipo
     * @return una lista de objetos {@link Lesion} con los jugadores lesionados del equipo
     */
    
    List<Jugador> listarLesionadosPorEquipo(String idEquipo);

    /**
     * Marca al jugador como lesionado, crea el registro en la tabla de lesiones
     * y cambia su estado activo a false.
     *
     * @param idJugador el ID del jugador
     */
    
    void registrarLesionYDesactivarJugador(String idJugador);

    /**
     * Elimina la lesión activa de un jugador (cuando se recupera)
     * y vuelve a marcarlo como activo.
     *
     * @param idJugador el ID del jugador recuperado
     */
    
    void eliminarLesionYReactivarJugador(String idJugador);
}