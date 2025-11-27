package com.clubManager.baseDatosClub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing) para la aplicación.
 * 
 * Esta clase permite que el backend sea accesible desde otros orígenes
 * (como la app Android) habilitando peticiones HTTP desde dominios externos.
 * Se definen los métodos permitidos y los headers aceptados.
 *
 * @author Sergio Vigil Soto
 */

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*") // Puedes restringirlo a una IP concreta si quieres
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
}