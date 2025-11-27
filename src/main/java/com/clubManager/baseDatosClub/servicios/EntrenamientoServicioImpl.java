package com.clubManager.baseDatosClub.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.EntrenamientoDTO;
import com.clubManager.baseDatosClub.entidades.Entrenamiento;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;
import com.clubManager.baseDatosClub.repositorios.EntrenamientoRepositorio;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.ModeloEntrenamientoRepositorio;

@Service
public class EntrenamientoServicioImpl implements EntrenamientoServicio {
	
	//Area de datos

    @Autowired
    private EntrenamientoRepositorio entrenamientoRepo;

    @Autowired
    private EquipoRepositorio equipoRepo;

    @Autowired
    private ModeloEntrenamientoRepositorio modeloRepo;

    //Métodos principales
    
    /**
     * {@inheritDoc}
     */
    
    @Override
    public Entrenamiento crearEntrenamiento(EntrenamientoDTO dto) {


        Entrenamiento entrenamiento = new Entrenamiento();

        entrenamiento.setFecha(dto.getFecha());
        entrenamiento.setHora(dto.getHora());
        entrenamiento.setTipo(dto.getTipo());
        entrenamiento.setObservaciones(dto.getObservaciones());

        if (dto.getIdEquipo() != null) 
        {
            Equipo equipo = equipoRepo.findById(dto.getIdEquipo())
                    .orElseThrow(() -> 
                    {
                        return new IllegalArgumentException("Equipo no encontrado");
                    });
            entrenamiento.setEquipo(equipo);
        } 
        else 
        {
            throw new IllegalArgumentException("El id del equipo no puede ser nulo");
        }

        if (dto.getIdModelo() != null) 
        {
            ModeloEntrenamiento modelo = modeloRepo.findByIdModelo(dto.getIdModelo())
                    .orElseThrow(() -> 
                    {
                        return new IllegalArgumentException("Modelo no encontrado");
                    });
            entrenamiento.setModeloEntrenamiento(modelo);
        } 
        else 
        {
            throw new IllegalArgumentException("El id del modelo no puede ser nulo");
        }

        Entrenamiento guardado = entrenamientoRepo.save(entrenamiento);
        return guardado;
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Optional<EntrenamientoDTO> buscarPorIdYEquipo(Long idEntrenamiento, String idEquipo) {

        Optional<Entrenamiento> resultado =
                entrenamientoRepo.findByIdEntrenamientoAndEquipo_IdEquipo(idEntrenamiento, idEquipo);

        if (resultado.isEmpty()) 
        {
            return Optional.empty();
        }

        Entrenamiento e = resultado.get();
        EntrenamientoDTO dto = new EntrenamientoDTO
        		(
                e.getIdEntrenamiento(),
                e.getFecha(),
                e.getHora(),
                e.getTipo(),
                e.getObservaciones(),
                e.getEquipo() != null ? e.getEquipo().getIdEquipo() : null,
                e.getModeloEntrenamiento() != null ? e.getModeloEntrenamiento().getIdModelo() : null,
                e.getModeloEntrenamiento() != null ? e.getModeloEntrenamiento().getNombre() : null
        		);

        return Optional.of(dto);
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public Entrenamiento modificarEntrenamiento(EntrenamientoDTO dto) {


        if (dto.getIdEntrenamiento() == null || dto.getIdEquipo() == null) 
        {
            throw new IllegalArgumentException("El id del entrenamiento y del equipo son obligatorios");
        }

        Entrenamiento entrenamiento = entrenamientoRepo
                .findByIdEntrenamientoAndEquipo_IdEquipo(dto.getIdEntrenamiento(), dto.getIdEquipo())
                .orElseThrow(() -> 
                {
                    return new IllegalArgumentException("Entrenamiento no encontrado para el equipo");
                });

        entrenamiento.setFecha(dto.getFecha());
        entrenamiento.setHora(dto.getHora());
        entrenamiento.setTipo(dto.getTipo());
        entrenamiento.setObservaciones(dto.getObservaciones());

        if (dto.getIdModelo() != null) 
        {
            ModeloEntrenamiento modelo = modeloRepo.findById(dto.getIdModelo())
                    .orElseThrow(() -> 
                    {
                        return new IllegalArgumentException("Modelo de entrenamiento no encontrado");
                    });
            entrenamiento.setModeloEntrenamiento(modelo);
        } 
        else 
        {
            entrenamiento.setModeloEntrenamiento(null);
        }

        Entrenamiento guardado = entrenamientoRepo.save(entrenamiento);
        return guardado;
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public void eliminarEntrenamientoPorIdYEquipo(Long idEntrenamiento, String idEquipo) {

        if (!entrenamientoRepo.existsByIdEntrenamientoAndEquipo_IdEquipo(idEntrenamiento, idEquipo)) 
        {
            throw new IllegalArgumentException("El entrenamiento no existe para este equipo");
        }

        entrenamientoRepo.deleteByIdEntrenamientoAndEquipo_IdEquipo(idEntrenamiento, idEquipo);
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public List<Entrenamiento> listarPorEquipo(String idEquipo) 
    {
        List<Entrenamiento> lista = entrenamientoRepo.findByEquipo_IdEquipo(idEquipo);
        return lista;
    }
    
    /**
     * {@inheritDoc}
     */

    @Override
    public void eliminarTodosPorEquipo(String idEquipo) 
    {
        List<Entrenamiento> entrenamientos = entrenamientoRepo.findByEquipo_IdEquipo(idEquipo);

        if (!entrenamientos.isEmpty()) 
        {
            entrenamientoRepo.deleteAll(entrenamientos);
        }
    }
}
