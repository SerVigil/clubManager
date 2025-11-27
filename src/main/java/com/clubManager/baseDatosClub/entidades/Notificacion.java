package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una notificación enviada a jugadores, entrenadores o padres.
 * 
 * Mapea la tabla {@code Notificacion} en la base de datos.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "notificacion")
public class Notificacion {

    // Área de Datos

    /**
     * Identificador único de la notificación.
     * Corresponde a la columna {@code idNotificacion}.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion")
    private Long idNotificacion;
    
    /**
     * Título de la notificación
     * Columna {@code titulo}
     */
    
    @NotNull
    @Size(max = 255)
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    /**
     * Mensaje de la notificación.
     * Columna {@code mensaje}, máximo 255 caracteres, no nula.
     */
    
    @NotNull
    @Size(max = 255)
    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    /**
     * Fecha de emisión de la notificación.
     * Columna {@code fecha}, no nula.
     */
    
    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    //Relaciones
    
    /**
     * Equipo al que pertenece la notificación.
     * Relación muchos a uno con {@link Equipo}.
     */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipo", nullable = false)
    @JsonIgnore
    private Equipo equipo;
    
    // Métodos Getter y Setter 

    public Long getIdNotificacion() 
    {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) 
    {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() 
    {
        return mensaje;
    }

    public void setMensaje(String mensaje) 
    {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

	public String getTitulo() 
	{
		return titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
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