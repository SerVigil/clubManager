package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa algunos de los datos de un partido.
 * 
 * @author Sergio Vigil Soto
 */

public class PartidoResumenDTO {
	
	//Area de Datos
	
	private Long idPartido;
	private String fecha;
	private	String tipoPartido;
	private String local;
	private String visitante;
	private String lugar;
	
	//Constructor
	
	public PartidoResumenDTO(Long idPartido, String fecha, String tipoPartido, String local,
			String visitante, String lugar) 
	{
		this.idPartido = idPartido;
		this.fecha = fecha;
		this.tipoPartido = tipoPartido;
		this.local = local;
		this.visitante = visitante;
		this.lugar = lugar;
	}

	//Métodos Getter y Setter
	
	public Long getIdPartido() 
	{
		return idPartido;
	}

	public void setIdPartido(Long idPartido) 
	{
		this.idPartido = idPartido;
	}

	public String getFechaPartido() {
		return fecha;
	}

	public void setFechaPartido(String fecha) 
	{
		this.fecha = fecha;
	}

	public String getTipoPartido() {
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