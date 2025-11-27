package com.clubManager.baseDatosClub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades de configuración para la ubicación de almacenamiento de archivos.
 * 
 * @author Sergio Vigil Soto
 */

@ConfigurationProperties(prefix = "clubmanager.upload")
public class FileStorageProperties {
	
	//Area de datos

    private String directorio;
    
    //Métodos getter y setter

    public String getDir() 
    {
        return directorio;
    }

    public void setDir(String directorio) 
    {
        this.directorio = directorio;
    }
}