package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Padre;

/**
 * Repositorio JPA para la entidad Padre.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * sobre los padres o tutores registrados en el sistema.
 * 
 * La clave primaria de esta entidad es el id del padre (tipo String).
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface PadreRepositorio extends JpaRepository<Padre, String> {
	
    /**
     * Busca un padre por su identificador único.
     * 
     * @param idPadre identificador del padre
     * @return un Optional que puede contener el padre correspondiente al identificador
     */
	
	Optional<Padre> findById(String idPadre);

    /**
     * Busca un padre por su DNI.
     * 
     * @param dni DNI del padre
     * @return un Optional que puede contener el padre correspondiente al DNI
     */
	
    Optional<Padre> findByDni(String dni);
    
    /**
     * Busca todos los padres vinculados al equipo indicado, usando la tabla intermedia 'equipo_padre'.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de padres asociados al equipo
     */
    
    @Query("SELECT p FROM Padre p JOIN p.equipos e WHERE e.idEquipo = :idEquipo")
    List<Padre> buscarPadresPorEquipo(@Param("idEquipo") String idEquipo);


}

