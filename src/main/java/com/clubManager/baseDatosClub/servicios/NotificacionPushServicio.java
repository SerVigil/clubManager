package com.clubManager.baseDatosClub.servicios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Notificacion;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.repositorios.NotificacionRepositorio;
import com.google.auth.oauth2.GoogleCredentials;

import jakarta.validation.Path;

/**
 * Servicio encargado de enviar notificaciones push a los miembros de un equipo
 * (jugadores, padres y entrenadores) mediante Firebase Cloud Messaging (FCM).
 * 
 * @author Sergio Vigil Soto
 */

@Service
public class NotificacionPushServicio {
	
	//Area de Datos

    @Autowired
    private NotificacionRepositorio notificacionRepo;

    private static final String PROJECT_ID = "clubmanager-1268f";

    private static final String FCM_URL =
            "https://fcm.googleapis.com/v1/projects/" + PROJECT_ID + "/messages:send";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Envía una notificación push a todos los miembros del equipo
     * (jugadores, padres y entrenadores) que tengan un token FCM registrado.
     */
    
    public void enviarNotificacionAEquipo(Equipo equipo, String titulo, String mensaje) 
    {
        Set<String> tokens = new HashSet<>();

        if (equipo.getJugadores() != null) 
        {
            equipo.getJugadores().stream()
                    .filter(j -> j != null && j.getFcmToken() != null)
                    .map(Jugador::getFcmToken)
                    .forEach(tokens::add);
        }

        if (equipo.getPadres() != null) 
        {
            equipo.getPadres().stream()
                    .filter(p -> p != null && p.getFcmToken() != null)
                    .map(Padre::getFcmToken)
                    .forEach(tokens::add);
        }

        if (equipo.getEntrenadores() != null) 
        {
            equipo.getEntrenadores().stream()
                    .filter(e -> e != null && e.getFcmToken() != null)
                    .map(Entrenador::getFcmToken)
                    .forEach(tokens::add);
        }

        Notificacion notificacion = guardarNotificacion(titulo, mensaje, LocalDate.now(), equipo);
        Long idNotificacion = notificacion.getIdNotificacion();
        String fechaNotificacion = notificacion.getFecha().toString();

        for (String token : tokens) {
            try {
                String accessToken = obtenerAccessToken();
                String cleanToken = token.replace("\"", "").trim();

                String payload = """
                {
                  "message": {
                    "token": "%s",
                    "notification": {
                      "title": "%s",
                      "body": "%s"
                    },
                    "data": {
                      "tipo": "notificacion",
                      "equipo": "%s",
                      "idNotificacion": "%s",
                      "fechaNotificacion": "%s",
                      "mensajeNotificacion": "%s"
                    }
                  }
                }
                """.formatted(cleanToken, titulo, mensaje, equipo.getIdEquipo(),
                        idNotificacion, fechaNotificacion, mensaje);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(FCM_URL))
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .build();

                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            } 
            catch (Exception e) 
            {
                e.getMessage();
            }
        }
    }

    /**
     * Obtiene un token de acceso OAuth2 válido usando la cuenta de servicio de Firebase.
     */
    
    private String obtenerAccessToken() throws Exception {
        // Leemos el JSON completo de la variable de entorno
        String serviceAccountJson = System.getenv("FCM_SERVICE_ACCOUNT_JSON");
        if (serviceAccountJson == null || serviceAccountJson.isEmpty()) {
            throw new IllegalStateException("No se encontró la variable de entorno FCM_SERVICE_ACCOUNT_JSON");
        }

        // Escribimos temporalmente en un archivo
        Path tempFile = Files.createTempFile("service-account", ".json");
        Files.writeString(tempFile, serviceAccountJson);

        try (InputStream serviceAccount = new FileInputStream(tempFile.toFile())) {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(serviceAccount)
                    .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

            googleCredentials.refreshIfExpired();

            if (googleCredentials.getAccessToken() == null) {
                googleCredentials.refresh();
            }

            return googleCredentials.getAccessToken().getTokenValue();
        }
    }


    /**
     * Guarda la notificación en la base de datos asociada al equipo indicado.
     */
    
    public Notificacion guardarNotificacion(String titulo, String mensaje, LocalDate fecha, Equipo equipo) 
    {
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(fecha);
        notificacion.setEquipo(equipo);

        return notificacionRepo.save(notificacion);
    }
}