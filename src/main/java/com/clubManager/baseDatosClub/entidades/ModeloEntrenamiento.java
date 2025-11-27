package com.clubManager.baseDatosClub.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * Entidad que representa un modelo predefinido de entrenamiento.
 * 
 * Mapea la tabla {@code modelo_entrenamiento} en la base de datos, que almacena
 * plantillas base para planificar entrenamientos.
 * 
 * Cada modelo define el tipo, duración, y observaciones generales que pueden
 * ser reutilizadas al crear entrenamientos concretos.
 * 
 * Un modelo puede estar asociado a múltiples entrenamientos.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "modelo_entrenamiento")
public class ModeloEntrenamiento {

    // Área de Datos
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModelo;
    
    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(length = 100, nullable = false)
    private String nombre;
    
    @NotBlank(message = "El tipo del modelo no puede estar vacío")
    @Size(max = 20, message = "El tipo no puede superar los 20 caracteres")
    @Column(length = 20, nullable = false)
    private String tipo;
    
    @NotNull(message = "La duración no puede ser nula")
    @Column(nullable = false)
    private Integer duracion;
    
    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    @Column(length = 500)
    private String observaciones;

    // Relaciones

    /**
     * Lista de entrenamientos basados en este modelo.
     * Relación uno a muchos con {@link Entrenamiento}.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "modeloEntrenamiento")
    private List<Entrenamiento> entrenamientos;
    
    /**
     * Equipo al que pertenecen los modelos de entrenamiento
     * Relación muchos a uno con {@link Equipo}.
     */
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idEquipo", nullable = true)
    private Equipo equipo;

    // Métodos Getter y Setter

    public Long getIdModelo() 
    {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) 
    {
        this.idModelo = idModelo;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public Integer getDuracion() 
    {
        return duracion;
    }

    public void setDuracion(Integer duracion) 
    {
        this.duracion = duracion;
    }

    public String getObservaciones() 
    {
        return observaciones;
    }

    public void setObservaciones(String observaciones) 
    {
        this.observaciones = observaciones;
    }

    public List<Entrenamiento> getEntrenamientos() 
    {
        return entrenamientos;
    }

    public void setEntrenamientos(List<Entrenamiento> entrenamientos) 
    {
        this.entrenamientos = entrenamientos;
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