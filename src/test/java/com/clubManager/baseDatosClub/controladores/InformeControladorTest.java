package com.clubManager.baseDatosClub.controladores;

import com.clubManager.baseDatosClub.dto.InformeDTO;
import com.clubManager.baseDatosClub.dto.InformeResumenDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Informe;
import com.clubManager.baseDatosClub.servicios.EntrenadorServicio;
import com.clubManager.baseDatosClub.servicios.InformeServicio;
import com.clubManager.baseDatosClub.servicios.JugadorServicio;
import com.clubManager.baseDatosClub.seguridad.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InformeControlador.class)
class InformeControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InformeServicio informeServicio;

    @TestConfiguration
    static class TestConfig 
    {
        @Bean 
        InformeServicio informeServicio() 
        	{ 
        	return Mockito.mock(InformeServicio.class); 
        	}
        @Bean 
        EntrenadorServicio entrenadorServicio() 
        	{ 
        	return Mockito.mock(EntrenadorServicio.class); 
        	}
        @Bean 
        JugadorServicio jugadorServicio() 
        	{ 
        	return Mockito.mock(JugadorServicio.class); 
        	}
        @Bean 
        TokenService tokenService() 
        	{ 
        	return Mockito.mock(TokenService.class);
        	}
    }

    @Test
    void obtenerInforme_devuelveDetalle() throws Exception 
    {
        Informe informe = new Informe();
        informe.setIdInforme(1L);
        informe.setFecha(LocalDate.of(2025,1,1));
        informe.setContenido("Contenido");
        informe.setTipo("Tipo");
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre("Pedro");
        informe.setEntrenador(entrenador);

        when(informeServicio.buscarPorIdYEquipo(1L,"EQ1"))
                .thenReturn(Optional.of(informe));

        mockMvc.perform(get("/api/informes/1/equipo/EQ1")
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fecha").value("2025-01-01"))
                .andExpect(jsonPath("$.tipo").value("Tipo"))
                .andExpect(jsonPath("$.contenido").value("Contenido"))
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void listarPorEquipo_devuelveLista() throws Exception {
        InformeResumenDTO dto = new InformeResumenDTO();
        dto.setIdInforme(1L);
        dto.setTipo("Tipo");

        when(informeServicio.listarInformesPorEquipo("EQ1"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/informes/equipo/EQ1")
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idInforme").value(1))
                .andExpect(jsonPath("$[0].tipo").value("Tipo"));
    }

    @Test
    void crearInforme_devuelveDTO() throws Exception 
    {
        Informe informe = new Informe();
        informe.setIdInforme(1L);
        informe.setFecha(LocalDate.of(2025,1,1));
        informe.setContenido("Contenido");
        informe.setTipo("Tipo");
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador("E1");
        informe.setEntrenador(entrenador);

        when(informeServicio.crearInforme(Mockito.any(InformeDTO.class)))
                .thenReturn(informe);

        mockMvc.perform(post("/api/informes/crear")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "idInforme":1,
                              "fecha":"2025-01-01",
                              "contenido":"Contenido",
                              "tipo":"Tipo",
                              "idEntrenador":"E1"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInforme").value(1))
                .andExpect(jsonPath("$.tipo").value("Tipo"))
                .andExpect(jsonPath("$.contenido").value("Contenido"));
    }

    @Test
    void eliminarInforme_devuelve204() throws Exception
    {
        mockMvc.perform(delete("/api/informes/eliminar/1/equipo/EQ1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isNoContent());

        verify(informeServicio).eliminarInforme(1L,"EQ1");
    }

    @Test
    void eliminarInformesPorEquipo_devuelveMensaje() throws Exception
    {
        mockMvc.perform(delete("/api/informes/eliminar/equipo/EQ1")
                        .with(csrf())
                        .with(user("ENTRENADOR1").roles("ENTRENADOR")))
                .andExpect(status().isOk())
                .andExpect(content().string("Informes del equipo con ID EQ1 eliminados correctamente."));

        verify(informeServicio).eliminarInformesPorEquipo("EQ1");
    }
}