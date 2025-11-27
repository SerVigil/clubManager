package com.clubManager.baseDatosClub.entidades;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre un jugador y un partido.
 * Forma parte de la clave primaria compuesta.
 * 
 * Mapea la tabla {@code jugador_partido} en la base de datos con clave
 * primaria compuesta por jugador y partido.
 * 
 * Esta clase usa {@link JugadorPartidoPK} como clase para la clave primaria compuesta.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "jugador_partido")
public class JugadorPartido implements Serializable {


    // Área de datos
	
    private static final long serialVersionUID = 1L;

    /**
     * Clave primaria compuesta formada por idJugador, idPartido y puntuación.
     */
    
    @EmbeddedId
    private JugadorPartidoPK id;
    
    @Column(name = "puntuacion")
    private Integer puntuacion = 0;

    // Relaciones

    /**
     * Relación con el jugador.
     * Mapea el campo {@code idJugador} de la clave compuesta.
     */
    
    @MapsId("idJugador")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idJugador", nullable = false)
    @JsonBackReference
    private Jugador jugador;

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
    
    public JugadorPartido() {}
    
    public JugadorPartido(Jugador jugador, Partido partido) 
    {
        this.id = new JugadorPartidoPK(jugador.getIdJugador(), partido.getIdPartido());
        this.jugador = jugador;
        this.partido = partido;
    }

    //Métodos Getters y Setters

    public JugadorPartidoPK getId() 
    {
        return id;
    }

    public void setId(JugadorPartidoPK id) 
    {
        this.id = id;
    }

    public Jugador getJugador() 
    {
        return jugador;
    }

    public void setJugador(Jugador jugador) 
    {
        this.jugador = jugador;
    }

    public Partido getPartido() 
    {
        return partido;
    }

    public void setPartido(Partido partido) 
    {
        this.partido = partido;
    }

	public Integer getPuntuacion() 
	{
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) 
	{
		this.puntuacion = puntuacion;
	}   
}