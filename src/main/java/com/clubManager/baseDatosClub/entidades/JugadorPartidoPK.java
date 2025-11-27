package com.clubManager.baseDatosClub.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Clase embebida que representa la clave primaria compuesta
 * para la entidad {@link JugadorPartido}.
 * 
 * Contiene los campos:
 * - jugador (idJugador),
 * - partido (idPartido),
 * 
 * @author Sergio Vigil Soto
 */

@Embeddable
public class JugadorPartidoPK implements Serializable {
	
	//Area de datos

    private static final long serialVersionUID = 1L;

    private String idJugador;

    private Long idPartido;
    
    //Constructores

    public JugadorPartidoPK() {}

    public JugadorPartidoPK(String idJugador, Long idPartido) 
    {
        this.idJugador = idJugador;
        this.idPartido = idPartido;
    }
    
    //Métodos Getter y Setter

    public String getIdJugador() 
    {
        return idJugador;
    }

    public void setIdJugador(String idJugador) 
    {
        this.idJugador = idJugador;
    }

    public Long getIdPartido() 
    {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) 
    {
        this.idPartido = idPartido;
    }
    
    //Métodos Equals y HashCode

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (!(o instanceof JugadorPartidoPK)) return false;
        JugadorPartidoPK that = (JugadorPartidoPK) o;
        return Objects.equals(idJugador, that.idJugador) &&
               Objects.equals(idPartido, that.idPartido);
    }

    @Override
    public int hashCode() 
    {
        return Objects.hash(idJugador, idPartido);
    }
}