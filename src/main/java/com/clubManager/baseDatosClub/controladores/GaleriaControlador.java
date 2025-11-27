package com.clubManager.baseDatosClub.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.clubManager.baseDatosClub.entidades.Galeria;
import com.clubManager.baseDatosClub.servicios.FileStorageService;
import com.clubManager.baseDatosClub.servicios.GaleriaServicio;

/**
 * Controlador REST para gestionar operaciones sobre la entidad Galeria.
 * 
 * Cada documento está vinculado a un equipo mediante su identificador.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api/galeria")
public class GaleriaControlador {
	
	//Area de datos

    @Autowired
    private GaleriaServicio galeriaServicio;
    
    @Autowired
    private FileStorageService fileStorageService;  

    /**
     * Lista todos los documentos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo
     * @return lista de documentos vinculados al equipo
     */
    
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<Galeria>> listarGaleriaPorEquipo(@PathVariable String idEquipo) 
    {
        List<Galeria> lista = galeriaServicio.listarGaleriaPorEquipo(idEquipo);
        if (lista == null || lista.isEmpty()) 
        {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca un documento específico en la galería por su identificador único.
     * 
     * @param id Identificador del documento a buscar.
     * @return Optional con el documento encontrado o vacío si no existe.
     */
    
    @GetMapping("/{idDocumento}")
    public Optional<Galeria> buscarPorId(@PathVariable Long idDocumento) 
    {
        return galeriaServicio.buscarPorId(idDocumento);
    }
    
    @PostMapping("/{idEquipo}/subir")
    public ResponseEntity<?> subirFoto
    		(
            @PathVariable String idEquipo,
            @RequestParam("file") MultipartFile file,
            @RequestParam String tipo,
            @RequestParam String autor,
            @RequestParam String nombreDocumento
            ) 
    {
        try 
        {
            String url = fileStorageService.storeFile(file, "fotosJugadores"); 

            LocalDate fecha = LocalDate.now();
            galeriaServicio.crearDocumento(tipo, autor, fecha, url, nombreDocumento, idEquipo);

            return ResponseEntity.ok(url);
        } 
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Error al subir la imagen: " + e.getMessage());
        }
    }
    
    /**
     * Creamos un nuevo documento en la galería con los datos proporcionados.
     * 
     * @param tipo            Tipo del documento.
     * @param autor           Autor del documento.
     * @param fecha           Fecha.
     * @param url         	  Ruta del archivo.
     * @param nombreDocumento Nombre identificativo del documento.
     * @param idEquipo        Identificador del equipo al que se asocia el documento.
     */
    
    @PostMapping
    public void crearDocumento
        (
        @RequestParam String tipo,
        @RequestParam String autor,
        @RequestParam String fecha,
        @RequestParam String url,
        @RequestParam String nombreDocumento,
        @RequestParam String idEquipo
        ) 
    {
        LocalDate fechaParsed = LocalDate.parse(fecha);
        galeriaServicio.crearDocumento(tipo, autor, fechaParsed, url, nombreDocumento, idEquipo);
    }

    /**
     * Modificamos un documento existente identificado por su id con los nuevos datos.
     * 
     * @param id              Identificador del documento a modificar.
     * @param tipo            Nuevo tipo del documento.
     * @param autor           Nuevo autor del documento.
     * @param fecha           Nueva fecha en formato String.
     * @param url         	  Nueva ruta del archivo.
     * @param nombreDocumento Nuevo nombre identificativo del documento.
     */
    
    @PutMapping("/{idDocumento}")
    public void modificarDocumento(
        @PathVariable Long idDocumento,
        @RequestParam String tipo,
        @RequestParam String autor,
        @RequestParam String fecha,
        @RequestParam String url,
        @RequestParam String nombreDocumento) 
    {
        LocalDate fechaParsed = LocalDate.parse(fecha);
        galeriaServicio.modificarDocumento(idDocumento, tipo, autor, fechaParsed, url, nombreDocumento);
    }

    /**
     * Elimina un documento de la galería identificado por su id.
     * 
     * @param idDocumento Identificador del documento a eliminar.
     */
    
    @DeleteMapping("/{idDocumento}")
    public void eliminarDocumento(@PathVariable Long idDocumento) 
    {
        galeriaServicio.eliminarDocumento(idDocumento);
    }

    /**
     * Elimina todos los documentos asociados a un equipo específico.
     * 
     * @param idEquipo identificador del equipo cuya galería se desea eliminar
     */
    
    @DeleteMapping("/equipo/{idEquipo}")
    public void eliminarGaleriaPorEquipo(@PathVariable Long idEquipo) 
    {
        galeriaServicio.eliminarGaleriaPorEquipo(idEquipo);
    }
}