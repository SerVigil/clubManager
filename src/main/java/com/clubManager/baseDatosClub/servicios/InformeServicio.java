package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.dto.InformeDTO;
import com.clubManager.baseDatosClub.dto.InformeResumenDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Informe;

/**
 * Interfaz de servicio para gestionar los informes generados en el sistema.
 * 
 * Define las operaciones principales para crear, modificar, consultar y eliminar informes.
 * 
 * Solo se podrán modificar o eliminar informes pertenecientes al equipo.
 * 
 * Un informe siempre debe estar emitido por un entrenador y puede estar
 * asociado opcionalmente a un jugador.
 * 
 * @author Sergio Vigil Soto
 */

public interface InformeServicio {
	
	/**
	 * Se busca un informe por su identificador y el identificador del equipo al que pertenece
	 * @param idInforme identificador único del informe
	 * @param idEquipo identificador único del equipo
	 * @return un Optional Informe si existe ese informe en el equipo
	 */
	
	Optional<Informe> buscarPorIdYEquipo(Long idInforme, String idEquipo);

    /**
     * Crea un nuevo informe a partir de los datos proporcionados en un {@link InformeDTO}.
     * 
     * El método valida los datos esenciales del informe (fecha, contenido y tipo),
     * convierte el DTO a una entidad {@link Informe} y la guarda en la base de datos.
     *
     * @param informeDTO objeto que contiene los datos del informe a crear.
     *                   Debe incluir fecha, contenido, tipo, id del entrenador,
     *                   id del jugador (opcional) e id del equipo.
     * @return informe creado y guardado en la base de datos
     */
	
    Informe crearInforme(InformeDTO informeDTO);

    /**
     * Modifica un informe existente perteneciente al equipo.
     * 
     * Este método solo se ejecutará si el informe pertenece al equipo.
     * 
     * @param idInforme identificador del informe a modificar
     * @param fecha nueva fecha del informe
     * @param contenido nuevo contenido del informe
     * @param tipo nuevo tipo de informe
     * @param entrenador entrenador emisor del informe (debe pertenecer al mismo equipo que el informe)
     * @param jugador jugador al que va dirigido el informe (opcional)
     */
    
    void modificarInforme(Long idInforme, LocalDate fecha, String contenido, String tipo,
                          Entrenador entrenador, Jugador jugador);

    /**
     * Elimina un informe existente, siempre que pertenezca al equipo.
     * 
     * @param id identificador del informe a eliminar
     * @param idEquipo identificador del equipo al que pertenece el informe
     */
    
    void eliminarInforme(Long idInforme, String idEquipo);

    /**
     * Elimina todos los informes asociados a un equipo específico.
     * 
     * @param idEquipo identificador único del equipo cuyos informes quieren ser eliminados.
     */
    
    void eliminarInformesPorEquipo(String idEquipo);

    /**
     * Crea un nuevo informe directamente a partir de una entidad {@link Informe}.
     * 
     * @param informe objeto informe a persistir
     * @return informe creado y almacenado
     */
    
    Informe crearInforme(Informe informe);

    /**
     * Lista todos los informes pertenecientes a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de informes asociados a ese equipo
     */
    
    List<InformeResumenDTO> listarInformesPorEquipo(String idEquipo);
}