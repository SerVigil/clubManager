package com.clubManager.baseDatosClub.dto;

import java.time.LocalDate;

/**
 * DTO que representa algunos de los datos de las notificaciones.
 * 
 * @author Sergio Vigil Soto
 */

public class NotificacionDTO {
	
	//Area de Datos
	
	private Long idNotificacion;
    private String titulo;
    private String mensaje;
    private LocalDate fecha;
    private String idEquipo;
    
    //Constructores
    
    public NotificacionDTO() {}
    
	public NotificacionDTO(Long idNotificacion, String titulo, String mensaje, LocalDate fecha, String idEquipo) 
	{
		this.idNotificacion = idNotificacion;
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.idEquipo = idEquipo;
	}
	
	//Métodos Getters y Setters

	public Long getIdNotificacion() 
	{
		return idNotificacion;
	}

	public void setIdNotificacion(Long idNotificacion) 
	{
		this.idNotificacion = idNotificacion;
	}

	public String getTitulo() 
	{
		return titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public String getMensaje() 
	{
		return mensaje;
	}

	public void setMensaje(String mensaje) 
	{
		this.mensaje = mensaje;
	}

	public LocalDate getFecha() 
	{
		return fecha;
	}

	public void setFecha(LocalDate fecha) 
	{
		this.fecha = fecha;
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