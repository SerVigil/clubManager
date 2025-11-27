package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Entrenamiento;

/**
 * Repositorio JPA para la entidad {@link Entrenamiento}.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * sobre los entrenamientos registrados en el sistema.
 * 
 * La clave primaria de esta entidad es {@code idEntrenamiento} (tipo Long).
 * 
 * Incluye métodos de búsqueda por fecha, tipo de entrenamiento y por equipo asociado,
 * así como eliminación de entrenamientos por equipo o por id específico.
 * 
 * @author Sergio Vigil
 */

@Repository
public interface EntrenamientoRepositorio extends JpaRepository<Entrenamiento, Long> {

    /**
     * Busca todos los entrenamientos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de entrenamientos vinculados a ese equipo
     */
	
    List<Entrenamiento> findByEquipo_IdEquipo(String idEquipo);

    /**
     * Elimina todos los entrenamientos asociados a un equipo específico.
     *
     * @param idEquipo identificador del equipo cuyos entrenamientos se eliminarán
     */
    
    void deleteByEquipo_IdEquipo(String idEquipo);

    /**
     * Busca un entrenamiento por su id y el id del equipo al que pertenece.
     * 
     * @param idEntrenamiento identificador del entrenamiento
     * @param idEquipo identificador del equipo
     * @return Optional con el entrenamiento si existe, o vacío si no
     */
    
    Optional<Entrenamiento> findByIdEntrenamientoAndEquipo_IdEquipo(Long idEntrenamiento, String idEquipo);

    /**
     * Elimina un entrenamiento por su id y el id del equipo al que pertenece.
     * 
     * @param idEntrenamiento identificador del entrenamiento a eliminar
     * @param idEquipo identificador del equipo asociado
     */
    
    void deleteByIdEntrenamientoAndEquipo_IdEquipo(Long idEntrenamiento, String idEquipo);

    /**
     * Comprueba si existe un entrenamiento por su id y el id del equipo.
     * 
     * @param idEntrenamiento identificador del entrenamiento
     * @param idEquipo identificador del equipo
     * @return true si existe, false en caso contrario
     */
    
    boolean existsByIdEntrenamientoAndEquipo_IdEquipo(Long idEntrenamiento, String idEquipo);
}