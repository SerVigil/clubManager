package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Jugador;

import jakarta.transaction.Transactional;

/**
 * Repositorio JPA para la entidad Jugador.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * sobre los jugadores registrados en el sistema.
 * 
 * La clave primaria de esta entidad es el id del jugador (tipo String).
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface JugadorRepositorio extends JpaRepository<Jugador, String> {
	
	
	
	/**
	 * Busca un jugador por su identificador único.
	 *
	 * @param idJugador identificador del jugador
	 * @return Optional con el jugador si existe, vacío si no se encuentra
	 */
	
	Optional<Jugador> findById(String idJugador);

	/**
	 * Lista todos los jugadores activos (activo = true) que pertenecen a un equipo concreto.
	 *
	 * @param idEquipo identificador del equipo
	 * @return lista de jugadores activos del equipo
	 */
	
	List<Jugador> findByActivoTrueAndEquipo_idEquipo(String idEquipo);

	/**
	 * Lista todos los jugadores que pertenecen al equipo indicado.
	 *
	 * @param idEquipo identificador del equipo
	 * @return lista de jugadores del equipo
	 */
	
	List<Jugador> findByEquipo_IdEquipo(String idEquipo);

	/**
	 * Realiza la vinculación entre un jugador y un padre en la tabla intermedia Jugador_Padre.
	 * Requiere que el jugador y el padre existan previamente en sus respectivas tablas.
	 *
	 * @param idJugador identificador del jugador
	 * @param idPadre identificador del padre
	 */
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO jugador_padre (id_jugador, id_padre) VALUES (:idJugador, :idPadre)", nativeQuery = true)
	void vincularPadreAJugador(@Param("idJugador") String idJugador, @Param("idPadre") String idPadre);

	/**
	 * Elimina todos los jugadores que pertenecen al equipo indicado.
	 *
	 * @param idEquipo identificador del equipo cuyos jugadores serán eliminados
	 */
	
	@Transactional
	void deleteByEquipo_IdEquipo(String idEquipo);

	/**
	 * Busca un jugador por su id y el id del equipo al que pertenece.
	 *
	 * @param idJugador identificador del jugador
	 * @param idEquipo identificador del equipo
	 * @return Optional con el jugador si existe, vacío si no se encuentra
	 */
	
	Optional<Jugador> findByIdJugadorAndEquipo_IdEquipo(String idJugador, String idEquipo);

	/**
	 * Lista todos los jugadores de un equipo ordenados por sus puntos totales en orden descendente.
	 *
	 * @param idEquipo identificador del equipo
	 * @return lista de jugadores ordenada por puntos totales
	 */
	
	List<Jugador> findByEquipo_IdEquipoOrderByPuntosTotalesDesc(String idEquipo);




}
