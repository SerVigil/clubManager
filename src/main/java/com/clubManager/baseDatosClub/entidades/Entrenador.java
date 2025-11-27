package com.clubManager.baseDatosClub.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un entrenador dentro del sistema.
 * 
 * Mapea la tabla {@code entrenador} en la base de datos, que almacena información personal
 * y de autenticación del entrenador, así como sus relaciones.
 * 
 * Se aplican restricciones y validaciones para garantizar la integridad de los datos.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "entrenador")
public class Entrenador {

    // Area de Datos

    /**
     * Identificador único del entrenador.
     * Corresponde a la columna {@code idEntrenador}.
     */
	
    @Id
    @Column(name = "idEntrenador", length = 36)
    @NotBlank(message = "El id del entrenador no puede estar vacío")
    @Size(max = 36, message = "El id del entrenador no puede tener más de 36 caracteres")
    private String idEntrenador;

    /**
     * Nombre del entrenador.
     * Campo obligatorio, longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Apellidos del entrenador.
     * Campo obligatorio, longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 255)
    @Column(name = "apellidos")
    private String apellidos;

    /**
     * Documento Nacional de Identidad del entrenador.
     * Campo obligatorio y único. Longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(max = 255)
    @Column(name = "dni", unique = true)
    private String dni;

    /**
     * Dirección postal del entrenador.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;

    /**
     * Tipo de usuario dentro del sistema.
     * Campo obligatorio, longitud máxima de 10 caracteres.
     */
    
    @NotBlank(message = "El tipo de usuario no puede estar vacío")
    @Size(max = 10)
    @Column(name = "tipoUsuario", nullable = false)
    private String tipoUsuario;

    /**
     * Contraseña del entrenador.
     * Campo obligatorio, longitud máxima de 255 caracteres.
     */
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Ruta o nombre de archivo de la foto del entrenador.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 255)
    @Column(name = "foto")
    private String foto;

    /**
     * Teléfono de contacto del entrenador.
     * Campo opcional, longitud máxima de 255 caracteres.
     */
    
    @Size(max = 255)
    @Column(name = "telefono")
    private String telefono;

    /**
     * Correo electrónico del entrenador.
     * Campo opcional, debe ser válido si se proporciona. Longitud máxima de 255 caracteres.
     */
    
    @Email(message = "Debe proporcionar un email válido")
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    
    /**
     * Token de Firebase Cloud Messaging (FCM) del entrenador.
     * Se utiliza para enviar notificaciones push al dispositivo del entrenador.
     */
    
    @Column(name = "fcmToken")
    private String fcmToken;

    // Relaciones
    
    /**
     * Documentos multimedia asociados a este entrenador.
     * Relación uno a muchos con Galeria.
     * 
     * Esta lista contiene todos los documentos de la galería que están vinculados
     * a este entrenador.
     */
    
    @OneToMany(mappedBy = "entrenador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Galeria> galeriaEntrenador;
    
    /**
     * Informes emitidos por este entrenador.
     */
    
    @OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Informe> informesEmitidos;
    
    /**
     * Relación muchos-a-uno con la entidad {@link Equipo}.
     *
     * Varios entrenadores pueden estar asociados al mismo equipo.
     *
     */
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;
    
    // Métodos Getters y Setters
   
    public String getIdEntrenador() 
    {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) 
    {
        this.idEntrenador = idEntrenador;
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

	public List<Galeria> getGaleriaEntrenador() 
	{
		return galeriaEntrenador;
	}

	public void setGaleriaEntrenador(List<Galeria> galeriaEntrenador) 
	{
		this.galeriaEntrenador = galeriaEntrenador;
	}
	
	public List<Informe> getInformesEmitidos() 
	{
		return informesEmitidos;
	}

	public void setInformesEmitidos(List<Informe> informesEmitidos) 
	{
		this.informesEmitidos = informesEmitidos;
	}

	public String getFcmToken() 
	{
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) 
	{
		this.fcmToken = fcmToken;
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