package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Notificacion;

/**
 * Repositorio JPA para la entidad Notificacion.
 * 
 * La clave primaria de esta entidad es de tipo Long.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {
	
	/**
	 * Obtiene todas las notificaciones asociadas a un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return lista de notificaciones vinculadas al equipo
	 */
	
	List<Notificacion> findByEquipo_IdEquipo(String idEquipo);

	/**
	 * Busca la notificación más reciente de un equipo, ordenada por fecha descendente.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return Optional con la última notificación del equipo, vacío si no existen notificaciones
	 */
	
	Optional<Notificacion> findTopByEquipo_IdEquipoOrderByFechaDesc(String idEquipo);





   
}
