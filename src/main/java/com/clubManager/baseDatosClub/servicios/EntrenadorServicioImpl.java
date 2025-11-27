package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.repositorios.EntrenadorRepositorio;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz {@link EntrenadorServicio}.
 * Proporciona la lógica de negocio para la gestión de entrenadores,
 * incluyendo validación de datos y persistencia.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class EntrenadorServicioImpl implements EntrenadorServicio {
	
	//Area de Datos

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public List<Entrenador> listarEntrenadores() 
    {
        return entrenadorRepositorio.findAll();
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Entrenador> buscarPorId(String idEntrenador) 
    {
        return entrenadorRepositorio.findById(idEntrenador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void crearEntrenador
    				(String idEntrenador, String nombre, String apellidos, String dni, String direccion,
                     String tipoUsuario, String password, String foto, String telefono, String email) 
    {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador(idEntrenador);
        entrenador.setNombre(nombre);
        entrenador.setApellidos(apellidos);
        entrenador.setDni(dni);
        entrenador.setDireccion(direccion);
        entrenador.setTipoUsuario(tipoUsuario);
        entrenador.setPassword(password);
        entrenador.setFoto(foto);
        entrenador.setTelefono(telefono);
        entrenador.setEmail(email);

        crearEntrenador(entrenador); 
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void crearEntrenador(Entrenador entrenador) 
    {
        if (entrenador == null) 
        {
            throw new IllegalArgumentException("El objeto entrenador no puede ser nulo.");
        }

        validarEntrenador(entrenador);

        if (entrenadorRepositorio.existsByDni(entrenador.getDni())) 
        {
            throw new IllegalArgumentException("Ya existe un entrenador con ese DNI.");
        }
        entrenadorRepositorio.save(entrenador);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    @Transactional
    public void modificarEntrenador
    				(String idEntrenador, String nombre, String apellidos, String dni, String direccion,
                     String tipoUsuario, String password, String foto, String telefono, String email) 
    {
        Entrenador actualizado = new Entrenador();
        actualizado.setNombre(nombre);
        actualizado.setApellidos(apellidos);
        actualizado.setDni(dni);
        actualizado.setDireccion(direccion);
        actualizado.setTipoUsuario(tipoUsuario);
        actualizado.setPassword(password);
        actualizado.setFoto(foto);
        actualizado.setTelefono(telefono);
        actualizado.setEmail(email);

        modificarEntrenador(actualizado);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    @Transactional
    public void modificarEntrenador(Entrenador entrenadorActualizado) 
    {
        if (entrenadorActualizado == null || entrenadorActualizado.getIdEntrenador() == null) 
        {
            throw new IllegalArgumentException("El objeto entrenador o su ID no pueden ser nulos.");
        }

        validarEntrenador(entrenadorActualizado);

        Entrenador existente = entrenadorRepositorio.findById(entrenadorActualizado.getIdEntrenador())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró entrenador con ID: " 
                + entrenadorActualizado.getIdEntrenador()));

        if (!existente.getDni().equals(entrenadorActualizado.getDni()) &&
            entrenadorRepositorio.existsByDni(entrenadorActualizado.getDni())) 
        {
            throw new IllegalArgumentException("Ya existe un entrenador con ese DNI.");
        }

        existente.setNombre(entrenadorActualizado.getNombre());
        existente.setApellidos(entrenadorActualizado.getApellidos());
        existente.setDni(entrenadorActualizado.getDni());
        existente.setDireccion(entrenadorActualizado.getDireccion());
        existente.setTipoUsuario(entrenadorActualizado.getTipoUsuario());
        existente.setPassword(entrenadorActualizado.getPassword());
        existente.setFoto(entrenadorActualizado.getFoto());
        existente.setTelefono(entrenadorActualizado.getTelefono());
        existente.setEmail(entrenadorActualizado.getEmail());
    }

    /** 
     * {@inheritDoc}
     */
    
    @Override
    public void eliminarEntrenador(String idEntrenador) 
    {
        entrenadorRepositorio.deleteById(idEntrenador);
    }

    /** 
     * {@inheritDoc} 
     * 
     */
    
    @Override
    public Optional<Entrenador> buscarPorDni(String dni) 
    {
        return entrenadorRepositorio.findByDni(dni);
    }

    /**
     * Valida los datos de un entrenador.
     * 
     * @param entrenador objeto a validar
     */
    
    private void validarEntrenador(Entrenador entrenador) 
    {
        if (entrenador.getIdEntrenador() == null || entrenador.getIdEntrenador().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El id es obligatorio.");
        }
        if (entrenador.getNombre() == null || entrenador.getNombre().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (entrenador.getApellidos() == null || entrenador.getApellidos().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        }
        if (entrenador.getDni() == null || !DNI_PATTERN.matcher(entrenador.getDni()).matches()) 
        {
            throw new IllegalArgumentException("El DNI no es válido. Debe tener 8 números seguidos de una letra.");
        }
        if (entrenador.getDireccion() == null || entrenador.getDireccion().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (entrenador.getTipoUsuario() == null || entrenador.getTipoUsuario().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio.");
        }
        if (entrenador.getPassword() == null || entrenador.getPassword().length() < 6) 
        {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (entrenador.getTelefono() == null || !TELEFONO_PATTERN.matcher(entrenador.getTelefono()).matches()) 
        {
            throw new IllegalArgumentException("El teléfono no es válido.");
        }
        if (entrenador.getEmail() == null || !EMAIL_PATTERN.matcher(entrenador.getEmail()).matches()) 
        {
            throw new IllegalArgumentException("El email no es válido.");
        }
    }
}