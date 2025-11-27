package com.clubManager.baseDatosClub.entidades;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un informe realizado por un entrenador.
 * 
 * Mapea la tabla {@code informe} en la base de datos y contiene información como 
 * la fecha, contenido, tipo de informe, y las relaciones con el entrenador y jugador.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "informe")
public class Informe implements Serializable {
	
	//Area de datos

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del informe.
     * Corresponde a la columna {@code idInforme}.
     */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInforme", nullable = false)
    private Long idInforme;

    /**
     * Fecha del informe.
     */
    
    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Contenido del informe.
     */
    
    @NotNull(message = "El contenido no puede ser nulo")
    @Size(max = 1000, message = "El contenido no puede superar 1000 caracteres")
    @Column(name = "contenido", nullable = false, length = 1000)
    private String contenido;

    /**
     * Tipo de informe.
     */
    
    @NotNull(message = "El tipo no puede ser nulo")
    @Size(max = 30, message = "El tipo no puede superar 30 caracteres")
    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;
    
    // Relaciones

    /**
     * Relación muchos a uno con {@link Entrenador}.
     * Cada informe es emitido por un único entrenador, por lo que 
     * este campo es obligatorio. Permite identificar al entrenador
     * que redacta el informe.
     */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntrenador", nullable = false)
    @JsonIgnore
    private Entrenador entrenador;

    /**
     * Relación muchos a uno con {@link Jugador}.
     * Un informe puede estar dirigido a un jugador concreto o no.
     * Este campo es opcional.
     */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idJugador", nullable = true)
    @JsonIgnore
    private Jugador jugador;
    
    /**
     * Equipo al que pertenece este informe.
     * Relación muchos a uno con {@link Equipo}.
     * Cada informe está asociado a un único equipo.
     */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipo")
    @JsonIgnore
    private Equipo equipo;

    // Constructores

    public Informe() {}

    public Informe(LocalDate fecha, String contenido, String tipo, Entrenador entrenador) 
    {
        this.fecha = fecha;
        this.contenido = contenido;
        this.tipo = tipo;
        this.entrenador = entrenador;
    }

    // Métodos Getters y setters

    public Long getIdInforme() 
    {
        return idInforme;
    }

    public void setIdInforme(Long idInforme) 
    {
        this.idInforme = idInforme;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getContenido() 
    {
        return contenido;
    }

    public void setContenido(String contenido) 
    {
        this.contenido = contenido;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

	public Entrenador getEntrenador() 
	{
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) 
	{
		this.entrenador = entrenador;
	}

	public Jugador getJugador() 
	{
		return jugador;
	}

	public void setJugador(Jugador jugador) 
	{
		this.jugador = jugador;
	}

	public long getId() 
	{
		return idInforme;
	}

	public Equipo getEquipo() 
	{
		return equipo;
	}

	public void setEquipo(Equipo equipo) 
	{
		this.equipo = equipo;
	}
}