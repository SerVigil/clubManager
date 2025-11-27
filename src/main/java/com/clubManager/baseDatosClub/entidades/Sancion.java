package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una sanción impuesta a un jugador.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "sancion")
public class Sancion {

    // Área de datos

    /**
     * Identificador único de la sanción.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSancion")
    private Long idSancion;

    /**
     * Tipo de sanción.
     *
     */
    
    @Size(max = 100, message = "El tipo de sanción no puede tener más de 100 caracteres")
    @Column(name = "tipo", length = 100)
    private String tipoSancion;

    /**
     * Fecha en la que se impone la sanción.
     *
     */
    
    @Column(name = "fecha")
    private LocalDate fecha;

    /**
     * Duración de la sanción.
     * 
     */
    
    @Size(max = 50, message = "La duración no puede tener más de 50 caracteres")
    @Column(name = "duracion", length = 50)
    private String duracion;

    /**
     * Descripción o motivo de la sanción.
     * Campo obligatorio, hasta 1000 caracteres.
     */
    
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    // Relaciones

    /**
     * Jugador al que se le aplica la sanción.
     * Clave foránea hacia idJugador (de tipo String).
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkIdJugador", referencedColumnName = "idJugador", nullable = false)
    private Jugador jugador;

    // Métodos Getters y Setters

    public Long getIdSancion() 
    {
        return idSancion;
    }

    public void setIdSancion(Long idSancion) 
    {
        this.idSancion = idSancion;
    }

    public String getTipoSancion() 
    {
        return tipoSancion;
    }

    public void setTipoSancion(String tipoSancion) 
    {
        this.tipoSancion = tipoSancion;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getDuracion() 
    {
        return duracion;
    }

    public void setDuracion(String duracion) 
    {
        this.duracion = duracion;
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