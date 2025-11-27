package com.clubManager.baseDatosClub.seguridad;

import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio encargado de generar, validar y extraer información de tokens JWT.
 *
 * @author Sergio Vigil Soto
 */

@Service
public class TokenService {
	
	//Area de datos

    private final Key key = Keys.hmacShaKeyFor("miClaveSecreta12345678901234567890123456789012".getBytes());

    /**
     * Genera un token JWT con ID de usuario y tipo.
     *
     * @param idUsuario identificador del usuario
     * @param tipoUsuario tipo de usuario.
     * @return token JWT firmado
     */
    
    public String generarToken(String idUsuario, String tipoUsuario) 
    {
        String token = Jwts.builder()
                .setSubject(idUsuario)
                .claim("tipoUsuario", tipoUsuario)
                .setIssuedAt(new Date())
                // No se establece expiración
                .signWith(key)
                .compact();
        return token;
    }

    /**
     * Valida si el token es correcto y pertenece al usuario.
     *
     * @param token token JWT
     * @param idUsuario identificador del usuario
     * @return true si es válido
     */
    
    public boolean validarToken(String token, String idUsuario) {
        try 
        {
            String subject = extraerIdUsuario(token);
            boolean valido = subject.equals(idUsuario);
            return valido;
        } 
        catch (Exception e) 
        {
        	 return false;
        }
    }

    /**
     * Extrae el ID del usuario del token.
     */
    
    public String extraerIdUsuario(String token) 
    {
        try 
        {
            String id = extraerClaims(token).getSubject();
            return id;
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }

    /**
     * Extrae el tipo de usuario del token.
     */
    
    public String extraerTipoUsuario(String token) 
    {
        try 
        {
            String tipo = (String) extraerClaims(token).get("tipoUsuario");
            return tipo;
        } 
        catch (Exception e)
        {
            throw e;
        }
    }

    

    /**
     * Extrae los claims del token, lanzando una excepción si no es válido.
     */
    
    private Claims extraerClaims(String token) 
    {
        try
        {
            String sanitized = token.replaceAll("\\s", "");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(sanitized)
                    .getBody();
            return claims;
        } 
        catch (Exception e) 
        {
            throw e;
        }
    }
}