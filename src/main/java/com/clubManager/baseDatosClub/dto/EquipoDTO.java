package com.clubManager.baseDatosClub.dto;

import com.clubManager.baseDatosClub.entidades.Equipo;

/**
 * DTO que representa algunos datos de un equipo.
 * 
 *  @author Sergio Vigil Soto
 */

public class EquipoDTO {
	
	//Area de datos
	
    private String nombreEquipo;
    private String escudoEquipo;
    
    //Constructor

    public EquipoDTO(Equipo equipo) 
    	{
        this.nombreEquipo = equipo.getNombreEquipo();
        this.escudoEquipo = equipo.getEscudoEquipo(); 
    	}
    
    //Métodos getter y setter

    public String getNombreEquipo() 
    	{ 
    	return nombreEquipo; 
    	}
    
    public String getEscudoEquipo() 
    	{ 
    	return escudoEquipo; 
    	}
}