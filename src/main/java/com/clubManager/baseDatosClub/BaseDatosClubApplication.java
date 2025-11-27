package com.clubManager.baseDatosClub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.clubManager.baseDatosClub.config.FileStorageProperties;

/**
 * Clase principal de la aplicación ClubManager.
 * Inicia la aplicación Spring Boot que gestiona la base de datos
 * del sistema del club deportivo.
 * 
 * Esta clase sirve como punto de entrada para ejecutar la aplicación
 * utilizando {@code SpringApplication.run}.
 * 
 * @author Sergio Vigil Soto
 */

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class BaseDatosClubApplication {

	/**
     * Método principal que lanza la aplicación.
     * 
     * @param args argumentos pasados por línea de comandos (opcional)
     */
	
	public static void main(String[] args) 
	{
		SpringApplication.run(BaseDatosClubApplication.class, args);
	}
}