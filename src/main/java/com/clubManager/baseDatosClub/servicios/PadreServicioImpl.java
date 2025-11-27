package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.PadreRelacionDTO;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.PadreRepositorio;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementación de la interfaz {@link PadreServicio} que gestiona la lógica de negocio
 * relacionada con la entidad {@link Padre}.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class PadreServicioImpl implements PadreServicio {
	
	//Area de datos

    @Autowired
    private PadreRepositorio padreRepositorio;
    
    @Autowired
    private EquipoRepositorio equipoRepositorio;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern VINCULO_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
       
    //Métodos principales

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Optional<Padre> buscarPorId(String idPadre) 
    {
        return padreRepositorio.findById(idPadre);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void crearPadre(String idPadre, String nombre, String apellidos, String dni, String direccion, String tipoUsuario,
                           String password, String foto, String telefono, String email, String vinculo) 
    {
        validarDatosPadre(idPadre, nombre, apellidos, dni, direccion, tipoUsuario, password, telefono, email, vinculo);

        if (padreRepositorio.findByDni(dni).isPresent()) 
        {
            throw new IllegalArgumentException("Ya existe un padre con ese DNI.");
        }

        Padre padre = new Padre();
        padre.setIdPadre(idPadre);
        padre.setNombre(nombre);
        padre.setApellidos(apellidos);
        padre.setDni(dni);
        padre.setDireccion(direccion);
        padre.setTipoUsuario(tipoUsuario);
        padre.setPassword(password);
        padre.setFoto(foto);
        padre.setTelefono(telefono);
        padre.setEmail(email);
        padre.setVinculo(vinculo);
        padreRepositorio.save(padre);
    }

    /**
     * {@inheritDoc} 
     */
    
    @Override
    public void modificarPadre(String idPadre, String nombre, String apellidos, String dni, String direccion,
                               String tipoUsuario, String password, String foto, 
                               String telefono, String email, String vinculo) 
    {
        Optional<Padre> optionalPadre = padreRepositorio.findById(idPadre);
        if (optionalPadre.isEmpty()) 
        {
            throw new IllegalArgumentException("No se encontró el padre con el id: " + idPadre);
        }
        validarDatosPadre(idPadre, nombre, apellidos, dni, direccion, tipoUsuario, password, telefono, email, vinculo);

        Padre padre = optionalPadre.get();
        padre.setNombre(nombre);
        padre.setApellidos(apellidos);
        padre.setDni(dni);
        padre.setDireccion(direccion);
        padre.setTipoUsuario(tipoUsuario);
        padre.setPassword(password);
        padre.setFoto(foto);
        padre.setTelefono(telefono);
        padre.setEmail(email);
        padre.setVinculo(vinculo);

        padreRepositorio.save(padre);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void eliminarPadre(String idPadre) 
    {
        padreRepositorio.deleteById(idPadre);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Optional<Padre> buscarPorDni(String dni) 
    {
        return padreRepositorio.findByDni(dni); 
    }

    /** 
     * {@inheritDoc}
     */
    
    @Override
    public void crearPadre(Padre padre) 
    {
        if (padre == null) 
        {
            throw new IllegalArgumentException("El objeto padre no puede ser nulo.");
        }

        crearPadre(
            padre.getIdPadre(),
            padre.getNombre(),
            padre.getApellidos(),
            padre.getDni(),
            padre.getDireccion(),
            padre.getTipoUsuario(),
            padre.getPassword(),
            padre.getFoto(),
            padre.getTelefono(),
            padre.getEmail(),
            padre.getVinculo()
        );
    }
    
    /** 
     * {@inheritDoc}
     */
    
    @Override
    public void modificarPadre(Padre padre) 
    {
        if (padre == null) 
        {
            throw new IllegalArgumentException("El objeto padre no puede ser nulo.");
        }

        modificarPadre
        (
            padre.getIdPadre(),
            padre.getNombre(),
            padre.getApellidos(),
            padre.getDni(),
            padre.getDireccion(),
            padre.getTipoUsuario(),
            padre.getPassword(),
            padre.getFoto(),
            padre.getTelefono(),
            padre.getEmail(),
            padre.getVinculo()
        );
    }
    
    /** 
     * {@inheritDoc}
     */
    
    @Override
    public List<PadreRelacionDTO> listarPadresPorEquipo(String idEquipo) 
    {
        if (idEquipo == null || idEquipo.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El identificador del equipo no puede estar vacío");
        }

        boolean existeEquipo = equipoRepositorio.existsById(idEquipo);
        if (!existeEquipo) 
        {
            throw new EntityNotFoundException("No se encontró el equipo con ID: " + idEquipo);
        }

        List<Padre> padres;
        try 
        {
            padres = padreRepositorio.buscarPadresPorEquipo(idEquipo);
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException("Error al buscar padres del equipo: " + idEquipo);
        }

        if (padres.isEmpty()) 
        {
        	throw new IllegalArgumentException
        	(
        	        "No se encontraron padres"
        	);
        }

        List<PadreRelacionDTO> resultado = padres.stream()
            .map(p -> new PadreRelacionDTO(p.getIdPadre(), p.getNombre() , p.getApellidos()))
            .toList();

        return resultado;
    }
    
    /**
     * 
     * Realiza validaciones sobre los datos introducidos para crear o modificar un padre.
     * Se validan campos obligatorios y formatos de DNI, teléfono, email y vínculo
     * 
     */
    
    private void validarDatosPadre(String idPadre, String nombre, String apellidos, String dni, String direccion, 
    		String tipoUsuario, String password, String telefono, String email, String vinculo) 
    {
    	if(idPadre == null || idPadre.trim().isEmpty())
    	{
    		throw new IllegalArgumentException("El id es obligatorio.");
    	}
        if (nombre == null || nombre.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        }
        if (dni == null || !DNI_PATTERN.matcher(dni).matches()) 
        {
            throw new IllegalArgumentException("El DNI no es válido. Debe tener 8 números seguidos de una letra.");
        }
        if (direccion == null || direccion.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio.");
        }
        if (password == null || password.length() < 6) 
        {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        if (telefono == null || !TELEFONO_PATTERN.matcher(telefono).matches()) 
        {
            throw new IllegalArgumentException("El teléfono no es válido.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) 
        {
            throw new IllegalArgumentException("El email no es válido.");
        }
        if (vinculo == null || !VINCULO_PATTERN.matcher(vinculo).matches()) 
        {
            throw new IllegalArgumentException("El vínculo no es válido.");
        }
    }
}