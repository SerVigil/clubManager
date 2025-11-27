package com.clubManager.baseDatosClub.servicios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.clubManager.baseDatosClub.dto.EntrenamientoDTO;
import com.clubManager.baseDatosClub.entidades.Entrenamiento;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.ModeloEntrenamiento;
import com.clubManager.baseDatosClub.repositorios.EntrenamientoRepositorio;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.ModeloEntrenamientoRepositorio;

class EntrenamientoServicioImplTest {

    @Mock
    private EntrenamientoRepositorio entrenamientoRepo;

    @Mock
    private EquipoRepositorio equipoRepo;

    @Mock
    private ModeloEntrenamientoRepositorio modeloRepo;

    @InjectMocks
    private EntrenamientoServicioImpl servicio;

    private EntrenamientoDTO dto;
    private Equipo equipo;
    private ModeloEntrenamiento modelo;

    @BeforeEach
    void setUp() 
    {
        MockitoAnnotations.openMocks(this);

        dto = new EntrenamientoDTO();
        dto.setFecha(LocalDate.of(2025, 1, 10));
        dto.setHora(LocalTime.of(18, 0));
        dto.setTipo("Físico");
        dto.setObservaciones("Entrenamiento de prueba");
        dto.setIdEquipo("E01");
        dto.setIdModelo(5L);

        equipo = new Equipo();
        equipo.setIdEquipo("E01");

        modelo = new ModeloEntrenamiento();
        modelo.setIdModelo(5L);
    }

    @Test
    void crearEntrenamiento_deberiaCrearCorrectamente() 
    {
        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));
        when(modeloRepo.findByIdModelo(5L)).thenReturn(Optional.of(modelo));

        Entrenamiento entrenamientoGuardado = new Entrenamiento();
        when(entrenamientoRepo.save(any(Entrenamiento.class)))
                .thenReturn(entrenamientoGuardado);

        Entrenamiento resultado = servicio.crearEntrenamiento(dto);

        assertNotNull(resultado);
        verify(equipoRepo, times(1)).findById("E01");
        verify(modeloRepo, times(1)).findByIdModelo(5L);
        verify(entrenamientoRepo, times(1)).save(any(Entrenamiento.class));
    }
}