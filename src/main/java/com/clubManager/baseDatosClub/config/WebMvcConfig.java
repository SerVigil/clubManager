package com.clubManager.baseDatosClub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de Spring MVC para que la carpeta de subida de archivos
 * como recursos sea accesible.
 * 
 *@author Sergio Vigil Soto
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	//Area de datos

    @Value("${clubmanager.upload.dir}")
    private String uploadDir;
    
    //Métodos principales

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) 
    {

        
        String path = uploadDir.replace("\\", "/");
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + path);
    }
}