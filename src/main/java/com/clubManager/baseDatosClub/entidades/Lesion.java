package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una lesión sufrida por un jugador.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "lesion")
public class Lesion {

    //Área de Datos 

    /**
     * Identificador único de la lesión.
     * Corresponde a la columna {@code idLesion}, clave primaria.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLesion")
    private Long idLesion;

    /**
     * Tipo de lesión (por ejemplo, muscular, fractura...).
     * Columna {@code tipo}, longitud máxima 100 caracteres.
     */
    
    @Size(max = 100)
    @Column(name = "tipo", length = 100)
    private String tipo;

    /**
     * Fecha en que comenzó la lesión.
     * Columna {@code fechaInicio}.
     */
    
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    /**
     * Fecha en que terminó la lesión.
     * Columna {@code fechaFin}.
     */
    
    @Column(name = "fechaFin")
    private LocalDate fechaFin;

    /**
     * Descripción textual de la lesión.
     * Columna {@code descripcion}, longitud máxima 1000 caracteres.
     */
    
    @Size(max = 1000)
    @Column(name = "descripcion",length = 1000)
    private String descripcion;

    // Relaciones 

    /**
     * Jugador que sufrió la lesión.
     * Clave foránea {@code fkIdJugador}, no nula.
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkIdJugador", referencedColumnName = "idJugador", nullable = false)
    private Jugador jugador;

    // Métodos Getters y Setters 

    public Long getIdLesion() 
    {
        return idLesion;
    }

    public void setIdLesion(Long idLesion) 
    {
        this.idLesion = idLesion;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() 
    {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) 
    {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() 
    {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) 
    {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() 
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion) 
    {
        this.descripcion = descripcion;
    }

    public Jugador getJugador() 
    {
        return jugador;
    }

    public void setJugador(Jugador jugador) 
    {
        this.jugador = jugador;
    }
}