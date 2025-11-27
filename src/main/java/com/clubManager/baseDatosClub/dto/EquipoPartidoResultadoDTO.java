package com.clubManager.baseDatosClub.dto;

/**
 * DTO utilizado para asignar un resultado del equipo en un partido.
 * 
 *  @author Sergio Vigil Soto
 */

public class EquipoPartidoResultadoDTO {
	
	//Area de Datos
	
	private Long idPartido;
    private String local;
    private String visitante;
    private String resultado;
    
    //Constructor
    
	public EquipoPartidoResultadoDTO(Long idPartido, String local, String visitante, String resultado) 
	{
		this.idPartido = idPartido;
		this.local = local;
		this.visitante = visitante;
		this.resultado = resultado;
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

	public String getResultado() 
	{
		return resultado;
	}

	public void setResultado(String resultado) 
	{
		this.resultado = resultado;
	}
}