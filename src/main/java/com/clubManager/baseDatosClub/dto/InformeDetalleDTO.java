package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa los detalles de un informe
 * 
 * @author Sergio Vigil Soto
 */


public class InformeDetalleDTO {
	
	//Area de datos

    private String fecha;
    private String tipo;
    private String contenido;
    private String nombre;

    // Getters y setters

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

    public String getContenido() 
    {
        return contenido;
    }

    public void setContenido(String contenido)
    {
        this.contenido = contenido;
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