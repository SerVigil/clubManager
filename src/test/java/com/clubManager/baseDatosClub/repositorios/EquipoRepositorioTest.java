package com.clubManager.baseDatosClub.repositorios;

import com.clubManager.baseDatosClub.entidades.Equipo;
import com.clubManager.baseDatosClub.entidades.Jugador;
import com.clubManager.baseDatosClub.entidades.Padre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class EquipoRepositorioTest {

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    @Autowired
    private JugadorRepositorio jugadorRepositorio;

    @Autowired
    private PadreRepositorio padreRepositorio;

    @Autowired
    private EntityManager entityManager;

    private Equipo crearEquipoValido(String id, String nombre) 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(id);
        equipo.setNombreEquipo(nombre);
        equipo.setPassword("password123");
        equipo.setCategoria("Senior");
        equipo.setJugadores(new ArrayList<>());
        equipo.setPadres(new ArrayList<>());
        return equipo;
    }

   
    private Jugador crearJugadorValido(String id, String nombre, String dni) 
    {
        Jugador jugador = new Jugador();
        jugador.setIdJugador(id);
        jugador.setNombre(nombre);
        jugador.setApellidos("Apellidos " + nombre);
        jugador.setDni(dni);
        jugador.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        jugador.setPassword("password123");
        jugador.setTelefono("600987654");
        jugador.setTipoUsuario("JUGADOR");
        return jugador;
    }

  
    private Padre crearPadreValido(String id, String nombre) 
    {
        Padre padre = new Padre();
        padre.setIdPadre(id);
        padre.setNombre(nombre);
        padre.setApellidos("Apellidos " + nombre);
        padre.setDni("12345678Z");
        padre.setTelefono("600123456");
        padre.setEmail("padre"+id+"@mail.com");
        padre.setPassword("password123");
        padre.setTipoUsuario("PADRE");
        padre.setVinculo("Tutor");
        return padre;
    }

    @Test
    void guardarEquipo_yBuscarPorId() 
    {
        Equipo equipo = crearEquipoValido("eq1", "Equipo 1");
        equipoRepositorio.save(equipo);

        Equipo encontrado = equipoRepositorio.findById("eq1").orElse(null);
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Equipo 1", encontrado.getNombreEquipo());
    }

    @Test
    void findJugadoresByIdEquipo_retornaLista() 
    {
        Equipo equipo = crearEquipoValido("eq2", "Equipo 2");
        equipoRepositorio.save(equipo);

        Jugador jugador = crearJugadorValido("j1", "Jugador 1", "12345678A");
        jugador.setEquipo(equipo); 
        jugadorRepositorio.save(jugador);

        List<Jugador> jugadores = equipoRepositorio.findJugadoresByIdEquipo("eq2");
        Assertions.assertEquals(1, jugadores.size());
        Assertions.assertEquals("Jugador 1", jugadores.get(0).getNombre());
    }

    @Test
    void findEquiposByIdPadre_retornaLista() 
    {
        Equipo equipo = crearEquipoValido("eq3", "Equipo 3");

        Padre padre = crearPadreValido("p1", "Padre 1");
        padreRepositorio.save(padre);

        equipo.getPadres().add(padre);
        equipoRepositorio.save(equipo);

        List<Equipo> equipos = equipoRepositorio.findEquiposByIdPadre("p1");
        Assertions.assertEquals(1, equipos.size());
        Assertions.assertEquals("Equipo 3", equipos.get(0).getNombreEquipo());
    }

    @Test
    @Transactional
    @Rollback
    void actualizarClasificacion_actualizaCorrectamente() 
    {
        Equipo equipo = crearEquipoValido("eq4", "Equipo 4");
        equipo.setClasificacion("url-antigua");
        equipoRepositorio.save(equipo);

        equipoRepositorio.actualizarClasificacion("eq4", "url-nueva");

        entityManager.flush();
        entityManager.clear();

        Equipo actualizado = equipoRepositorio.findById("eq4").orElse(null);
        Assertions.assertNotNull(actualizado);
        Assertions.assertEquals("url-nueva", actualizado.getClasificacion());
    }
}