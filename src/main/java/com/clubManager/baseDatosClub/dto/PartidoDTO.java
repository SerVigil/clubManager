package com.clubManager.baseDatosClub.dto;

import java.time.LocalDate;

/**
 * DTO que representa datos de un partido.
 * 
 * @author Sergio Vigil Soto
 */

public class PartidoDTO {
	
	//Area de datos
	
    private Long idPartido;
    private String idEquipo;
    private String local;
    private String visitante;
    private LocalDate fecha;
    private String tipoPartido;
    private String lugar;
    
    //Constructor

    public PartidoDTO(Long idPartido, String idEquipo, String local, String visitante,
    		LocalDate fecha, String tipoPartido, String lugar) 
    {
		this.idPartido = idPartido;
		this.idEquipo = idEquipo;
		this.local = local;
		this.visitante = visitante;
		this.fecha = fecha;
		this.tipoPartido = tipoPartido;
		this.lugar = lugar;
	}

	//Métodos Getters y setters
    
    public Long getIdPartido() 
    {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) 
    {
        this.idPartido = idPartido;
    }

    public String getIdEquipo() 
    {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) 
    {
        this.idEquipo = idEquipo;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getTipoPartido() 
    {
        return tipoPartido;
    }

    public void setTipoPartido(String tipoPartido) 
    {
        this.tipoPartido = tipoPartido;
    }

    public String getLugar() 
    {
        return lugar;
    }

    public void setLugar(String lugar) 
    {
        this.lugar = lugar;
    }

	public String getLocal() 
	{
		return local;
	}

	public void setLocal(String local) 
	{
		this.local = local;
	}

	public String getVisitante() 
	{
		return visitante;
	}

	public void setVisitante(String visitante) 
	{
		this.visitante = visitante;
	}    
}