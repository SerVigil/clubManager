package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;

/**
 * Repositorio JPA para la entidad {@link ModeloEntrenamiento}.
 * 
 * Proporciona métodos CRUD estándar para gestionar los modelos predefinidos de entrenamiento.
 * 
 * Extiende de {@link JpaRepository}
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface ModeloEntrenamientoRepositorio extends JpaRepository<ModeloEntrenamiento, Long> {
	
	/**
	 * Busca un modelo de entrenamiento por su identificador único.
	 *
	 * @param idModelo identificador del modelo de entrenamiento
	 * @return Optional con el modelo si existe, vacío si no se encuentra
	 */
	
	Optional<ModeloEntrenamiento> findByIdModelo(Long idModelo);

	/**
	 * Obtiene todos los modelos de entrenamiento asociados a un equipo concreto.
	 *
	 * @param idEquipo identificador único del equipo
	 * @return lista de modelos de entrenamiento vinculados al equipo
	 */
	
	List<ModeloEntrenamiento> findByEquipo_IdEquipo(String idEquipo);

	/**
	 * Busca un modelo de entrenamiento por su id y el id del equipo al que pertenece.
	 *
	 * @param idModelo identificador del modelo de entrenamiento
	 * @param idEquipo identificador del equipo
	 * @return Optional con el modelo si existe, vacío si no se encuentra
	 */
	
	Optional<ModeloEntrenamiento> findByIdModeloAndEquipo_IdEquipo(Long idModelo, String idEquipo);
}
