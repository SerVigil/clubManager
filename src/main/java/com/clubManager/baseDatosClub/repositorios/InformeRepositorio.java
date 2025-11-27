package com.clubManager.baseDatosClub.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Informe;

import jakarta.transaction.Transactional;

/**
 * Repositorio JPA para la entidad {@link Informe}.
 * 
 * Proporciona métodos CRUD básicos para gestionar los informes almacenados en la base de datos.
 * Extiende de {@link JpaRepository}.
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface InformeRepositorio extends JpaRepository<Informe, Long>{
	
	Optional<Informe> findByIdInformeAndEquipo_IdEquipo(Long idInforme, String idEquipo);

	
	/**
     * Elimina todos los informes asociados a un equipo por su ID.
     * 
     * @param idEquipo ID del equipo cuyos informes serán eliminados.
     */
	
    @Modifying
    @Transactional
    @Query("DELETE FROM Informe i WHERE i.equipo.id = :idEquipo")
    void eliminarTodosPorIdEquipo(String idEquipo);
    
    /**
     * Lista todos los informes asociado a un equipo por su ID.
     * @param idEquipo ID del equipo cuyos informes vamos a listar
     * @return lista de Informes asociados al equipo
     */
    
    List<Informe> findByEquipo_IdEquipo(String idEquipo);

}