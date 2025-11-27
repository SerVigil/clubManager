package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;
import com.clubManager.baseDatosClub.repositorios.ModeloEntrenamientoRepositorio;

import jakarta.transaction.Transactional;

/**
 * Implementación del servicio {@link ModeloEntrenamientoServicio}.
 * 
 * Gestiona la creación, consulta, modificación y eliminación de los distintos
 * modelos de entrenamiento que se pueden crear para un equipo.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class ModeloEntrenamientoServicioImpl implements ModeloEntrenamientoServicio {
	
	//Area de datos
	
	@Autowired
	private ModeloEntrenamientoRepositorio modeloEntrenamientoRepo;
	
	//Métodos principales
	
	/** 
     * {@inheritDoc} 
     */

	@Transactional
	@Override
    public List<ModeloEntrenamiento> listarModelosPorEquipo(String idEquipo) 
	{
        return modeloEntrenamientoRepo.findByEquipo_IdEquipo(idEquipo);
    }

	/** 
     * {@inheritDoc} 
     */
	
	@Override
    public Optional<ModeloEntrenamiento> buscarPorIdYEquipo(String idEquipo, Long idModelo) 
	{
        return modeloEntrenamientoRepo.findByIdModeloAndEquipo_IdEquipo(idModelo, idEquipo);
    }
	
	/** 
     * {@inheritDoc} 
     */

	@Override
	public void crearModelo(Equipo equipo, String nombre, String tipo, Integer duracion, String observaciones) 
	{
	    if (equipo == null || equipo.getIdEquipo() == null) 
	    {
	        throw new IllegalArgumentException("El equipo no puede ser nulo ni carecer de ID");
	    }

	    ModeloEntrenamiento modelo = new ModeloEntrenamiento();
	    modelo.setEquipo(equipo);
	    modelo.setNombre(nombre);
	    modelo.setTipo(tipo);
	    modelo.setDuracion(duracion);
	    modelo.setObservaciones(observaciones);

	    modeloEntrenamientoRepo.save(modelo);
	}

	/** 
     * {@inheritDoc} 
     */

	@Override
    public void modificarModeloPorEquipo(String idEquipo, Long idModelo, String nombre, String tipo, 
    		Integer duracion, String observaciones) 
	{
        Optional<ModeloEntrenamiento> optional = modeloEntrenamientoRepo.findByIdModeloAndEquipo_IdEquipo
        		(idModelo, idEquipo);
        if (optional.isPresent()) 
        {
            ModeloEntrenamiento modelo = optional.get();
            modelo.setNombre(nombre);
            modelo.setTipo(tipo);
            modelo.setDuracion(duracion);
            modelo.setObservaciones(observaciones);
            modeloEntrenamientoRepo.save(modelo);
        } 
        else 
        {
            throw new IllegalArgumentException("Modelo no encontrado para el equipo: " + idEquipo);
        }
    }
	
	/** 
     * {@inheritDoc} 
     */

	@Override
	public void eliminarModeloPorEquipo(String idEquipo, Long idModelo) 
	{
	    Optional<ModeloEntrenamiento> optional = modeloEntrenamientoRepo
	            .findByIdModeloAndEquipo_IdEquipo(idModelo, idEquipo);
	    
	    if (optional.isPresent()) 
	    {
	        ModeloEntrenamiento modelo = optional.get();
	        modeloEntrenamientoRepo.delete(modelo);
	    }
	}
	
	/** 
     * {@inheritDoc} 
     */
	
	@Override
    public void eliminarTodosLosModelosDeEquipo(String idEquipo) 
	{
        List<ModeloEntrenamiento> modelos = (modeloEntrenamientoRepo.findByEquipo_IdEquipo(idEquipo));
        modeloEntrenamientoRepo.deleteAll(modelos);
    }
}