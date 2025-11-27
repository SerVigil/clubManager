package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Lesion;

/**
 * Repositorio JPA para la entidad Lesión.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * sobre las lesiones padecidas por los jugadores.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface LesionRepositorio extends JpaRepository<Lesion, Long> {
	
	
	/**
	 * Obtiene todas las lesiones asociadas a los jugadores de un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return lista de lesiones de los jugadores del equipo
	 */
	
	List<Lesion> findByJugador_Equipo_IdEquipo(String idEquipo);

	/**
	 * Comprueba si existe alguna lesión registrada para un jugador específico.
	 *
	 * @param idJugador identificador único del jugador
	 * @return true si existe al menos una lesión, false en caso contrario
	 */
	
	boolean existsByJugador_IdJugador(String idJugador);

	/**
	 * Elimina todas las lesiones asociadas a un jugador específico.
	 *
	 * @param idJugador identificador único del jugador
	 */
	
	void deleteByJugador_IdJugador(String idJugador);
	
	Optional <Lesion> findFirstByJugador_IdJugador(String idJugador);


}