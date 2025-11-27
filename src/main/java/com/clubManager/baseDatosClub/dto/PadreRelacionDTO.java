package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa algunos datos de un padre. 
 * 
 * @author Sergio Vigil Soto
 */

public class PadreRelacionDTO {
	
	//Area de Datos
	
	private String idPadre;
	private String nombre;
	private String apellidos;
	
	//Constructor
	
	public PadreRelacionDTO(String idPadre, String nombre, String apellidos) 
	{
		this.idPadre = idPadre;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	//Métodos Getter y Setter

	public String getIdPadre() 
	{
		return idPadre;
	}

	public void setIdPadre(String idPadre) 
	{
		this.idPadre = idPadre;
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