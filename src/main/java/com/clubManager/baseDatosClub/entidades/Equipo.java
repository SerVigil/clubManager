package com.clubManager.baseDatosClub.entidades;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * Entidad que representa a un equipo.
 * 
 * Mapea la tabla {@code equipo} en la base de datos, que almacena información del equipo,
 * así como sus relaciones con los entrenadores, jugadores, partidos, entrenamientos...
 * Se aplican restricciones y validaciones para garantizar la integridad de los datos.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "equipo")
public class Equipo {
	
	//Area de Datos
	
	/**
	 * Identificador único del equipo
	 * Corresponde a la columna {@code idEquipo}.
	 */
	
	@Id
	@Column(name = "idEquipo", length = 36)
    @NotBlank(message = "El id del equipo no puede estar vacío")
    @Size(max = 36, message = "El id del equipo no puede tener más de 36 caracteres")
    private String idEquipo;
	
	/**
     * Nombre del equipo.
     * Campo obligatorio, longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 255)
    @Column(name = "nombreEquipo")
    private String nombreEquipo;
	
    /**
     * Categoría del equipo
     * Campo obligatorio, longitud máxima de 20 caracteres.
     */
    
    @NotBlank(message = "La categoría no puede estar vacía")
    @Size(max = 20)
    @Column(name = "categoria")
    private String categoria;
    
    /**
     * Ruta o nombre de archivo de la foto del equipo.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 255)
    @Column(name = "fotoEquipo")
    private String fotoEquipo;
    
    /**
     * Ruta o nombre de archivo del escudo del equipo.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 500)
    @Column(name = "escudoEquipo")
    private String escudoEquipo;
    
    /**
     * Contraseña del equipo.
     * Campo obligatorio, longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;
    
    /**
     * Url que nos va a mostrar la clasificación del equipo
     * Campo para poder guardar una url.
     */
    
    @Size(max = 500)
    @Column(name = "clasificacion")
    private String clasificacion;
    
    //Relaciones
    
    /**
     * Jugadores que pertenecen a este equipo.
     * Relación uno a muchos con {@link Jugador}.
     *
     * La lista contiene todos los jugadores asignados a este equipo.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Jugador> jugadores;

    /**
     * Entrenadores asignados a este equipo.
     * Relación uno a muchos con {@link Entrenador}.
     * Un equipo puede tener varios entrenadores.
     */
    
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Entrenador> entrenadores = new ArrayList<>();
    
    /**
     * Informes asociados a este equipo.
     * Relación uno a muchos con {@link Informe}.
     * La lista contiene todos los informes generados para este equipo.
     */
    
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Informe> informes;


    /**
     * Entrenamientos programados para este equipo.
     * Relación uno a muchos con {@link Entrenamiento}.
     * La lista contiene todos los entrenamientos de este equipo.
     */
    
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Entrenamiento> entrenamientos;

    /**
     * Partidos que pertenecen a este equipo.
     * Relación uno a muchos con {@link Partido}.
     *
     * La lista contiene todos los partidos asignados a este equipo.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partido> partidos;
    
    /**
     * Padres asociados a este equipo.
     * Relación muchos a muchos con {@link Padre}.
     * La tabla intermedia es 'equipo_padre'.
     */
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinTable
    (
        name = "equipo_padre",
        joinColumns = @JoinColumn(name = "idEquipo"),
        inverseJoinColumns = @JoinColumn(name = "idPadre")
    )
    @JsonIgnore
    private List<Padre> padres;
    
    /**
     * Lista de notificaciones asociadas al equipo.
     * Relación uno a muchos con {@link Notificacion}.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notificacion> notificaciones;
    
    /**
     * Lista de documentos asociados al equipo.
     * Relación uno a muchos con {@link Galeria}.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Galeria> documentosGaleria;
    
    /**
     * Lista de modelos de entrenamiento asociados al equipo.
     * Relación uno a muchos con {@link ModeloEntrenamiento}.
     */
    
    @JsonIgnore
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModeloEntrenamiento> modelos;

    //Métodos Getter y Setter

	public String getIdEquipo() 
	{
		return idEquipo;
	}

	public void setIdEquipo(String idEquipo) 
	{
		this.idEquipo = idEquipo;
	}

	public String getNombreEquipo() 
	{
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) 
	{
		this.nombreEquipo = nombreEquipo;
	}

	public String getCategoria() 
	{
		return categoria;
	}

	public void setCategoria(String categoria) 
	{
		this.categoria = categoria;
	}

	public String getFotoEquipo() 
	{
		return fotoEquipo;
	}

	public void setFotoEquipo(String fotoEquipo) 
	{
		this.fotoEquipo = fotoEquipo;
	}

	public List<Jugador> getJugadores() 
	{
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) 
	{
		this.jugadores = jugadores;
	}

	public List<Entrenador> getEntrenadores() 
	{
		return entrenadores;
	}

	public void setEntrenadores(List<Entrenador> entrenadores) 
	{
		this.entrenadores = entrenadores;
	}

	public List<Entrenamiento> getEntrenamientos() 
	{
		return entrenamientos;
	}

	public void setEntrenamientos(List<Entrenamiento> entrenamientos) 
	{
		this.entrenamientos = entrenamientos;
	}

	public List<Partido> getPartidos() 
	{
		return partidos;
	}

	public void setPartidos(List<Partido> partidos) 
	{
		this.partidos = partidos;
	}

	public String getEscudoEquipo() 
	{
		return escudoEquipo;
	}

	public void setEscudoEquipo(String escudoEquipo) 
	{
		this.escudoEquipo = escudoEquipo;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public List<Padre> getPadres() 
	{
		return padres;
	}

	public void setPadres(List<Padre> padres) 
	{
		this.padres = padres;
	}

	public List<Informe> getInformes() 
	{
		return informes;
	}

	public void setInformes(List<Informe> informes) 
	{
		this.informes = informes;
	}

	public List<Notificacion> getNotificaciones() 
	{
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) 
	{
		this.notificaciones = notificaciones;
	}

	public String getClasificacion() 
	{
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) 
	{
		this.clasificacion = clasificacion;
	}

	public List<Galeria> getDocumentosGaleria() 
	{
		return documentosGaleria;
	}

	public void setDocumentosGaleria(List<Galeria> documentosGaleria) 
	{
		this.documentosGaleria = documentosGaleria;
	}

	public List<ModeloEntrenamiento> getModelos() 
	{
		return modelos;
	}

	public void setModelos(List<ModeloEntrenamiento> modelos) 
	{
		this.modelos = modelos;
	}	
}