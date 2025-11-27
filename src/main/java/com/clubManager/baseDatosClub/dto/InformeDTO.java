package com.clubManager.baseDatosClub.dto;

import java.time.LocalDate;

/**
 * DTO para crear o modificar un informe.
 * 
 *  @author Sergio Vigil Soto
 */

public class InformeDTO {
	
	//Area de datos
	
	private Long idInforme;
    private LocalDate fecha;
    private String contenido;
    private String tipo;
    private String idEntrenador;
    private String idJugador;
    private String idEquipo;
    
    //Constructor

    public InformeDTO(Long idInforme,  LocalDate fecha, String contenido, String tipo,
			 String idEntrenador, String idJugador, String idEquipo) 
    {
    	this.idInforme = idInforme;
		this.fecha = fecha;
		this.contenido = contenido;
		this.tipo = tipo;
		this.idEntrenador = idEntrenador;
		this.idJugador = idJugador;
		this.idEquipo = idEquipo;
	}
    
    //Métodos Getters y setters
    
    public Long getIdInforme() 
	{
		return idInforme;
	}

	public void setIdInforme(Long idInforme) 
	{
		this.idInforme = idInforme;
	} 
    
    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getContenido() 
    {
        return contenido;
    }

    public void setContenido(String contenido) 
    {
        this.contenido = contenido;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public String getIdEntrenador() 
    {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) 
    {
        this.idEntrenador = idEntrenador;
    }

    public String getIdJugador() 
    {
        return idJugador;
    }

    public void setIdJugador(String idJugador) 
    {
        this.idJugador = idJugador;
    }

	public String getIdEquipo() 
	{
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) 
	{
		this.idEquipo = idEquipo;
	} 
}