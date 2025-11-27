package com.clubManager.baseDatosClub.servicios;

import java.util.Optional;

import com.clubManager.baseDatosClub.dto.PartidoDTO;
import com.clubManager.baseDatosClub.entidades.Partido;

/**
 * Interfaz de servicio para gestionar las operaciones relacionadas con los partidos.
 *
 *@author Sergio Vigil Soto
 */

public interface PartidoServicio {
	
	
	/**
	 * Busca un partido del equipo por su identificador único.
	 * 
	 * @param id el id del partido que queremos buscar
	 * @param id del equipo al que pertenece el partido
	 * @return un {@link Optional} con el partido encontrado, o vacío si no existe
	 */
	
	Optional<Partido> buscarPorIdPartidoYEquipos_IdEquipo(Long idPartido, String idEquipo);
	
	/**
     * Modifica un partido a partir de un objeto {@link Partido}.
     * 
     * @param dto el objeto Partido a modificar
	 * @return 
     */
	
	Partido modificarPartido(PartidoDTO dto);
	
	/**
     * Elimina un partido por su ID.
     * 
     * @param id el ID del partido a eliminar
     */
	
    void eliminarPartido(Long id);
    
    /**
     * Elimina todos los partido del sistema.
     * @param idEquipo identificador del equipo del que se quieren eliminar los partidos
     */
    
    void eliminarPartidosPorEquipo(String idEquipo);
    
    /**
     * Crea un partido a partir de los datos del DTO y genera automáticamente
     * la relación en EquipoPartido usando el id del equipo recibido.
     * 
     * @param dto Datos del partido enviados desde Android
     * @return PartidoDTO con el id del partido y el id del equipo
     */
    
    public PartidoDTO crearPartido(PartidoDTO dto);
    
}


