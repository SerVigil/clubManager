package com.clubManager.baseDatosClub.controladores;

import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.servicios.EntrenadorServicio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EntrenadorControlador.class)
class EntrenadorControladorTest {
	
	//Area de datos

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntrenadorServicio entrenadorServicio;

    @TestConfiguration
    static class TestConfig {
        @Bean
        EntrenadorServicio entrenadorServicio() 
        {
            return Mockito.mock(EntrenadorServicio.class);
        }
        @Bean
        com.clubManager.baseDatosClub.seguridad.TokenService tokenService() 
        {
            return Mockito.mock(com.clubManager.baseDatosClub.seguridad.TokenService.class);
        }
    }

    private Entrenador crearEntrenador(String id, String nombre) 
    {
        Entrenador e = new Entrenador();
        e.setIdEntrenador(id);
        e.setNombre(nombre);
        e.setApellidos("Apellido");
        e.setDni("12345678A");
        e.setDireccion("Calle Falsa 123");
        e.setTipoUsuario("ENTRENADOR");
        e.setPassword("pass");
        e.setFoto("foto.png");
        e.setTelefono("600000000");
        e.setEmail("test@test.com");
        return e;
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void listarEntrenadores_devuelveLista() throws Exception 
    {
        when(entrenadorServicio.listarEntrenadores())
                .thenReturn(List.of(crearEntrenador("E1", "Juan")));

        mockMvc.perform(get("/api/entrenadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEntrenador").value("E1"))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void buscarPorId_devuelveEntrenador() throws Exception 
    {
        when(entrenadorServicio.buscarPorId("E1"))
                .thenReturn(Optional.of(crearEntrenador("E1", "Pedro")));

        mockMvc.perform(get("/api/entrenadores/E1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntrenador").value("E1"))
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void crearEntrenador_devuelve200() throws Exception 
    {
        mockMvc.perform(post("/api/entrenadores")
                        .with(csrf()) // añade token CSRF simulado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "idEntrenador":"E2",
                              "nombre":"Luis",
                              "apellidos":"Apellido",
                              "dni":"12345678A",
                              "direccion":"Calle Falsa 123",
                              "tipoUsuario":"ENTRENADOR",
                              "password":"pass",
                              "foto":"foto.png",
                              "telefono":"600000000",
                              "email":"test@test.com"
                            }
                            """))
                .andExpect(status().isOk());

        verify(entrenadorServicio).crearEntrenador
        (
                "E2","Luis","Apellido","12345678A",
                "Calle Falsa 123","ENTRENADOR","pass",
                "foto.png","600000000","test@test.com"
        );
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void modificarEntrenador_devuelve200() throws Exception 
    {
        mockMvc.perform(put("/api/entrenadores/E1")
                        .with(csrf()) // añade token CSRF simulado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "idEntrenador":"E1",
                              "nombre":"Carlos",
                              "apellidos":"Apellido",
                              "dni":"12345678A",
                              "direccion":"Calle Falsa 123",
                              "tipoUsuario":"ENTRENADOR",
                              "password":"pass",
                              "foto":"foto.png",
                              "telefono":"600000000",
                              "email":"test@test.com"
                            }
                            """))
                .andExpect(status().isOk());

        verify(entrenadorServicio).modificarEntrenador
        (
                eq("E1"), eq("Carlos"), eq("Apellido"), eq("12345678A"),
                eq("Calle Falsa 123"), eq("ENTRENADOR"), eq("pass"),
                eq("foto.png"), eq("600000000"), eq("test@test.com")
        );
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void eliminarEntrenador_devuelve200() throws Exception 
    {
        mockMvc.perform(delete("/api/entrenadores/E1")
                        .with(csrf())) // añade token CSRF simulado
                .andExpect(status().isOk());

        verify(entrenadorServicio).eliminarEntrenador("E1");
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void buscarPorDni_devuelveEntrenador() throws Exception 
    {
        when(entrenadorServicio.buscarPorDni("12345678A"))
                .thenReturn(Optional.of(crearEntrenador("E3", "Mario")));

        mockMvc.perform(get("/api/entrenadores/dni/12345678A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntrenador").value("E3"))
                .andExpect(jsonPath("$.nombre").value("Mario"));
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void registrarEntrenador_devuelve201ConId() throws Exception 
    {
        mockMvc.perform(post("/api/entrenadores/registrar")
                        .with(csrf()) // añade token CSRF simulado
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "idEntrenador":"E4",
                              "nombre":"Ana",
                              "apellidos":"Apellido",
                              "dni":"12345678A",
                              "direccion":"Calle Falsa 123",
                              "tipoUsuario":"ENTRENADOR",
                              "password":"pass",
                              "foto":"foto.png",
                              "telefono":"600000000",
                              "email":"test@test.com"
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEntrenador").value("E4"));

        verify(entrenadorServicio).crearEntrenador(any(Entrenador.class));
    }
}