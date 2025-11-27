package com.clubManager.baseDatosClub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO que representa la información de inicio de sesión de un usuario.
 * Contiene el identificador y la contraseña.
 * Se utiliza para recibir los datos del cliente en las peticiones de login.
 * 
 * @author Sergio Vigil Soto
 */

public class LoginRequest {
	
	//Area de datos

    @JsonProperty("identificador")
    private String identificador;

    @JsonProperty("password")
    private String password;
    
    //Constructores

    public LoginRequest() {}

    public LoginRequest(String identificador, String password) 
    {
        this.identificador = identificador;
        this.password = password;
    }
    
    //Métodos Getter y Setter

    public String getIdentificador()
    {
        return identificador;
    }

    public void setIdentificador(String identificador) 
    {
        this.identificador = identificador;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}