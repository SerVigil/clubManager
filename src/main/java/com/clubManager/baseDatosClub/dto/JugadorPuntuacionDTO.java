package com.clubManager.baseDatosClub.dto;

/**
 * DTO utilizado para asignar la puntuación a un jugador
 * 
 * @author Sergio Vigil Soto
 */

public class JugadorPuntuacionDTO {
	
	//Area de datos

    private String idJugador;
    private String nombre;
    private Integer puntuacion;
    private Integer puntosTotales;
    
    //Constructores

    public JugadorPuntuacionDTO() {}

    public JugadorPuntuacionDTO(String idJugador, String nombre, Integer puntosTotales, Integer puntuacion) 
    {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.puntosTotales = puntosTotales;
        this.puntuacion = puntuacion;
    }

    // Métodos Getters y setters
    
    public String getIdJugador() 
    { 
    	return idJugador; 
    }
    public void setIdJugador(String idJugador) 
    {
    	this.idJugador = idJugador; 
    }

    public String getNombre() 
    { 
    	return nombre;
    }
    
    public void setNombre(String nombre)
    { 
    	this.nombre = nombre;
    }

    public Integer getPuntuacion() 
    { 
    	return puntuacion;
    }
    
    public void setPuntuacion(Integer puntuacion)
    { 
    	this.puntuacion = puntuacion;
    }
    
    public Integer getPuntosTotales()
    {
    	return puntosTotales;
    }
    
    public void setPuntosTotales(Integer puntosTotales)
    {
    	this.puntosTotales = puntosTotales;
    }
}