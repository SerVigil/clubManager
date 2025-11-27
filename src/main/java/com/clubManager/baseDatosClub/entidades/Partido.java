package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un partido dentro del sistema.
 * 
 * Mapea la tabla {@code partido} en la base de datos, que almacena información sobre los partidos
 * disputados por el club, incluyendo fecha, lugar, tipo y resultado, así como las relaciones con jugadores.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "partido")
public class Partido {

    // Área de datos

    /**
     * Identificador único del partido.
     * Clave primaria con auto-incremento.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPartido")
    private Long idPartido;

    /**
     * Fecha en la que se juega el partido.
     * Campo obligatorio.
     */
    
    @NotNull(message = "La fecha del partido no puede ser nula")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Lugar donde se disputa el partido.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 255, message = "El lugar no puede tener más de 255 caracteres")
    @Column(name = "lugar", length = 255)
    private String lugar;
    
    /**
     * Equipo que juega como local el partido.
     * Campo obligatorio.
     */
    
    
    @Column(name = "local", length = 50, nullable = false)
    @Size(max = 50, message = "El equipo local no puede tener más de 50 caracteres")
    private String local;
    
    /**
     * Equipo que juega como visitante el partido.
     * Campo obligatorio.
     */

    @Column(name = "visitante", length = 50, nullable = false)
    @Size(max = 50, message = "El equipo visitante no puede tener más de 50 caracteres")
    private String visitante;

    /**
     * Resultado final del partido.
     *
     */
    
    @Size(max = 50, message = "El resultado no puede tener más de 50 caracteres")
    @Column(name = "resultado", length = 50)
    private String resultado;

    /**
     * Tipo de partido (amistoso, liga, copa, etc.).
     * Campo opcional, longitud máxima de 50 caracteres.
     */
    
    @Size(max = 50, message = "El tipo de partido no puede tener más de 50 caracteres")
    @Column(name = "tipoPartido", length = 50)
    private String tipoPartido;
    
    //Relaciones
    
    /**
     * Lista de asociaciones entre este partido y los jugadores que participaron.
     * 
     * Relación uno a muchos con {@link JugadorPartido}, mapeada por el campo {@code partido}.
     * 
     * Al eliminar un partido, todas sus asociaciones con jugadores también se eliminan. 
     */
    
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<JugadorPartido> jugadorPartidos;
    
    /**
     * Equipo al que pertenece un partido.
     * Relación muchos a uno con {@link Equipo}.
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEquipo")
    @JsonIgnore
    private Equipo equipo;
    
    // Métodos Getters y Setters

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getLugar() 
    {
        return lugar;
    }

    public void setLugar(String lugar) 
    {
        this.lugar = lugar;
    }

    public String getResultado() 
    {
        return resultado;
    }

    public void setResultado(String resultado) 
    {
        this.resultado = resultado;
    }

    public String getTipoPartido() 
    {
        return tipoPartido;
    }

    public void setTipoPartido(String tipoPartido) 
    {
        this.tipoPartido = tipoPartido;
    }

	public List<JugadorPartido> getJugadorPartidos() {
		return jugadorPartidos;
	}

	public void setJugadorPartidos(List<JugadorPartido> jugadorPartidos) 
	{
		this.jugadorPartidos = jugadorPartidos;
	}

	public Long getIdPartido() 
	{
		return idPartido;
	}

	public void setIdPartido(Long idPartido) 
	{
		this.idPartido = idPartido;
	}

	public String getLocal() 
	{
		return local;
	}

	public void setLocal(String local) 
	{
		this.local = local;
	}

	public String getVisitante() 
	{
		return visitante;
	}

	public void setVisitante(String visitante) 
	{
		this.visitante = visitante;
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