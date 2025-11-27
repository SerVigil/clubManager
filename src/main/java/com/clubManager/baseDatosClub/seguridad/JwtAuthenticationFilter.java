package com.clubManager.baseDatosClub.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de Spring Security que intercepta cada petición HTTP para validar el token JWT.
 * 
 * @author Sergio Vigil Soto
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	//Area de datos

    private final TokenService tokenService;
    
    //Constructor

    public JwtAuthenticationFilter(TokenService tokenService) 
    {
        this.tokenService = tokenService;
    }
    
    //Métodos principales

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String token = authHeader.replaceFirst("(?i)^bearer", "").trim();
            token = token.replaceAll("\\s", "");

            try {
                String idUsuario = tokenService.extraerIdUsuario(token);
                String tipoUsuario = tokenService.extraerTipoUsuario(token);
                boolean tokenValido = tokenService.validarToken(token, idUsuario);

                if (idUsuario != null && tipoUsuario != null && tokenValido) 
                {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        String rolSpring = tipoUsuario.startsWith("ROLE_") ? tipoUsuario : "ROLE_" + 
                            tipoUsuario.toUpperCase();

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
                        		(
                                idUsuario,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority(rolSpring))
                        		);

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } 
            catch (Exception e) 
            {
                e.getMessage();
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) 
    {
        String path = request.getServletPath();
        return path.equals("/api/login")
                || path.equals("/api/entrenadores/registrar")
                || path.equals("/api/jugadores/registrar")
                || path.equals("/api/padres/registrar");
    }
}