package com.clubManager.baseDatosClub.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.EquipoPartido;
import com.clubManager.baseDatosClub.entidades.EquipoPartidoPK;

import jakarta.transaction.Transactional;

/**
 * Repositorio JPA para la entidad {@link EquipoPartido}.
 * 
 * Esta interfaz permite realizar operaciones CRUD sobre la tabla relacionada con la entidad
 * {@code EquipoPartido}, cuya clave primaria está compuesta por {@link EquipoPartidoPK}.
 * 
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface EquipoPartidoRepositorio extends JpaRepository<EquipoPartido, EquipoPartidoPK> {
	
	/**
	 * Devuelve todos los partidos en los que ha participado un equipo.
	 *
	 * @param idEquipo Identificador del equipo.
	 * @return Lista de {@link EquipoPartido}.
	 */
	
	List<EquipoPartido> findByEquipo_IdEquipo(String idEquipo);
	
	/**
     * Borra todas las relaciones equipo-partido de un equipo dado.
     *
     * @param idEquipo identificador único del equipo
     */
	
    @Modifying
    @Transactional
    void deleteByPartido_Equipo_IdEquipo(String idEquipo);

    /**
     * Borra todas las relaciones de un partido dado.
     *
     * @param idPartido identificador único del partido
     */
    
    @Modifying
    @Transactional
    void deleteByPartido_IdPartido(Long idPartido);

    /**
     * Verifica si existen relaciones para un equipo dado.
     *
     * @param idEquipo identificador único del equipo
     * @return true si existen relaciones, false en caso contrario
     */
    
    boolean existsByPartido_Equipo_IdEquipo(String idEquipo);
	


}

