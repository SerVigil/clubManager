package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Sancion;

/**
 * Repositorio JPA para la entidad Sancion.
 * Proporciona métodos CRUD y consultas básicas para gestionar sanciones.
 * La clave primaria de Sancion es de tipo Long.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface SancionRepositorio extends JpaRepository<Sancion, Long> {
	
	/**
	 * Obtiene todas las sanciones asociadas a los jugadores de un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return lista de sanciones de los jugadores del equipo
	 */
	
	List<Sancion> findByJugador_Equipo_IdEquipo(String idEquipo);

	/**
	 * Comprueba si existe alguna sanción registrada para un jugador específico.
	 *
	 * @param idJugador identificador único del jugador
	 * @return true si existe al menos una sanción, false en caso contrario
	 */
	
	boolean existsByJugador_IdJugador(String idJugador);

	/**
	 * Busca la primera sanción registrada para un jugador concreto.
	 *
	 * @param idJugador identificador único del jugador
	 * @return Optional con la primera sanción encontrada, vacío si no existen sanciones
	 */
	
	Optional<Sancion> findFirstByJugador_IdJugador(String idJugador);

	/**
	 * Elimina todas las sanciones asociadas a un jugador específico.
	 *
	 * @param idJugador identificador único del jugador
	 */
	
	void deleteByJugador_IdJugador(String idJugador);
}