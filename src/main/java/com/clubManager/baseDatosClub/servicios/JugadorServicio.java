package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.dto.JugadorRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Jugador;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con los jugadores.
 * 
 * @author Sergio Vigil Soto
 */

public interface JugadorServicio {

    /**
     * Busca un jugador por su identificador único.
     * 
     * @param id identificador del jugador
     * @return Optional con el jugador encontrado (vacío si no existe)
     */
    
    Optional<Jugador> buscarPorId(String idJugador);

    /**
     * Crea un nuevo jugador utilizando parámetros individuales.
     * 
     * @param idJugador identificador del jugador
     * @param nombre nombre del jugador
     * @param apellidos apellidos del jugador
     * @param dni documento nacional de identidad
     * @param direccion dirección del jugador
     * @param tipoUsuario rol o perfil del jugador
     * @param password contraseña de acceso
     * @param telefono número de teléfono
     * @param email dirección de correo electrónico
     * @param posicion posición en el campo
     * @param fechaNacimiento fecha de nacimiento
     */
    
    void crearJugador
    (String idJugador, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
     String password, String telefono, String email, String posicion, LocalDate fechaNacimiento);

    /**
     * Modifica un jugador existente utilizando parámetros individuales.
     * 
     * @param idJugador identificador del jugador a modificar
     * @param nombre nuevo nombre
     * @param apellidos nuevos apellidos
     * @param dni nuevo DNI
     * @param direccion nueva dirección
     * @param tipoUsuario nuevo tipo de usuario
     * @param password nueva contraseña
     * @param foto nueva ruta de la foto
     * @param telefono nuevo teléfono
     * @param email nuevo email
     * @param posicion nueva posición
     * @param fechaNacimiento nueva fecha de nacimiento
     */
    
    void modificarJugador(String idJugador, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                         String password, String foto, String telefono, String email,
                         String posicion, LocalDate fechaNacimiento);

    /**
     * Elimina un jugador solo si pertenece al equipo indicado.
     *
     * @param idJugador ID del jugador.
     * @param idEquipo ID del equipo al que debe pertenecer.
     * @return true si se elimina correctamente, false si no pertenece o no existe.
     */
    
    boolean eliminarJugador(String idJugador, String idEquipo);

    /**
     * Crea un nuevo jugador a partir de un objeto.
     * 
     * @param jugador objeto Jugador a crear
     * @return jugador creado
     */
    
    Jugador crearJugador(Jugador jugador);

    /**
     * Actualiza un jugador existente a partir de un objeto completo.
     * 
     * @param jugador objeto Jugador con los datos actualizados
     * @return jugador modificado
     */
    
    Jugador actualizarJugador(Jugador jugador);

    /**
     * Elimina un jugador utilizando su ID.
     * 
     * @param idJugador identificador del jugador a eliminar
     */
    
    void eliminarJugadorPorId(String idJugador);

    /**
     * Obtiene un jugador por su ID.
     * 
     * @param idJugador identificador del jugador
     * @return Optional con el jugador si existe
     */
    
    Optional<Jugador> obtenerJugadorPorId(String idJugador);

    /**
     * Lista solo los jugadores que se encuentran activos y que pertenezcan a un equipo.
     * 
     * @return lista de jugadores activos de un equipo dado.
     */
    
    public List<Jugador> listarJugadoresActivosPorEquipo(String idEquipo);

    /**
     * Cambia el estado activo/inactivo de un jugador.
     * 
     * @param idJugador identificador del jugador
     * @param activo nuevo estado de activación
     * @return jugador actualizado
     */
    
    Jugador cambiarEstadoActivo(String idJugador, boolean activo);

    /**
     * Incrementa la cantidad de tarjetas rojas de un jugador.
     * 
     * @param idJugador identificador del jugador
     * @param tarjetasRojas cantidad de tarjetas rojas a sumar
     * @return jugador actualizado
     */
    
    Jugador incrementarRojas(String idJugador, Integer tarjetasRojas);
    
    /**
     * Incrementa la cantidad de tarjetas amarillas de un jugador.
     * 
     * @param idJugador identificador del jugador
     * @param tarjetasAmarillas cantidad de tarjetas amarillas a sumar
     * @return jugador actualizado
     */
    
    Jugador incrementarAmarillas(String idJugador, Integer tarjetasAmarillas);
    
    /**
     * Incrementa la cantidad de goles recibidos por un portero.
     * 
     * @param idJugador identificador del jugador
     * @param goles cantidad de goles a sumar
     * @return jugador actualizado
     */
    
    Jugador incrementarGolesEncajados(String idJugador, Integer golesEncajados);
    
    /**
     * Incrementa la cantidad de goles anotados por un jugador.
     * 
     * @param idJugador identificador del jugador
     * @param goles cantidad de goles a sumar
     * @return jugador actualizado
     */
    
    Jugador incrementarGoles(String idJugador, Integer goles);
    
    /**
     * Vincula un padre existente a un jugador, añadiendo la relación en la tabla intermedia.
     * 
     * @param idJugador identificador del jugador
     * @param idPadre identificador del padre
     * @return jugador actualizado con el padre vinculado
     */
    
    Jugador vincularPadreAJugador(String idJugador, String idPadre);
    
    /**
     * Lista todos los jugadores que pertenecen al equipo indicado.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de jugadores en formato DTO
     */
    
    List<JugadorRelacionDTO> listarJugadoresPorEquipo(String idEquipo);
    
    /**
     * Elimina todos los jugadores que pertenecen al equipo indicado.
     * 
     * Si el equipo no existe, lanza una excepción.
     * 
     * @param idEquipo identificador del equipo
     */
    
    void eliminarJugadoresPorEquipo(String idEquipo);
    
    /**
     * Obtiene el ranking de jugadores de un equipo ordenados por puntos totales (de mayor a menor).
     *
     * @param idEquipo ID del equipo
     * @return lista de jugadores con sus puntos totales
     */
    
    List<JugadorPuntuacionDTO> obtenerRankingPorEquipo(String idEquipo);
    
    /**
     * Reactiva un jugador previamente inactivo y elimina su estado actual.
     *
     * @param idJugador Identificador del jugador a reactivar.
     */
    
    void reactivarJugadorYEliminarEstado(String idJugador);

    /**
     * Obtiene un jugador que pertenece a un equipo específico.
     *
     * @param idJugador Identificador del jugador.
     * @param idEquipo  Identificador del equipo.
     * @return Un {@link Optional} que contiene el jugador si existe en el equipo, o vacío si no.
     */
    
    public Optional<Jugador> obtenerJugadorPorEquipo(String idJugador, String idEquipo);

    /**
     * Incrementa los puntos totales de un jugador.
     *
     * @param idJugador Identificador del jugador.
     * @param puntos    Cantidad de puntos a incrementar.
     * @return El {@link Jugador} actualizado con los nuevos puntos.
     */
    
    Jugador incrementarPuntos(String idJugador, Integer puntos);
}