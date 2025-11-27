package com.clubManager.baseDatosClub.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de Spring Security.
 * 
 * Esta clase asegura que solo los usuarios autenticados puedan acceder a los endpoints
 * protegidos, mientras que los endpoints de login y registro permanecen accesibles.
 * 
 * @author Sergio Vigil Soto
 */


@Configuration
public class SecurityConfig {
	
	//Area de datos

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    //Constructor

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) 
    {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 🔥 clave
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login",
                                 "/api/entrenadores/registrar",
                                 "/api/jugadores/registrar",
                                 "/api/padres/registrar",
                                 "/uploads/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}