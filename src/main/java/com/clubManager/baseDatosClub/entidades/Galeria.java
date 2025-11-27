package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

/**
 * Entidad que representa un documento multimedia o archivo subido a la galería.
 * 
 * Mapea la tabla {@code galeria} en la base de datos y contiene información 
 * como el autor, tipo, nombre, fecha y contenido del archivo, así como su asociación
 * con un entrenador, jugador o padre.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "galeria")
public class Galeria {

    // Área de Datos

    /**
     * Identificador único del documento.
     * Corresponde a la columna {@code id_documento}, con auto-incremento.
     */
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDocumento")
    private Long idDocumento;

    /**
     * Tipo del documento (por ejemplo: imagen, vídeo, PDF).
     * Columna {@code tipo}, longitud máxima 255, no nula.
     */
    
    @Column(name = "tipo", nullable = false, length = 255)
    private String tipo;

    /**
     * Autor del documento.
     * Columna {@code autor}, longitud máxima 255, no nula.
     */
    
    @Column(name = "autor", nullable = false, length = 255)
    private String autor;

    /**
     * Fecha de subida o creación del documento.
     * Columna {@code fecha}, no nula.
     */
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Ruta del archivo.
     * Columna {@code url}.
     */
    
    @Column(name = "url")
    private String url;

 
    /**
     * Nombre del documento para mostrar o identificar.
     * Columna {@code nombre_documento}, longitud máxima 255, no nula.
     */
    
    @Column(name = "nombreDocumento", nullable = false, length = 255)
    private String nombreDocumento;
  
    // Relaciones

    /**
     * Entrenador asociado al documento (opcional).
     * Clave foránea {@code id_entrenador}.
     */
    
    @ManyToOne
    @JoinColumn(name = "idEntrenador", nullable = true)
    private Entrenador entrenador;

    /**
     * Jugador asociado al documento (opcional).
     * Clave foránea {@code idJugador}.
     */
    
    @ManyToOne
    @JoinColumn(name = "idJugador", nullable = true)
    private Jugador jugador;

    /**
     * Padre asociado al documento (opcional).
     * Clave foránea {@code idPadre}.
     */
    
    @ManyToOne
    @JoinColumn(name = "idPadre", nullable = true)
    private Padre padre;
    
    /**
     * Equipo asociado al documento.
     * Clave foránea {@code idEquipo}.
     */
    
    @ManyToOne
    @JoinColumn(name = "idEquipo", nullable = true)
    private Equipo equipo;

    // Métodos Getter y Setter

    public Long getIdDocumento() 
    {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) 
    {
        this.idDocumento = idDocumento;
    }

    public String getTipo() 
    {
        return tipo;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

    public String getAutor() 
    {
        return autor;
    }

    public void setAutor(String autor) 
    {
        this.autor = autor;
    }

    public LocalDate getFecha() 
    {
        return fecha;
    }

    public void setFecha(LocalDate fecha) 
    {
        this.fecha = fecha;
    }

    public String getUrl() 
    {
        return url;
    }

    public void setUrl(String url) 
    {
        this.url = url;
    }


    public String getNombreDocumento() 
    {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) 
    {
        this.nombreDocumento = nombreDocumento;
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

    public Padre getPadre() 
    {
        return padre;
    }

    public void setPadre(Padre padre) 
    {
        this.padre = padre;
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