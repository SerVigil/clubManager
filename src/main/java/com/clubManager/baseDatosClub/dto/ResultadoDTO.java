package com.clubManager.baseDatosClub.dto;

/**
 * DTO para asignar el resultado a un partido.
 * 
 * @author Sergio Vigil Soto
 */

public class ResultadoDTO {
	
	//Area de Datos

    private String resultado;
    
    //Métodos Getter y Setter

    public String getResultado() 
    {
        return resultado;
    }

    public void setResultado(String resultado) 
    {
        this.resultado = resultado;
    }
}