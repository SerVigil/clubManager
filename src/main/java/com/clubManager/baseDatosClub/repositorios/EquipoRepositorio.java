package com.clubManager.baseDatosClub.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;

/**
 * Repositorio JPA para la entidad {@link Equipo}.
 * 
 * Proporciona métodos CRUD básicos para gestionar los equipos almacenados en la base de datos.
 * Extiende de {@link JpaRepository} 
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface EquipoRepositorio extends JpaRepository<Equipo, String> {
	
	 /**
     * Obtiene la lista de jugadores asociados a un equipo concreto.
     *
     * @param idEquipo identificador único del equipo
     * @return lista de jugadores pertenecientes al equipo
     */
	
    @Query("SELECT e.jugadores FROM Equipo e WHERE e.idEquipo = :idEquipo")
    List<Jugador> findJugadoresByIdEquipo(@Param("idEquipo") String idEquipo);
    
    /**
     * Busca todos los equipos vinculados a un padre específico.
     *
     * @param idPadre identificador único del padre
     * @return lista de equipos asociados al padre
     */
    
    @Query("SELECT e FROM Equipo e JOIN e.padres p WHERE p.idPadre = :idPadre")
    List<Equipo> findEquiposByIdPadre(@Param("idPadre") String idPadre);
    
    /**
     * Actualiza la URL de clasificación de un equipo.
     *
     * @param idEquipo identificador único del equipo
     * @param url nueva URL de clasificación
     */
    
    @Modifying
    @Query("UPDATE Equipo e SET e.clasificacion = :url WHERE e.idEquipo = :idEquipo")
    void actualizarClasificacion(@Param("idEquipo") String idEquipo, @Param("url") String url);
}