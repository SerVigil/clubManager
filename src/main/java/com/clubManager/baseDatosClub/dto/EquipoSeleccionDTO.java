package com.clubManager.baseDatosClub.dto;

import com.clubManager.baseDatosClub.entidades.Equipo;

/**
 * DTO usado para representar de forma simplificada un equipo
 * con su identificador y nombre.
 * 
 *  @author Sergio Vigil Soto
 */

public class EquipoSeleccionDTO {
	
	//Area de datos
	
    private String idEquipo;
    private String nombreEquipo;
    
    //Constructor
    
    public EquipoSeleccionDTO() {}
    
    public EquipoSeleccionDTO(String idEquipo, String nombreEquipo)
    {
    	this.idEquipo = idEquipo;
    	this.nombreEquipo = nombreEquipo;
    }

    public EquipoSeleccionDTO(Equipo equipo) 
    {
        this.idEquipo = equipo.getIdEquipo();
        this.nombreEquipo = equipo.getNombreEquipo();
    }
    
    //Métodos getter y setter

    public String getIdEquipo() 
    {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) 
    {
		this.idEquipo = idEquipo;
	}
    
    public String getNombreEquipo() 
	{
        return nombreEquipo;
    }

	public void setNombreEquipo(String nombreEquipo) 
	{
		this.nombreEquipo = nombreEquipo;
	}	
}