package com.clubManager.baseDatosClub.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.clubManager.baseDatosClub.dto.JugadorPuntuacionDTO;
import com.clubManager.baseDatosClub.dto.JugadorRelacionDTO;
import com.clubManager.baseDatosClub.entidades.*;
import com.clubManager.baseDatosClub.repositorios.*;


@SpringBootTest
class JugadorServicioImplTest {
	
	//Area de Datos

    @Mock
    private JugadorRepositorio jugadorRepo;

    @Mock
    private PadreRepositorio padreRepo;

    @Mock
    private EquipoRepositorio equipoRepo;

    @Mock
    private SancionRepositorio sancionRepo;

    @Mock
    private LesionRepositorio lesionRepo;

    @InjectMocks
    private JugadorServicioImpl jugadorServicio;

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        jugador = new Jugador();
        jugador.setIdJugador("J1");
        jugador.setNombre("Juan");
        jugador.setApellidos("Perez");
        jugador.setDni("12345678A");
        jugador.setDireccion("Calle 1");
        jugador.setTipoUsuario("Jugador");
        jugador.setPassword("123456");
        jugador.setTelefono("+34123456789");
        jugador.setEmail("test@test.com");
        jugador.setPosicion("Delantero");
        jugador.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        jugador.setActivo(true);
        jugador.setGoles(0);
        jugador.setTarjetasAmarillas(0);
        jugador.setTarjetasRojas(0);
        jugador.setGolesEncajados(0);
        jugador.setPuntosTotales(10);
        jugador.setPadres(new ArrayList<>());
    }
    
    //Test para los métodos principales

    @Test
    void buscarPorId_OK() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        Optional<Jugador> result = jugadorServicio.buscarPorId("J1");
        assertTrue(result.isPresent());
    }

    @Test
    void crearJugador_OK() 
    {
        when(jugadorRepo.save(any(Jugador.class))).thenReturn(jugador);

        jugadorServicio.crearJugador("J1", "Juan", "Perez", "12345678A", "Calle",
                "Jugador", "123456", "+34666666666", "test@test.com",
                "Delantero", LocalDate.of(2000, 1, 1));

        verify(jugadorRepo).save(any(Jugador.class));
    }

    @Test
    void crearJugador_DatosInvalidos_LanzaException() 
    {
        assertThrows(IllegalArgumentException.class, () -> {
            jugadorServicio.crearJugador("", "Juan", "Perez", "12345678A", "Calle",
                    "Jugador", "123456", "+34666666666", "test@test.com",
                    "Delantero", LocalDate.now());
        });
    }

    @Test
    void modificarJugador_OK() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        when(jugadorRepo.save(any(Jugador.class))).thenReturn(jugador);

        jugadorServicio.modificarJugador("J1", "Juan", "Perez", "12345678A", "Calle",
                "Jugador", "123456", "foto.png", "+34123456789",
                "test@test.com", "Delantero", LocalDate.of(2000, 1, 1));

        verify(jugadorRepo).save(any(Jugador.class));
    }

    @Test
    void modificarJugador_NoExiste_LanzaExcepcion() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> 
        {
            jugadorServicio.modificarJugador("J1", "Juan", "Perez", "12345678A", "Calle",
                    "Jugador", "123456", "foto.png", "+34123456789",
                    "test@test.com", "Delantero", LocalDate.of(2000, 1, 1));
        });
    }

    @Test
    void eliminarJugador_OK() 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo("E1");
        jugador.setEquipo(equipo);

        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));

        boolean result = jugadorServicio.eliminarJugador("J1", "E1");

        assertTrue(result);
        verify(jugadorRepo).delete(jugador);
    }

    @Test
    void eliminarJugador_NoCoincideEquipo() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        boolean result = jugadorServicio.eliminarJugador("J1", "OTRO");
        assertFalse(result);
    }

    @Test
    void crearJugador_Obj_OK() 
    {
        when(jugadorRepo.save(jugador)).thenReturn(jugador);
        Jugador result = jugadorServicio.crearJugador(jugador);
        assertEquals("J1", result.getIdJugador());
    }

    @Test
    void actualizarJugador_OK() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        when(jugadorRepo.save(jugador)).thenReturn(jugador);

        Jugador res = jugadorServicio.actualizarJugador(jugador);
        assertNotNull(res);
    }

    @Test
    void actualizarJugador_NoExiste_LanzaExcepcion() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> jugadorServicio.actualizarJugador(jugador));
    }

    @Test
    void eliminarJugadorPorId_OK() 
    {
        jugadorServicio.eliminarJugadorPorId("J1");
        verify(jugadorRepo).deleteById("J1");
    }

    @Test
    void listarJugadoresActivosPorEquipo_OK() 
    {
        when(jugadorRepo.findByActivoTrueAndEquipo_idEquipo("E1"))
            .thenReturn(List.of(jugador));

        List<Jugador> list = jugadorServicio.listarJugadoresActivosPorEquipo("E1");
        assertEquals(1, list.size());
    }

    @Test
    void cambiarEstadoActivo_OK() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        jugadorServicio.cambiarEstadoActivo("J1", false);

        assertFalse(jugador.isActivo());
        verify(jugadorRepo).save(jugador);
    }

    @Test
    void cambiarEstadoActivo_NoExiste() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> jugadorServicio.cambiarEstadoActivo("J1", false));
    }

    @Test
    void incrementarGoles_OK() 
    {
        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        jugadorServicio.incrementarGoles("J1", 2);
        assertEquals(2, jugador.getGoles());
    }

    @Test
    void incrementarGoles_CantidadInvalida() 
    {
        assertThrows(IllegalArgumentException.class, () -> jugadorServicio.incrementarGoles("J1", 0));
    }

    @Test
    void vincularPadreAJugador_OK() 
    {
        Jugador jugador = new Jugador();
        jugador.setIdJugador("J1");
        jugador.setPadres(new ArrayList<>());

        Padre padre = new Padre();
        padre.setIdPadre("P1");

        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        when(padreRepo.findById("P1")).thenReturn(Optional.of(padre));

        when(jugadorRepo.save(any(Jugador.class))).thenAnswer(inv -> inv.getArgument(0));

        Jugador result = jugadorServicio.vincularPadreAJugador("J1", "P1");

        assertTrue(result.getPadres().contains(padre));
    }


    @Test
    void vincularPadre_Repetido_LanzaException() 
    {
        Padre padre = new Padre();
        padre.setIdPadre("P1");
        jugador.getPadres().add(padre);

        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));
        when(padreRepo.findById("P1")).thenReturn(Optional.of(padre));

        assertThrows(IllegalStateException.class,
                () -> jugadorServicio.vincularPadreAJugador("J1", "P1"));
    }

    @Test
    void listarJugadoresPorEquipo_OK() 
    {
        when(jugadorRepo.findByEquipo_IdEquipo("E1")).thenReturn(List.of(jugador));

        List<JugadorRelacionDTO> list = jugadorServicio.listarJugadoresPorEquipo("E1");

        assertEquals(1, list.size());
        assertEquals("Juan", list.get(0).getNombre());
    }

    @Test
    void eliminarJugadoresPorEquipo_OK() 
    {
        when(equipoRepo.existsById("E1")).thenReturn(true);
        jugadorServicio.eliminarJugadoresPorEquipo("E1");
        verify(jugadorRepo).deleteByEquipo_IdEquipo("E1");
    }

    @Test
    void eliminarJugadoresPorEquipo_NoExisteEquipo() 
    {
        when(equipoRepo.existsById("E1")).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> jugadorServicio.eliminarJugadoresPorEquipo("E1"));
    }

    @Test
    void obtenerJugadorPorEquipo_OK() 
    {
        when(jugadorRepo.findByIdJugadorAndEquipo_IdEquipo("J1", "E1"))
            .thenReturn(Optional.of(jugador));

        Optional<Jugador> result = jugadorServicio.obtenerJugadorPorEquipo("J1", "E1");

        assertTrue(result.isPresent());
    }

    @Test
    void incrementarPuntos_OK() {
        Jugador jugador = new Jugador();
        jugador.setIdJugador("J1");
        jugador.setPuntosTotales(10);

        when(jugadorRepo.findById("J1")).thenReturn(Optional.of(jugador));

        when(jugadorRepo.save(any(Jugador.class)))
            .thenAnswer(inv -> inv.getArgument(0));

        Jugador result = jugadorServicio.incrementarPuntos("J1", 5);

        assertEquals(15, result.getPuntosTotales());
    }


    @Test
    void incrementarPuntos_Invalido() 
    {
        assertThrows(IllegalArgumentException.class, () -> jugadorServicio.incrementarPuntos("J1", -3));
    }

    @Test
    void obtenerRankingPorEquipo_OK() 
    {
        jugador.setPuntosTotales(15);

        when(jugadorRepo.findByEquipo_IdEquipoOrderByPuntosTotalesDesc("E1"))
            .thenReturn(List.of(jugador));

        List<JugadorPuntuacionDTO> ranking = jugadorServicio.obtenerRankingPorEquipo("E1");

        assertEquals(1, ranking.size());
        assertEquals("J1", ranking.get(0).getIdJugador());
        assertEquals(15, ranking.get(0).getPuntosTotales());
    }
}