package com.clubManager.baseDatosClub.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Clase que representa la clave primaria compuesta de la entidad EquipoPartido.
 * Incluye los campos idEquipo e idPartido.
 * Esta clase es embebida y Serializable para ser usada como clave compuesta en JPA.
 * 
 * @author Sergio Vigil Soto
 */

@Embeddable
public class EquipoPartidoPK implements Serializable {
	
	// Área de datos
	
	private static final long serialVersionUID = 1L;
    
    private String idEquipo;
 
    private Long idPartido;
    
    //Constructores
    
    public EquipoPartidoPK() {}

	public EquipoPartidoPK(String idEquipo, Long idPartido) 
	{
		this.idEquipo = idEquipo;
		this.idPartido = idPartido;
	}
	
	//Métodos Getter y Setter

	public String getIdEquipo() 
	{
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) 
	{
		this.idEquipo = idEquipo;
	}

	public Long getIdPartido() 
	{
		return idPartido;
	}

	public void setIdPartido(Long idPartido) 
	{
		this.idPartido = idPartido;
	}
	
	//Métodos HashCode y Equals

	@Override
	public int hashCode() 
	{
		return Objects.hash(idPartido, idEquipo);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipoPartidoPK other = (EquipoPartidoPK) obj;
		return Objects.equals(idPartido, other.idPartido) && Objects.equals(idEquipo, other.idEquipo);
	}
}