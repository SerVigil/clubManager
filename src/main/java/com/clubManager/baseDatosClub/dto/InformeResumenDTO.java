package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa un resumen de un informe
 * 
 * @author Sergio Vigil Soto
 */

public class InformeResumenDTO {
	
	//Area de datos
	
    private Long idInforme;
    private String fecha;
    private String tipo;
    private String nombre;
    
    //Constructores
    
    public InformeResumenDTO() {}
    
    public InformeResumenDTO(Long idInforme, String fecha, String tipo, String nombre) 
    {
        this.idInforme = idInforme;
        this.fecha = fecha;
        this.tipo = tipo;
        this.nombre = nombre;
    }
    
    // Métodos Getters y setters
    
	public Long getIdInforme() 
	{
		return idInforme;
	}
	
	public void setIdInforme(Long idInforme) 
	{
		this.idInforme = idInforme;
	}
	
	public String getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}
	
	public String getTipo() 
	{
		return tipo;
	}
	
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
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