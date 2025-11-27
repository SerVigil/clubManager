package com.clubManager.baseDatosClub.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubManager.baseDatosClub.entidades.Entrenador;

/**
 * Repositorio JPA para la entidad Entrenador.
 * Permite realizar operaciones CRUD y consultas personalizadas
 * sobre los entrenadores registrados en el sistema.
 * 
 * La clave primaria de esta entidad es el campo idEntrenador (tipo String).
 * 
 * @author Sergio Vigil Soto
 */

@Repository
public interface EntrenadorRepositorio extends JpaRepository<Entrenador, String> {
	
	/**
     * Busca un entrenador por su id.
     * 
     * @param id Identificador único del entrenador
     * @return Optional con el entrenador si existe, vacío si no se encuentra
     */
	
	Optional<Entrenador> findById(String idEntrenador);

    /**
     * Busca un entrenador por su DNI.
     * 
     * @param dni Documento Nacional de Identidad del entrenador
     * @return Optional con el entrenador si existe, vacío si no se encuentra
     */
	
    Optional<Entrenador> findByDni(String dni);
    
    /**
     * Verifica si existe un entrenador registrado con el DNI indicado.
     * 
     * @param dni DNI a verificar
     * @return true si existe un entrenador con ese DNI, false en caso contrario
     */
    
    boolean existsByDni(String dni);
}
