package com.clubManager.baseDatosClub.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * Servicio encargado del almacenamiento de archivos en el servidor.
 * Permite guardar archivos en subcarpetas y generar rutas públicas para su acceso.
 */

@Service
public class FileStorageService {

    private final Path localizacion;

    /**
     * Constructor que inicializa la carpeta raíz del almacenamiento.
     * @param uploadDir Ruta base definida en application.properties.
     */
    
    public FileStorageService(@Value("${clubmanager.upload.dir}") String directorioSubida) 
    {
        this.localizacion = Paths.get(directorioSubida).toAbsolutePath().normalize();

        try 
        {
            Files.createDirectories(this.localizacion);
        } 
        catch (IOException ex) 
        {
            throw new RuntimeException("No se pudo crear el directorio de almacenamiento", ex);
        }
    }

    /**
     * Guarda un archivo dentro de una subcarpeta.
     * @param file Archivo a almacenar.
     * @param subcarpeta Subcarpeta donde se guardará el archivo.
     * @return Ruta relativa que puede usarse como URL pública del archivo.
     */
    
    public String storeFile(MultipartFile file, String subCarpeta) 
    {
        if (file.isEmpty()) 
        {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        String nombreOriginal = StringUtils.cleanPath(file.getOriginalFilename());
        
        if (nombreOriginal.contains("..")) 
        {
            throw new IllegalArgumentException("Nombre de archivo inválido");
        }

        String extension = "";
        int dot = nombreOriginal.lastIndexOf(".");
        if (dot != -1) 
        {
            extension = nombreOriginal.substring(dot);
        }

        String nombreArchivo = UUID.randomUUID().toString() + extension;

        try 
        {
            Path targetFolder = localizacion.resolve(subCarpeta);
            Files.createDirectories(targetFolder);

            Path targetFile = targetFolder.resolve(nombreArchivo);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subCarpeta + "/" + nombreArchivo;
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
}