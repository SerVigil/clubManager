package com.clubManager.baseDatosClub.servicios;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Galeria;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.GaleriaRepositorio;

import org.springframework.http.HttpStatus;

/**
 * Implementación del servicio {@link GaleriaServicio}.
 * 
 * Gestiona la creación, consulta, modificación y eliminación de documentos en la galería,
 * asegurando que cada documento esté vinculado a un equipo.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class GaleriaServicioImpl implements GaleriaServicio {
	
	//Area de Datos

    @Autowired
    private GaleriaRepositorio galeriaRepositorio;

    @Autowired
    private EquipoRepositorio equipoRepositorio;
    
    //Métodos Principales

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public List<Galeria> listarGaleriaPorEquipo(String idEquipo) 
    {
        return galeriaRepositorio.findByEquipo_IdEquipo(idEquipo);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public Optional<Galeria> buscarPorId(Long idDocumento) 
    {
        return galeriaRepositorio.findById(idDocumento);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void crearDocumento(String tipo, String autor, LocalDate fecha, String url,
            String nombreDocumento, String idEquipo) 
    		{
    			Equipo equipo = equipoRepositorio.findById(idEquipo)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo no encontrado"));

    			Galeria galeria = new Galeria();
    			galeria.setTipo(tipo);
    			galeria.setAutor(autor);
    			galeria.setFecha(fecha);
    			galeria.setUrl(url);
    			galeria.setNombreDocumento(nombreDocumento);
    			galeria.setEquipo(equipo);

    			galeriaRepositorio.save(galeria);
    		}

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void modificarDocumento(Long id, String tipo, String autor, LocalDate fecha, String url, 
    		String nombreDocumento) 
    {
        Galeria galeria = galeriaRepositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento no encontrado"));

        galeria.setTipo(tipo);
        galeria.setAutor(autor);
        galeria.setFecha(fecha);
        galeria.setUrl(url);
        galeria.setNombreDocumento(nombreDocumento);

        galeriaRepositorio.save(galeria);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void eliminarDocumento(Long id) {
        if (!galeriaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento no encontrado");
        }
        galeriaRepositorio.deleteById(id);
    }

    /** 
     * {@inheritDoc} 
     */
    
    @Override
    public void eliminarGaleriaPorEquipo(Long idEquipo) 
    {
        galeriaRepositorio.deleteByEquipo_IdEquipo(String.valueOf(idEquipo));
    }  
}