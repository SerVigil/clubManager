package com.clubManager.baseDatosClub.repositorios;

import com.clubManager.baseDatosClub.entidades.Entrenamiento;
import com.clubManager.baseDatosClub.entidades.Equipo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class EntrenamientoRepositorioTest {

    @Autowired
    private EntrenamientoRepositorio entrenamientoRepositorio;

    @Autowired
    private EquipoRepositorio equipoRepositorio;

    private Equipo crearEquipoValido(String id, String nombre) 
    {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(id);
        equipo.setNombreEquipo(nombre);
        equipo.setCategoria("Senior");
        equipo.setPassword("password123");
        return equipo;
    }

    private Entrenamiento crearEntrenamientoValido(String tipo, Equipo equipo) 
    {
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setTipo(tipo); // ≤ 20 caracteres
        entrenamiento.setFecha(LocalDate.now());
        entrenamiento.setHora(LocalTime.of(10, 0));
        entrenamiento.setEquipo(equipo);
        return entrenamiento;
    }

    @Test
    void guardarEntrenamiento_yBuscarPorId() 
    {
        Equipo equipo = crearEquipoValido("eq1", "Equipo 1");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Fisico", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        Long idGenerado = entrenamiento.getIdEntrenamiento();
        Entrenamiento encontrado = entrenamientoRepositorio.findById(idGenerado).orElse(null);

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Fisico", encontrado.getTipo());
    }

    @Test
    void findByEquipo_IdEquipo_retornaLista() 
    {
        Equipo equipo = crearEquipoValido("eq2", "Equipo 2");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Tecnico", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        List<Entrenamiento> entrenamientos = entrenamientoRepositorio.findByEquipo_IdEquipo("eq2");
        Assertions.assertEquals(1, entrenamientos.size());
        Assertions.assertEquals("Tecnico", entrenamientos.get(0).getTipo());
    }

    @Test
    void findByIdEntrenamientoAndEquipo_IdEquipo_retornaEntrenamiento() 
    {
        Equipo equipo = crearEquipoValido("eq3", "Equipo 3");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Tactico", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        Long idGenerado = entrenamiento.getIdEntrenamiento();
        Optional<Entrenamiento> encontrado =
                entrenamientoRepositorio.findByIdEntrenamientoAndEquipo_IdEquipo(idGenerado, "eq3");

        Assertions.assertTrue(encontrado.isPresent());
        Assertions.assertEquals("Tactico", encontrado.get().getTipo());
    }

    @Test
    @Transactional
    @Rollback
    void deleteByEquipo_IdEquipo_eliminaEntrenamientos() 
    {
        Equipo equipo = crearEquipoValido("eq4", "Equipo 4");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Resistencia", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        entrenamientoRepositorio.deleteByEquipo_IdEquipo("eq4");

        List<Entrenamiento> entrenamientos = entrenamientoRepositorio.findByEquipo_IdEquipo("eq4");
        Assertions.assertTrue(entrenamientos.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void deleteByIdEntrenamientoAndEquipo_IdEquipo_eliminaCorrectamente() 
    {
        Equipo equipo = crearEquipoValido("eq5", "Equipo 5");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Velocidad", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        Long idGenerado = entrenamiento.getIdEntrenamiento();
        entrenamientoRepositorio.deleteByIdEntrenamientoAndEquipo_IdEquipo(idGenerado, "eq5");

        boolean existe = entrenamientoRepositorio.existsByIdEntrenamientoAndEquipo_IdEquipo(idGenerado, "eq5");
        Assertions.assertFalse(existe);
    }

    @Test
    void existsByIdEntrenamientoAndEquipo_IdEquipo_devuelveTrueSiExiste() 
    {
        Equipo equipo = crearEquipoValido("eq6", "Equipo 6");
        equipoRepositorio.save(equipo);

        Entrenamiento entrenamiento = crearEntrenamientoValido("Mixto", equipo);
        entrenamientoRepositorio.save(entrenamiento);

        Long idGenerado = entrenamiento.getIdEntrenamiento();
        boolean existe = entrenamientoRepositorio.existsByIdEntrenamientoAndEquipo_IdEquipo(idGenerado, "eq6");
        Assertions.assertTrue(existe);

        boolean noExiste = entrenamientoRepositorio.existsByIdEntrenamientoAndEquipo_IdEquipo(99L, "eq6");
        Assertions.assertFalse(noExiste);
    }
}