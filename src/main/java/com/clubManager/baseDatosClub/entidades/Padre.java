package com.clubManager.baseDatosClub.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un padre dentro del sistema.
 * 
 * Mapea la tabla {@code padre} en la base de datos, que almacena información personal
 * y de autenticación del padre.
 * 
 * Se aplican restricciones y validaciones para garantizar la integridad de los datos.
 * 
 * @author Sergio Vigil Soto
 */

@Entity
@Table(name = "padre")
public class Padre {

    // Área de datos

    /**
     * Identificador único del padre.
     * Corresponde a la columna {@code idPadre}, clave primaria.
     */
	
    @Id
    @Column(name = "idPadre", length = 36)
    @NotBlank(message = "El id del padre no puede estar vacío")
    @Size(max = 36, message = "El id del padre no puede tener más de 36 caracteres")
    private String idPadre;

    /**
     * Nombre del padre.
     * Campo obligatorio, longitud máxima 15.
     */
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 15, message = "El nombre no puede tener más de 15 caracteres")
    @Column(name = "nombre", nullable = false, length = 15)
    private String nombre;

    /**
     * Apellidos del padre.
     * Campo obligatorio, longitud máxima 30.
     */
    
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 30, message = "Los apellidos no pueden tener más de 30 caracteres")
    @Column(name = "apellidos", nullable = false, length = 30)
    private String apellidos;

    /**
     * DNI del padre.
     * Campo obligatorio y único, longitud máxima 10.
     */
    
    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(max = 10, message = "El DNI no puede tener más de 10 caracteres")
    @Column(name = "dni", nullable = false, unique = true, length = 10)
    private String dni;

    /**
     * Dirección del padre.
     * Campo opcional, longitud máxima 30.
     */
    
    @Size(max = 30, message = "La dirección no puede tener más de 30 caracteres")
    @Column(name = "direccion", length = 30)
    private String direccion;

    /**
     * Tipo de usuario.
     * Campo obligatorio, longitud máxima 10.
     */
    
    @NotBlank(message = "El tipo de usuario no puede estar vacío")
    @Size(max = 10, message = "El tipo de usuario no puede tener más de 10 caracteres")
    @Column(name = "tipoUsuario", nullable = false, length = 10)
    private String tipoUsuario;

    /**
     * Contraseña.
     * Campo obligatorio, longitud máxima 255.
     */
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 255, message = "La contraseña no puede tener más de 255 caracteres")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Foto del padre.
     * Campo opcional, longitud máxima 255.
     */
    
    @Size(max = 255, message = "La foto no puede tener más de 255 caracteres")
    @Column(name = "foto", length = 255)
    private String foto;

    /**
     * Teléfono del padre.
     * Campo obligatorio, longitud máxima 15.
     */
    
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    /**
     * Email del padre.
     * Campo obligatorio, longitud máxima 50, debe ser válido.
     */
    
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe proporcionar un email válido")
    @Size(max = 50, message = "El email no puede tener más de 50 caracteres")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    /**
     * Vínculo familiar.
     * Campo obligatorio, longitud máxima 20.
     */
    
    @NotBlank(message = "El vínculo no puede estar vacío")
    @Size(max = 20, message = "El vínculo no puede tener más de 20 caracteres")
    @Column(name = "vinculo", nullable = false, length = 20)
    private String vinculo;
    
    /**
     * Token necesario para recibir notificaciones
     */
    
    @Column(name = "fcmToken")
    private String fcmToken;

    // Relaciones

    /**
     * Jugadores asociados a este padre.
     * Relación muchos a muchos inversa con Jugador.
     */
    
    @ManyToMany(mappedBy = "padres", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Jugador> jugadores;
    
    /**
     * Documentos multimedia asociados a este padre.
     * Relación uno a muchos con Galeria.
     * 
     * Esta lista contiene todos los documentos de la galería que están vinculados
     * a este padre.
     */
    
    @OneToMany(mappedBy = "padre", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Galeria> galeriaPadre;
    
    /**
     * Equipos a los que pertenece este padre.
     * Relación inversa muchos a muchos con {@link Equipo}.
     */
    
    @ManyToMany(mappedBy = "padres", fetch = FetchType.EAGER)
    @JsonIgnore 
    private List<Equipo> equipos;

    //Métodos Getters y setters

    public String getIdPadre() 
    {
        return idPadre;
    }

    public void setIdPadre(String idPadre) 
    {
        this.idPadre = idPadre;
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

    public String getVinculo() 
    {
        return vinculo;
    }

    public void setVinculo(String vinculo) 
    {
        this.vinculo = vinculo;
    }

    public List<Jugador> getJugadores() 
    {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) 
    {
        this.jugadores = jugadores;
    }

	public List<Galeria> getGaleriaPadre() 
	{
		return galeriaPadre;
	}

	public void setGaleriaPadre(List<Galeria> galeriaPadre) 
	{
		this.galeriaPadre = galeriaPadre;
	}

	public List<Equipo> getEquipos() 
	{
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos)
	{
		this.equipos = equipos;
	}

	public String getFcmToken() 
	{
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) 
	{
		this.fcmToken = fcmToken;
	}
}