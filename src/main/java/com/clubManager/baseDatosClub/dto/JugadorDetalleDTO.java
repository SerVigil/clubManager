package com.clubManager.baseDatosClub.dto;

import java.time.LocalDate;

/**
 * DTO que representa alguno de los datos de un jugador.
 * 
 * @author Sergio Vigil Soto
 */

public class JugadorDetalleDTO {
	
	//Area de Datos
	
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private Integer dorsal;
    private String posicion;
    private LocalDate fechaNacimiento;
    
    //Constructor
    
	public JugadorDetalleDTO(String nombre, String apellidos, String dni, String direccion, String telefono,
			String email, Integer dorsal, String posicion, LocalDate fechaNacimiento)
	{
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.dorsal = dorsal;
		this.posicion = posicion;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	//Métodos Getter y Setter

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

	public Integer getDorsal() 
	{
		return dorsal;
	}

	public void setDorsal(Integer dorsal) 
	{
		this.dorsal = dorsal;
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
}