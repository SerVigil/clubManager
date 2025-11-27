package com.clubManager.baseDatosClub.dto;

import com.clubManager.baseDatosClub.entidades.Partido;

/**
 * DTO que representa algunos de los datos de un equipo_partido.
 * 
 *  @author Sergio Vigil Soto
 */

public class EquipoPartidoDTO {
	
	//Area de Datos
	
	private String idEquipo;
    private Long idPartido;
    private Partido partido;
    
    //Constructor
    
	public EquipoPartidoDTO(String idEquipo, Long idPartido, Partido partido) 
	{
		this.idEquipo = idEquipo;
		this.idPartido = idPartido;
		this.partido = partido;
	}
	
	//Métodos Getter y Setter

	public String getIdEquipo() 
	{
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) 
	{
		this.idEquipo = idEquipo;
	}

	public Long getIdPartido() 
	{
		return idPartido;
	}

	public void setIdPartido(Long idPartido) 
	{
		this.idPartido = idPartido;
	}

	public Partido getPartido() 
	{
		return partido;
	}

	public void setPartido(Partido partido) 
	{
		this.partido = partido;
	}
}