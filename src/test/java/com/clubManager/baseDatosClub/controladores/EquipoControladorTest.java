package com.clubManager.baseDatosClub.controladores;

import com.clubManager.baseDatosClub.dto.EquipoSeleccionDTO;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.seguridad.TokenService;
import com.clubManager.baseDatosClub.servicios.EquipoServicio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipoControlador.class)
class EquipoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EquipoServicio equipoServicio;

    @TestConfiguration
    static class TestConfig 
    {
        @Bean
        EquipoServicio equipoServicio() {
            return Mockito.mock(EquipoServicio.class);
        }

        @Bean
        TokenService tokenService() {
            return Mockito.mock(TokenService.class);
        }
    }

    private Equipo crearEquipo(String id, String password) 
    {
        Equipo e = new Equipo();
        e.setIdEquipo(id);
        e.setPassword(password);
        e.setJugadores(new ArrayList<>());
        return e;
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void validarEquipo_correcto_devuelve200() throws Exception 
    {
        Equipo equipo = crearEquipo("EQ1", "secret");
        when(equipoServicio.buscarPorId("EQ1")).thenReturn(Optional.of(equipo));

        mockMvc.perform(post("/api/equipos/validar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "identificador":"EQ1",
                                  "password":"secret"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void validarEquipo_incorrecto_devuelve401() throws Exception 
    {
        Equipo equipo = crearEquipo("EQ1", "secret");
        when(equipoServicio.buscarPorId("EQ1")).thenReturn(Optional.of(equipo));

        mockMvc.perform(post("/api/equipos/validar")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "identificador":"EQ1",
                                  "password":"wrong"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Identificador o contraseña incorrectos"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void buscarPorId_devuelveEquipo() throws Exception 
    {
        Equipo equipo = crearEquipo("EQ1", "secret");
        when(equipoServicio.buscarPorId("EQ1")).thenReturn(Optional.of(equipo));

        mockMvc.perform(get("/api/equipos/EQ1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEquipo").value("EQ1"))
                .andExpect(jsonPath("$.password").value("secret"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void buscarPorId_noExiste_devuelve404() throws Exception 
    {
        when(equipoServicio.buscarPorId("EQ1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/equipos/EQ1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearEquipo_devuelve200() throws Exception 
    {
        mockMvc.perform(post("/api/equipos/registrar")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idEquipo":"EQ1",
                                  "nombreEquipo":"MiEquipo",
                                  "categoria":"Senior",
                                  "escudoEquipo":"escudo.png",
                                  "password":"secret"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Equipo creado y asignado correctamente al entrenador."));

        verify(equipoServicio).crearEquipo("EQ1", "MiEquipo", "Senior", "escudo.png", "secret");
        verify(equipoServicio).asignarEquipoAEntrenador("ENTRENADOR1", "EQ1");
    }

    @Test
    void crearEquipo_errorAsignacion_devuelve500() throws Exception 
    {
        doThrow(new RuntimeException("fallo"))
                .when(equipoServicio).asignarEquipoAEntrenador(eq("ENTRENADOR1"), eq("EQ1"));

        mockMvc.perform(post("/api/equipos/registrar")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idEquipo":"EQ1",
                                  "nombreEquipo":"MiEquipo",
                                  "categoria":"Senior",
                                  "escudoEquipo":"escudo.png",
                                  "password":"secret"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al asignar el equipo al entrenador: fallo"));
    }

    @Test
    void modificarEquipo_devuelve200() throws Exception 
    {
        mockMvc.perform(put("/api/equipos/EQ1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idEquipo":"EQ1",
                                  "nombreEquipo":"NuevoNombre",
                                  "categoria":"Juvenil",
                                  "escudoEquipo":"nuevo.png",
                                  "password":"newpass",
                                  "fotoEquipo":"foto.png"
                                }
                                """))
                .andExpect(status().isOk());

        verify(equipoServicio).modificarEquipo(
                eq("EQ1"), eq("NuevoNombre"), eq("Juvenil"),
                eq("nuevo.png"), eq("newpass"), eq("foto.png")
        );
    }

    @Test
    void eliminarEquipo_devuelve200() throws Exception 
    {
        mockMvc.perform(delete("/api/equipos/EQ1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk());

        verify(equipoServicio).eliminarEquipo("EQ1");
    }
    
    @Test
    void agregarPadre_devuelve200() throws Exception 
    {
        mockMvc.perform(post("/api/equipos/EQ1/padres/P1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(content().string("Padre agregado correctamente"));

        verify(equipoServicio).agregarPadreAEquipo("EQ1", "P1");
    }

    @Test
    void agregarPadre_error_devuelve400() throws Exception 
    {
        doThrow(new RuntimeException("Error"))
                .when(equipoServicio).agregarPadreAEquipo("EQ1", "P1");

        mockMvc.perform(post("/api/equipos/EQ1/padres/P1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error"));
    }

    @Test
    void verPadresDelEquipo_devuelveLista() throws Exception 
    {
        Padre padre = new Padre();
        padre.setIdPadre("P1");
        padre.setNombre("Juan");

        when(equipoServicio.obtenerPadresDeEquipo("EQ1"))
                .thenReturn(List.of(padre));

        mockMvc.perform(get("/api/equipos/EQ1/padres")
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPadre").value("P1"))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void verPadresDelEquipo_error_devuelve404() throws Exception 
    {
        doThrow(new RuntimeException("Error"))
                .when(equipoServicio).obtenerPadresDeEquipo("EQ1");

        mockMvc.perform(get("/api/equipos/EQ1/padres")
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerEquiposPorPadre_devuelveLista() throws Exception 
    {
        EquipoSeleccionDTO dto = new EquipoSeleccionDTO("EQ1", "MiEquipo");
        when(equipoServicio.obtenerEquiposPorPadre("P1"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/equipos/porPadre/P1")
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEquipo").value("EQ1"))
                .andExpect(jsonPath("$[0].nombreEquipo").value("MiEquipo"));
    }

    @Test
    void establecerClasificacion_devuelve200() throws Exception 
    {
        mockMvc.perform(put("/api/equipos/EQ1/clasificacion")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Primera\""))
                .andExpect(status().isOk());

        verify(equipoServicio).establecerClasificacion("EQ1", "Primera");
    }
}
