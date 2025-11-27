package com.clubManager.baseDatosClub.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Galeria;

/**
 * Repositorio JPA para la entidad {@link Galeria}.
 * 
 * Proporciona métodos CRUD básicos para gestionar las galerías almacenadas en la base de datos.
 * Extiende de {@link JpaRepository} 
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface GaleriaRepositorio extends JpaRepository<Galeria, Long> 
{
	
	
	/**
	 * Obtiene todas las galerías asociadas a un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return lista de galerías vinculadas al equipo
	 */
	
	List<Galeria> findByEquipo_IdEquipo(String idEquipo);

	/**
	 * Elimina todas las galerías asociadas a un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 */
	
	void deleteByEquipo_IdEquipo(String idEquipo);

}