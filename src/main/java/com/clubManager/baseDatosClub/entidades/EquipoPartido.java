package com.clubManager.baseDatosClub.entidades;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre un equipo y un partido,
 * forma parte de la clave primaria compuesta.
 * 
 * Mapea la tabla {@code equipo_partido} en la base de datos con clave
 * primaria compuesta por equipo y partido.
 * 
 * Esta clase usa {@link EquipoPartidoPK} como clase para la clave primaria compuesta.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "equipo_partido")
public class EquipoPartido implements Serializable {
	
	// Área de Datos
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Clave primaria compuesta formada por idEquipo e idPartido.
     */
    
    @EmbeddedId
    private EquipoPartidoPK id;
    
    // Relaciones
    
    /**
     * Relación con el equipo.
     * Mapea el campo {@code idEquipo} de la clave compuesta.
     */
    
    @MapsId("idEquipo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipo", nullable = false)
    @JsonBackReference
    private Equipo equipo;

    /**
     * Relación con el partido.
     * Mapea el campo {@code idPartido} de la clave compuesta.
     */
    
    @MapsId("idPartido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPartido", nullable = false)
    @JsonBackReference
    private Partido partido;


    
    // Constructores
    
    public EquipoPartido() {}
    
    public EquipoPartido(Equipo equipo, Partido partido) 
    {
        this.id = new EquipoPartidoPK(equipo.getIdEquipo(), partido.getIdPartido());
        this.equipo = equipo;
        this.partido = partido;
    }
    
    //Métodos Getters y Setters
   
    public EquipoPartidoPK getId() 
    {
        return id;
    }

    public void setId(EquipoPartidoPK id) 
    {
        this.id = id;
    }

    public Equipo getEquipo() 
    {
        return equipo;
    }

    public void setEquipo(Equipo equipo) 
    {
        this.equipo = equipo;
    }

    public Partido getPartido() 
    {
        return partido;
    }

    public void setPartido(Partido partido) 
    {
        this.partido = partido;
    }

    //Métodos HashCode y Equals
    
	@Override
	public int hashCode() 
	{
		return Objects.hash(id);
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
		EquipoPartido other = (EquipoPartido) obj;
		return Objects.equals(id, other.id);
	}
    
    
}