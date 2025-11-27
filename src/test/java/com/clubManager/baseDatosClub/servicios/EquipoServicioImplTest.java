package com.clubManager.baseDatosClub.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.clubManager.baseDatosClub.dto.EquipoSeleccionDTO;
import com.clubManager.baseDatosClub.entidades.Entrenador;
import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;
import com.clubManager.baseDatosClub.repositorios.EntrenadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.EquipoRepositorio;
import com.clubManager.baseDatosClub.repositorios.JugadorRepositorio;
import com.clubManager.baseDatosClub.repositorios.PadreRepositorio;

class EquipoServicioImplTest {

    @Mock
    private EquipoRepositorio equipoRepo;

    @Mock
    private JugadorRepositorio jugadorRepo;

    @Mock
    private PadreRepositorio padreRepo;

    @Mock
    private EntrenadorRepositorio entrenadorRepo;

    @InjectMocks
    private EquipoServicioImpl servicio;

    @BeforeEach
    void setUp() 
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId() 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo("E01");

        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));

        Optional<Equipo> resultado = servicio.buscarPorId("E01");

        assertTrue(resultado.isPresent());
        assertEquals("E01", resultado.get().getIdEquipo());
    }

    @Test
    void testCrearEquipo() 
    {
        doAnswer(inv -> null).when(equipoRepo).save(any(Equipo.class));

        servicio.crearEquipo("E01", "Juveniles", "Sub-18", "escudo.png", "pass123");

        verify(equipoRepo, times(1)).save(any(Equipo.class));
    }

    @Test
    void testAgregarJugadorAEquipo() 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo("E01");
        equipo.setJugadores(new ArrayList<>());

        Jugador jugador = new Jugador();
        jugador.setIdJugador("J10");

        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));
        when(jugadorRepo.findById("J10")).thenReturn(Optional.of(jugador));

        servicio.agregarJugadorAEquipo("E01", "J10");

        assertEquals(1, equipo.getJugadores().size());
        assertEquals(equipo, jugador.getEquipo());

        verify(jugadorRepo, times(1)).save(jugador);
    }

    @Test
    void testObtenerJugadoresDeEquipo() 
    {
        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();

        Equipo equipo = new Equipo();
        equipo.setIdEquipo("E01");
        equipo.setJugadores(List.of(j1, j2));

        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));

        List<Jugador> jugadores = servicio.obtenerJugadoresDeEquipo("E01");

        assertEquals(2, jugadores.size());
    }

    @Test
    void testAgregarPadreAEquipo() 
    {
        Equipo equipo = new Equipo();
        equipo.setPadres(new ArrayList<>());

        Padre padre = new Padre();
        padre.setIdPadre("P22");

        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));
        when(padreRepo.findById("P22")).thenReturn(Optional.of(padre));

        servicio.agregarPadreAEquipo("E01", "P22");

        assertEquals(1, equipo.getPadres().size());
        verify(equipoRepo, times(1)).save(equipo);
    }

    @Test
    void testObtenerEquiposPorPadre() 
    {
        Equipo eq = new Equipo();
        eq.setIdEquipo("E01");

        when(equipoRepo.findEquiposByIdPadre("P22")).thenReturn(List.of(eq));

        List<EquipoSeleccionDTO> lista = servicio.obtenerEquiposPorPadre("P22");

        assertEquals(1, lista.size());
        assertEquals("E01", lista.get(0).getIdEquipo());
    }

    @Test
    void testAsignarEquipoAEntrenador() 
    {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador("EN1");

        Equipo equipo = new Equipo();
        equipo.setIdEquipo("E01");

        when(entrenadorRepo.findById("EN1")).thenReturn(Optional.of(entrenador));
        when(equipoRepo.findById("E01")).thenReturn(Optional.of(equipo));

        servicio.asignarEquipoAEntrenador("EN1", "E01");

        assertEquals(equipo, entrenador.getEquipo());
        verify(entrenadorRepo, times(1)).save(entrenador);
    }

    @Test
    void testEstablecerClasificacion() 
    {
        servicio.establecerClasificacion("E01", "Primero");

        verify(equipoRepo, times(1)).actualizarClasificacion("E01", "Primero");
    }
}