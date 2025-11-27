package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un entrenamiento.
 * 
 * Mapea la tabla {@code Entrenamiento} en la base de datos.
 * 
 * Gestiona las relaciones de los entrenamientos con los equipos para los que son creados,
 * y con los distintos modelos de entrenamientos. 
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "entrenamiento")
public class Entrenamiento {

	//Area de Datos
	
    /**
     * Identificador único del entrenamiento.
     * Corresponde a la columna {@code idEntrenamiento}.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrenamiento;

    /**
     * Fecha en la que se realiza el entrenamiento.
     * Columna {@code fecha}, no nula.
     */
    
    @NotNull(message = "La fecha no puede ser nula")
    @Column(nullable = false)
    private LocalDate fecha;

    /**
     * Hora en la que se inicia el entrenamiento.
     * Columna {@code hora}, no nula.
     */
    
    @NotNull(message = "La hora no puede ser nula")
    @Column(nullable = false)
    private LocalTime hora;

    /**
     * Tipo de entrenamiento.
     * Columna {@code tipo}, con longitud máxima 20 caracteres, no nula.
     */
    
    @NotBlank(message = "El tipo de entrenamiento no puede estar vacío")
    @Size(max = 20, message = "El tipo no puede superar los 20 caracteres")
    @Column(length = 20, nullable = false)
    private String tipo;

    /**
     * Observaciones adicionales sobre el entrenamiento.
     * Columna {@code observaciones}, longitud máxima 500 caracteres, puede ser nula.
     */
    
    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    @Column(length = 500)
    private String observaciones;
    
    //Relaciones
    
    /**
     * Equipo al que pertenece este entrenamiento.
     * Relación muchos a uno con {@link Equipo}.
     * 
     * Cada entrenamiento está asociado a un único equipo.
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;
    
    /**
     * Modelo base utilizado para planificar un entrenamiento.
     * Relación muchos a uno con {@link ModeloEntrenamiento}.
     * 
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idModelo")
    private ModeloEntrenamiento modeloEntrenamiento;

    // Métodos Getters y Setters

    public Long getIdEntrenamiento() 
    {
        return idEntrenamiento;
    }

    public void setIdEntrenamiento(Long idEntrenamiento) 
    {
        this.idEntrenamiento = idEntrenamiento;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public LocalTime getHora() 
    {
        return hora;
    }

    public void setHora(LocalTime hora) 
    {
        this.hora = hora;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public String getObservaciones() 
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones) 
    {
        this.observaciones = observaciones;
    }

	public Equipo getEquipo() 
	{
		return equipo;
	}

	public void setEquipo(Equipo equipo) 
	{
		this.equipo = equipo;
	}

	public ModeloEntrenamiento getModeloEntrenamiento() 
	{
		return modeloEntrenamiento;
	}

	public void setModeloEntrenamiento(ModeloEntrenamiento modeloEntrenamiento) 
	{
		this.modeloEntrenamiento = modeloEntrenamiento;
	}	
}