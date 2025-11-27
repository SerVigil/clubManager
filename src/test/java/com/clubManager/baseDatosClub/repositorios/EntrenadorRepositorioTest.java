package com.clubManager.baseDatosClub.repositorios;

import com.clubManager.baseDatosClub.entidades.Entrenador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class EntrenadorRepositorioTest {

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    private Entrenador crearEntrenador(String id, String nombre, String dni) 
    {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador(id);
        entrenador.setNombre(nombre);
        entrenador.setApellidos("Apellidos de " + nombre);   
        entrenador.setTipoUsuario("ENTRENADOR");             
        entrenador.setPassword("password123");              
        entrenador.setDni(dni);
        return entrenador;
    }

    @Test
    void guardarEntrenador_yBuscarPorId() 
    {
        String idBuscado = "e1";
        Entrenador entrenador = crearEntrenador(idBuscado, "Entrenador 1", "12345678A");

        entrenadorRepositorio.save(entrenador);

        Optional<Entrenador> encontradoOptional = entrenadorRepositorio.findById(idBuscado);

        Assertions.assertTrue(encontradoOptional.isPresent(), "El entrenador debe ser encontrado por su ID.");
        Entrenador encontrado = encontradoOptional.get();
        Assertions.assertEquals(idBuscado, encontrado.getIdEntrenador());
        Assertions.assertEquals("Entrenador 1", encontrado.getNombre());
    }

    @Test
    void findByDni_retornaEntrenador() 
    {
        String dniBuscado = "87654321B";
        Entrenador entrenador = crearEntrenador("e2", "Entrenador 2", dniBuscado);

        entrenadorRepositorio.save(entrenador);

        Optional<Entrenador> encontrado = entrenadorRepositorio.findByDni(dniBuscado);

        Assertions.assertTrue(encontrado.isPresent(), "El entrenador debe ser encontrado por DNI.");
        Assertions.assertEquals("e2", encontrado.get().getIdEntrenador());
    }

    @Test
    void existsByDni_devuelveTrueSiExiste() 
    {
        String dniExistente = "11223344C";
        Entrenador entrenador = crearEntrenador("e3", "Entrenador 3", dniExistente);

        entrenadorRepositorio.save(entrenador);

        boolean existe = entrenadorRepositorio.existsByDni(dniExistente);
        Assertions.assertTrue(existe, "Debe devolver true para un DNI existente.");

        boolean noExiste = entrenadorRepositorio.existsByDni("99999999Z");
        Assertions.assertFalse(noExiste, "Debe devolver false para un DNI que no existe.");
    }
}