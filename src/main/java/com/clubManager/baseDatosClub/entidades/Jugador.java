package com.clubManager.baseDatosClub.entidades;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Representa a un jugador dentro del sistema.
 * Esta clase está mapeada con la tabla jugador de la base de datos.
 * Incluye validaciones y relaciones con otras entidades como partidos, padres, etc.
 * 
 * Se añaden anotaciones @JsonIgnore a las colecciones para evitar errores de LazyInitialization
 * al serializar objetos a JSON.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "jugador")
public class Jugador {

    // Area de Datos

    @Id
    @Column(name = "idJugador", length = 36)
    @NotBlank(message = "El id del jugador no puede estar vacío")
    @Size(max = 36, message = "El id del jugador no puede tener más de 36 caracteres")
    private String idJugador;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "Apellidos no puede estar vacío")
    @Size(max = 255)
    @Column(name = "apellidos")
    private String apellidos;

    @NotBlank(message = "El dni no puede estar vacío")
    @Size(max = 255)
    @Column(name = "dni", unique = true)
    private String dni;

    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;

    @NotBlank(message = "El tipoUsuario no puede estar vacío")
    @Size(max = 10)
    @Column(name = "tipoUsuario") 
    private String tipoUsuario;

    @NotBlank(message = "El password no puede estar vacío")
    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 255)
    @Column(name = "foto") 
    private String foto;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 255)
    @Column(name = "telefono")
    private String telefono;

    @Email
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Min(0)
    @Column(name = "goles")
    private Integer goles = 0;
    
    @Min(0)
    @Column(name = "golesEncajados")
    private Integer golesEncajados;

    @Min(0)
    @Column(name = "tarjetasAmarillas")
    private Integer tarjetasAmarillas = 0;

    @Min(0)
    @Column(name = "tarjetasRojas")
    private Integer tarjetasRojas = 0;

    @Column(name = "dorsal")
    private Integer dorsal;

    @Column(name = "activo")
    private boolean activo = true;

    @Size(max = 255)
    @Column(name = "posicion")
    private String posicion;

    @NotNull(message = "La fecha no puede estar vacía.")
    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "puntosTotales")
    private Integer puntosTotales;
    
    @Column(name = "fcmToken")
    private String fcmToken;

    // Relaciones
    
    /**
     * Equipo al que pertenece este jugador.
     * Relación muchos a uno con {@link Equipo}.
     * Cada jugador está asignado a un único equipo.
     */
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEquipo")
    @JsonIgnore
    private Equipo equipo;

    /**
     * Relación uno a muchos con {@link JugadorPartido}, mapeada por el campo {@code jugador}.
     * Cada instancia representa una participación del jugador en un partido concreto.
     */
    
    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<JugadorPartido> jugadorPartidos;
    
    /**
     * Padres asociados al jugador.
     * Relación muchos a muchos entre {@link Jugador} y {@link Padre}.
     */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    (
        name = "Jugador_Padre",
        joinColumns = @JoinColumn(name = "idJugador"),
        inverseJoinColumns = @JoinColumn(name = "idPadre")
    )
    @JsonIgnore
    private List<Padre> padres;
    
    /**
     * Sanciones registradas para este jugador.
     * Relación uno a muchos con {@link Sancion}.
     */

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Sancion> sanciones;
    
    /**
     * Lesiones asociadas a un jugador.
     * Relación uno a muchos con {@link Lesion}.
     */

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Lesion> lesiones;

    /**
     * Documentos multimedia asociados a este jugador.
     * Relación uno a muchos con {@link Galeria}.
     */
    
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Galeria> galeriaJugador;
    
    /**
     * Informes dirigidos a este jugador.
     * Relación uno a muchos con {@link Informe}
     */
    
    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Informe> informesRecibidos;

    // Métodos Getters y Setters

    public String getIdJugador() 
    {
        return idJugador;
    }

    public void setIdJugador(String idJugador) 
    {
        this.idJugador = idJugador;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getApellidos() 
    {
        return apellidos;
    }

    public void setApellidos(String apellidos) 
    {
        this.apellidos = apellidos;
    }

    public String getDni() 
    {
        return dni;
    }

    public void setDni(String dni) 
    {
        this.dni = dni;
    }

    public String getDireccion() 
    {
        return direccion;
    }

    public void setDireccion(String direccion) 
    {
        this.direccion = direccion;
    }

    public String getTipoUsuario() 
    {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) 
    {
        this.tipoUsuario = tipoUsuario;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getFoto() 
    {
        return foto;
    }

    public void setFoto(String foto) 
    {
        this.foto = foto;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public Integer getGoles() 
    {
        return goles;
    }

    public void setGoles(Integer goles) 
    {
        this.goles = goles;
    }

    public Integer getTarjetasAmarillas() 
    {
        return tarjetasAmarillas;
    }

    public void setTarjetasAmarillas(Integer tarjetasAmarillas) 
    {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public Integer getTarjetasRojas() 
    {
        return tarjetasRojas;
    }

    public void setTarjetasRojas(Integer tarjetasRojas) 
    {
        this.tarjetasRojas = tarjetasRojas;
    }

    public Integer getDorsal() 
    {
        return dorsal;
    }

    public void setDorsal(Integer dorsal) 
    {
        this.dorsal = dorsal;
    }

    public boolean isActivo() 
    {
        return activo;
    }

    public void setActivo(boolean activo) 
    {
        this.activo = activo;
    }

    public String getPosicion() 
    {
        return posicion;
    }

    public void setPosicion(String posicion) 
    {
        this.posicion = posicion;
    }

    public LocalDate getFechaNacimiento() 
    {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) 
    {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Equipo getEquipo() 
    {
        return equipo;
    }

    public void setEquipo(Equipo equipo) 
    {
        this.equipo = equipo;
    }

    public List<JugadorPartido> getJugadorPartidos() 
    {
        return jugadorPartidos;
    }

    public void setJugadorPartidos(List<JugadorPartido> jugadorPartidos) 
    {
        this.jugadorPartidos = jugadorPartidos;
    }

    public List<Padre> getPadres() 
    {
        return padres;
    }

    public void setPadres(List<Padre> padres) 
    {
        this.padres = padres;
    }

    public List<Sancion> getSanciones() 
    {
        return sanciones;
    }

    public void setSanciones(List<Sancion> sanciones) 
    {
        this.sanciones = sanciones;
    }

    public List<Lesion> getLesiones() 
    {
        return lesiones;
    }

    public void setLesiones(List<Lesion> lesiones)
    {
        this.lesiones = lesiones;
    }

    public List<Galeria> getGaleriaJugador() 
    {
        return galeriaJugador;
    }

    public void setGaleriaJugador(List<Galeria> galeriaJugador) 
    {
        this.galeriaJugador = galeriaJugador;
    }

    public List<Informe> getInformesRecibidos() 
    {
		return informesRecibidos;
	}
	public void setInformesRecibidos(List<Informe> informesRecibidos)
	{
		this.informesRecibidos = informesRecibidos;
	}
	
	public String getFcmToken() 
	{
		return fcmToken;
	}
	
	public void setFcmToken(String fcmToken) 
	{
		this.fcmToken = fcmToken;
	}
	public Integer getGolesEncajados() 
	{
		return golesEncajados;
	}
	public void setGolesEncajados(Integer golesEncajados) 
	{
		this.golesEncajados = golesEncajados;
	}
	
	public Integer getPuntosTotales() 
	{
		return puntosTotales;
	}
	
	public void setPuntosTotales(Integer puntosTotales) 
	{
		this.puntosTotales = puntosTotales;
	}   
}