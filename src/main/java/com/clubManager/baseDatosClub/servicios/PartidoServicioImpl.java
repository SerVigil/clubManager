package com.clubManager.baseDatosClub.servicios;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.dto.PartidoDTO;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.EquipoPartido;
import com.clubManager.baseDatosClub.entidades.EquipoPartidoPK;
import com.clubManager.baseDatosClub.entidades.Partido;
import com.clubManager.baseDatosClub.repositorios.EquipoPartidoRepositorio;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.PartidoRepositorio;

import jakarta.transaction.Transactional;

/**
 * Implementación de la interfaz {@link PartidoServicio} que gestiona la lógica de negocio
 * relacionada con la entidad {@link Partido}.
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class PartidoServicioImpl implements PartidoServicio {
	
	//Area de datos
	
	private static final Logger log = LoggerFactory.getLogger(PartidoServicioImpl.class);

    @Autowired
    private PartidoRepositorio partidoRepo;

    @Autowired
    private EquipoRepositorio equipoRepo;

    @Autowired
    private EquipoPartidoRepositorio equipoPartidoRepo;

    //Métodos Principales
    
    /** 
     * {@inheritDoc}
     */
    
    @Override
    public Optional<Partido> buscarPorIdPartidoYEquipos_IdEquipo(Long idPartido, String idEquipo)
    {
        return partidoRepo.findByIdPartidoAndEquipo_IdEquipo(idPartido, idEquipo);
    }
    
    /** 
     * {@inheritDoc}
     */

    @Override
    public Partido modificarPartido(PartidoDTO dto) 
    {
        Long id = dto.getIdPartido();
        if (id != null && partidoRepo.existsById(id)) {
         
            Partido partido = partidoRepo.findById(id).orElseThrow();

            partido.setFecha(dto.getFecha());
            partido.setLugar(dto.getLugar());
            partido.setTipoPartido(dto.getTipoPartido());
            partido.setLocal(dto.getLocal());
            partido.setVisitante(dto.getVisitante());

            Partido partidoModificado = partidoRepo.save(partido);

            if (dto.getIdEquipo() != null && !dto.getIdEquipo().isBlank()) {
                Equipo equipo = equipoRepo.findById(dto.getIdEquipo()).orElse(null);
                if (equipo != null) {
                    EquipoPartidoPK pk = new EquipoPartidoPK(dto.getIdEquipo(), partidoModificado.getIdPartido());
                    EquipoPartido relacion = new EquipoPartido();
                    relacion.setId(pk);
                    relacion.setEquipo(equipo);
                    relacion.setPartido(partidoModificado);
                    equipoPartidoRepo.save(relacion);
                } 
            }
            return partidoModificado;
        } 
        else 
        {
            return null;
        }
    }
    
    /** 
     * {@inheritDoc}
     */

    @Override
    public void eliminarPartido(Long idPartido) 
    {
        if (partidoRepo.existsById(idPartido)) 
        {
            partidoRepo.deleteById(idPartido);
        } 
    }
    
    /** 
     * {@inheritDoc}
     */

    @Override
    @Transactional
    public void eliminarPartidosPorEquipo(String idEquipo) {
        log.info(">>> INICIO eliminarPartidosPorEquipo(idEquipo={})", idEquipo);

        if (idEquipo == null || idEquipo.isEmpty()) {
            log.error("ERROR: idEquipo nulo o vacío");
            throw new IllegalArgumentException("El ID del equipo no puede ser nulo o vacío");
        }

        log.debug(">>> Verificando existencia de partidos para el equipo {}", idEquipo);
        boolean existenPartidos = partidoRepo.existsByEquipo_IdEquipo(idEquipo);
        log.debug(">>> Resultado existencia: {}", existenPartidos);

        if (!existenPartidos) {
            log.warn("No existen partidos asociados al equipo con ID '{}'", idEquipo);
            throw new IllegalArgumentException("No existen partidos asociados al equipo con ID '" + idEquipo + "'");
        }
        equipoPartidoRepo.deleteByPartido_Equipo_IdEquipo(idEquipo);
        log.info(">>> Eliminando partidos asociados al equipo {}", idEquipo);
        partidoRepo.deleteByEquipo_IdEquipo(idEquipo);
        log.info(">>> Eliminación completada para equipo {}", idEquipo);

        log.info(">>> FIN eliminarPartidosPorEquipo(idEquipo={})", idEquipo);
    }
    
    /** 
     * {@inheritDoc}
     */

    @Override
    @Transactional
    public PartidoDTO crearPartido(PartidoDTO dto) {

        // 1️⃣ Obtener el equipo
        Equipo equipo = equipoRepo.findById(dto.getIdEquipo())
                .orElseThrow(() -> 
                    new IllegalArgumentException("Equipo no encontrado: " + dto.getIdEquipo())
                );

        // 2️⃣ Crear el partido y asignarle el equipo
        Partido partido = new Partido();
        partido.setFecha(dto.getFecha());
        partido.setLugar(dto.getLugar());
        partido.setTipoPartido(dto.getTipoPartido());
        partido.setLocal(dto.getLocal());
        partido.setVisitante(dto.getVisitante());
        partido.setEquipo(equipo);  
        
     


        // 3️⃣ Guardar el partido
        partidoRepo.save(partido);
        
    
        EquipoPartidoPK pk = new EquipoPartidoPK(equipo.getIdEquipo(), partido.getIdPartido());
        EquipoPartido equipoPartido = new EquipoPartido();
        equipoPartido.setId(pk);
        equipoPartido.setEquipo(equipo);
        equipoPartido.setPartido(partido);
        equipoPartidoRepo.save(equipoPartido);

        PartidoDTO respuesta = new PartidoDTO(
                partido.getIdPartido(),
                equipo.getIdEquipo(),  // obtenemos el id del equipo desde la relación
                partido.getLocal(),
                partido.getVisitante(),
                partido.getFecha(),
                partido.getTipoPartido(),
                partido.getLugar()
        );

        return respuesta;
    }

}