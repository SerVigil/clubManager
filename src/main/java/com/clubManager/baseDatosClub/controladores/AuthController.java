package com.clubManager.baseDatosClub.controladores;

import com.clubManager.baseDatosClub.dto.LoginRequest;
import com.clubManager.baseDatosClub.dto.LoginResponse;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.repositorios.EntrenadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.PadreRepositorio;
import com.clubManager.baseDatosClub.seguridad.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de gestionar el inicio de sesión de usuarios.
 * Verifica credenciales y genera un token JWT si son válidas.
 * 
 * @author Sergio Vigil Soto
 */

@RestController
@RequestMapping("/api")
public class AuthController {
	
	//Area de datos

    private final EntrenadorRepositorio entrenadorRepo;
    private final JugadorRepositorio jugadorRepo;
    private final PadreRepositorio padreRepo;
    private final TokenService tokenService;
    
    //Constructor

    public AuthController
    				(
    			EntrenadorRepositorio entrenadorRepo,
                          JugadorRepositorio jugadorRepo,
                          PadreRepositorio padreRepo,
                          TokenService tokenService
    				) 
    {
        this.entrenadorRepo = entrenadorRepo;
        this.jugadorRepo = jugadorRepo;
        this.padreRepo = padreRepo;
        this.tokenService = tokenService;
    }
    
    /**
     * Maneja la autenticación de usuarios mediante un identificador y contraseña.
     * 
     * Se diferencia entre los tres tipos de usuarios del sistema: entrenador, jugador y padre.  
     * Si las credenciales son correctas, genera un token JWT y devuelve información
     * del usuario junto con el token. Para jugadores también incluye el ID del equipo.
     *
     * @param request Objeto {@link LoginRequest} que contiene el identificador y la contraseña.
     * @return {@link ResponseEntity} con un objeto {@link LoginResponse} si la autenticación es exitosa,
     *         o un mensaje de error.
     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String identificador = request.getIdentificador();
        String password = request.getPassword();

        String tipoUsuario = null;
        String idUsuario = null;
        String idEquipo = null;

        try {
            Entrenador entrenador = entrenadorRepo.findById(identificador).orElse(null);
            if (entrenador != null && entrenador.getPassword().equals(password)) 
            {
                tipoUsuario = "entrenador";
                idUsuario = entrenador.getIdEntrenador();
            }
            
            if (tipoUsuario == null) 
            {
                Jugador jugador = jugadorRepo.findById(identificador).orElse(null);
                if (jugador != null && jugador.getPassword().equals(password)) 
                {
                    tipoUsuario = "jugador";
                    idUsuario = jugador.getIdJugador();
                    if (jugador.getEquipo() != null) {
                        idEquipo = jugador.getEquipo().getIdEquipo();
                }    
              }
            }

            if (tipoUsuario == null) 
            {
                Padre padre = padreRepo.findById(identificador).orElse(null);
                if (padre != null && padre.getPassword().equals(password)) 
                {
                    tipoUsuario = "padre";
                    idUsuario = padre.getIdPadre();
                }
            }
            
            if (tipoUsuario == null) 
            {
                return ResponseEntity.status(401).body("Identificador o contraseña incorrectos");
            }

            String token = tokenService.generarToken(idUsuario, tipoUsuario);

            return ResponseEntity.ok(new LoginResponse(tipoUsuario, idUsuario, token, idEquipo));

        } 
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
    
    /**
     * Guarda el token FCM de un usuario para notificaciones push.
     * @param idUsuario El ID del usuario (Entrenador, Jugador o Padre)
     * @param token FCM token del dispositivo
     */
    
    @PostMapping("/usuarios/{idUsuario}/fcm-token")
    public ResponseEntity<?> guardarFcmToken(
            @PathVariable String idUsuario,
            @RequestBody String token) {

        try {
            Entrenador entrenador = entrenadorRepo.findById(idUsuario).orElse(null);
            if (entrenador != null) 
            {
                entrenador.setFcmToken(token);
                entrenadorRepo.save(entrenador);
                return ResponseEntity.ok().build();
            }

            Jugador jugador = jugadorRepo.findById(idUsuario).orElse(null);
            if (jugador != null) 
            {
                jugador.setFcmToken(token);
                jugadorRepo.save(jugador);
                return ResponseEntity.ok().build();
            }

            Padre padre = padreRepo.findById(idUsuario).orElse(null);
            if (padre != null) {
                padre.setFcmToken(token);
                padreRepo.save(padre);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.status(404).body("Usuario no encontrado");
        } 
        catch (Exception e)
        {
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}