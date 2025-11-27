package com.clubManager.baseDatosClub.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO que representa los datos de un entrenamiento de un equipo,
 * incluyendo fecha, hora, tipo, observaciones y referencias a equipo y modelo.
 * 
 *  @author Sergio Vigil Soto
 */

public class EntrenamientoDTO {
	
	//Area de Datos
	
	private Long idEntrenamiento;
	private LocalDate fecha;
	private LocalTime hora;
	private String tipo;
	private String observaciones;
	private String idEquipo;
	private Long idModelo;
	private String nombre;

	
	//Constructores
	
	public EntrenamientoDTO() {
	}

	public EntrenamientoDTO(Long idEntrenamiento, LocalDate fecha, LocalTime hora, String tipo, 
			String observaciones,String idEquipo, Long idModelo, String nombre) 
	{
		this.idEntrenamiento = idEntrenamiento;
		this.fecha = fecha;
		this.hora = hora;
		this.tipo = tipo;
		this.observaciones = observaciones;
		this.idEquipo = idEquipo;
		this.idModelo = idModelo;
		this.nombre = nombre;
	}
	
	//Métodos Getter y Setter

	public Long getIdEntrenamiento() 
	{
		return idEntrenamiento;
	}

	public void setIdEntrenamiento(Long idEntrenamiento) 
	{
		this.idEntrenamiento = idEntrenamiento;
	}

	public LocalDate getFecha() 
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha) 
	{
		this.fecha = fecha;
	}

	public LocalTime getHora() 
	{
		return hora;
	}

	public void setHora(LocalTime hora) 
	{
		this.hora = hora;
	}

	public String getTipo() 
	{
		return tipo;
	}

	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}

	public String getObservaciones() 
	{
		return observaciones;
	}

	public void setObservaciones(String observaciones) 
	{
		this.observaciones = observaciones;
	}

	public String getIdEquipo() 
	{
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) 
	{
		this.idEquipo = idEquipo;
	}

	public Long getIdModelo() 
	{
		return idModelo;
	}

	public void setIdModelo(Long idModelo) 
	{
		this.idModelo = idModelo;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}	
}