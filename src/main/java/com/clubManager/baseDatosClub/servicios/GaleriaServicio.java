package com.clubManager.baseDatosClub.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.clubManager.baseDatosClub.entidades.Galeria;

/**
 * Servicio para la gestión de documentos en la galería del club.
 * 
 * Permite crear, consultar, modificar y eliminar documentos asociados a equipos.
 * Cada documento representa una imagen, vídeo, PDF u otro archivo vinculado a un equipo específico.
 * 
 * @author Sergio Vigil Soto
 */

public interface GaleriaServicio {

    /**
     * Lista todos los documentos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de documentos vinculados al equipo
     */
	
    List<Galeria> listarGaleriaPorEquipo(String idEquipo);

    /**
     * Busca un documento específico de la galería por su ID.
     * 
     * @param id identificador único del documento
     * @return un Optional que puede contener el documento si existe
     */
    
    Optional<Galeria> buscarPorId(Long idDocumento);

    /**
     * Crea un nuevo documento en la galería, asociado a un equipo.
     * 
     * @param tipo tipo de documento (ej. imagen, vídeo, PDF)
     * @param autor autor o creador del documento
     * @param fecha fecha de creación o subida
     * @param url ruta o identificador del archivo
     * @param nombreDocumento nombre del documento
     * @param idEquipo identificador del equipo al que se asocia el documento
     */
    
    void crearDocumento(String tipo, String autor, LocalDate fecha, String url, String nombreDocumento, String idEquipo);

    /**
     * Modifica los datos de un documento existente en la galería.
     * 
     * @param id identificador del documento a modificar
     * @param tipo nuevo tipo de documento
     * @param autor nuevo autor
     * @param fecha nueva fecha
     * @param url nuevo contenido o ruta del archivo
     * @param nombreDocumento nuevo nombre del documento
     */
    
    void modificarDocumento(Long id, String tipo, String autor, LocalDate fecha, String url, String nombreDocumento);

    /**
     * Elimina un documento específico de la galería por su ID.
     * 
     * @param id identificador del documento a eliminar
     */
    
    void eliminarDocumento(Long id);

    /**
     * Elimina todos los documentos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo cuya galería se desea eliminar
     */
    
    void eliminarGaleriaPorEquipo(Long idEquipo);
}