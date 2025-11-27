package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import com.clubManager.baseDatosClub.entidades.Partido;

import jakarta.transaction.Transactional;


/**
 * Repositorio JPA para la entidad {@link Partido}.
 * 
 * Proporciona métodos CRUD básicos para gestionar partidos.
 * 
 * Extiende {@link JpaRepository} para obtener funcionalidades estándar.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface PartidoRepositorio extends JpaRepository<Partido, Long> {

	/**
	 * Busca un partido por su identificador y el id de uno de los equipos que participan en él.
	 *
	 * @param idPartido identificador único del partido
	 * @param idEquipo identificador único del equipo
	 * @return Optional con el partido si existe, vacío si no se encuentra
	 */
	
	Optional<Partido> findByIdPartidoAndEquipo_IdEquipo(Long idPartido, String idEquipo);
	
	/**
	 * Lista todos los partidos que pertenecen al equipo indicado.
	 *
	 * @param idEquipo identificador del equipo
	 * @return lista de partidos del equipo
	 */
	
	List<Partido> findByEquipo_IdEquipo(String idEquipo);
	
	/**
	 * Borra todos los partidos que pertenecen al equipo indicado.
	 *
	 * @param idEquipo identificador del equipo
	 */
	@Modifying
	@Transactional
	void deleteByEquipo_IdEquipo(String idEquipo);
	
	boolean existsByEquipo_IdEquipo(String idEquipo);

}