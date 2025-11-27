package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa algun dato de un jugador
 * 
 * @author Sergio Vigil Soto
 */

public class JugadorRelacionDTO {
	
	//Area de Datos
	
	private String idJugador;
	private String nombre;
	private String apellidos;
	
	//Constructor
	
	public JugadorRelacionDTO(String idJugador, String nombre, String apellidos) 
	{
		this.idJugador = idJugador;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	//Métodos Getter y Setter

	public String getIdJugador() 
	{
		return idJugador;
	}

	public void setIdJugador(String idJugador) 
	{
		this.idJugador = idJugador;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getApellidos() 
	{
		return apellidos;
	}

	public void setApellidos(String apellidos) 
	{
		this.apellidos = apellidos;
	}
}