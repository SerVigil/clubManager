package com.clubManager.baseDatosClub.dto;

/**
 * DTO que representa la respuesta al iniciar sesión.
 * Contiene el tipo de usuario, el ID del usuario, el token de autenticación
 * y opcionalmente el ID del equipo al que pertenece el usuario.
 * 
 * @author Sergio Vigil Soto
 */

public class LoginResponse {
	
	//Area de datos

    private String tipoUsuario;
    private String idUsuario;
    private String token; 
    private String idEquipo; 

    // Constructores
    
    public LoginResponse(String tipoUsuario, String idUsuario, String token, String idEquipo) 
    {
        this.tipoUsuario = tipoUsuario;
        this.idUsuario = idUsuario;
        this.token = token;
        this.idEquipo = idEquipo;
    }

    public LoginResponse(String tipoUsuario, String idUsuario, String token) 
    {
        this(tipoUsuario, idUsuario, token, null);
    }

    // Métodos Getters y Setters
    
    public String getTipoUsuario() 
    {
        return tipoUsuario;
    }

    public String getIdUsuario() 
    {
        return idUsuario;
    }

    public String getToken()
    {
        return token;
    }

    public String getIdEquipo() 
    {
        return idEquipo;
    }

    public void setTipoUsuario(String tipoUsuario) 
    {
        this.tipoUsuario = tipoUsuario;
    }

    public void setIdUsuario(String idUsuario) 
    {
        this.idUsuario = idUsuario;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public void setIdEquipo(String idEquipo)
    {
        this.idEquipo = idEquipo;
    }
}