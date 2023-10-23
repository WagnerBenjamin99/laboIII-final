package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.CharArrayReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarreraDaoMemoryImplTest {
    @Mock
    private Map<Integer, Carrera> repositorioCarrera;
    @InjectMocks
    private CarreraDaoMemoryImpl carreraDaoMemory;
    private static List<Carrera> repositorioValues;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        Mockito.reset(repositorioCarrera);
        repositorioValues = new ArrayList<>();

        Carrera c1 = new Carrera();
        c1.setNombre("TUP");
        c1.setCantidadCuatrimestres(4);
        c1.setIdDepartamento(1);
        c1.setId(0);

        Carrera c2 = new Carrera();
        c2.setNombre("Electronica");
        c2.setCantidadCuatrimestres(4);
        c2.setIdDepartamento(2);
        c2.setId(1);

        repositorioValues.add(c1);
        repositorioValues.add(c2);





    }

    @Test
    void getAllCarreras() throws CarreraNotFoundException {
        when(repositorioCarrera.values()).thenReturn(repositorioValues);
        List<Carrera> result  = carreraDaoMemory.getAllCarreras();

        assertEquals(repositorioValues, result);
    }
    @Test
    void getCarreraById() throws CarreraNotFoundException {
        when(repositorioCarrera.values()).thenReturn(repositorioValues);
        Carrera result = carreraDaoMemory.getCarreraById(0);
        Carrera esperada = new Carrera();

        esperada.setNombre("TUP");
        esperada.setCantidadCuatrimestres(4);
        esperada.setIdDepartamento(1);
        esperada.setId(0);

        assertEquals(esperada, result);
    }

    @Test
    void agregarMateria() throws CarreraBadRequestException, CarreraNotFoundException {
        Carrera c1 = new Carrera();
        c1.setNombre("TUP");
        c1.setCantidadCuatrimestres(4);
        c1.setIdDepartamento(1);
        c1.setId(0);

        when(repositorioCarrera.values()).thenReturn(repositorioValues);
        Materia m1 = new Materia("labo I", 1, 1, null);


        Carrera result = carreraDaoMemory.agregarMateria(m1, c1);

        assertEquals(m1.getId(), result.getMateriasList().get(0).getId());
    }

    @Test
    void crearCarreraConMaterias() throws CarreraBadRequestException, CarreraNotFoundException {

        Materia m1 = new Materia("labo I", 1, 1, null);
        Materia m2 = new Materia("labo II", 1, 2, null);

        when(repositorioCarrera.values()).thenReturn((repositorioValues));

        List<Materia> materias = new ArrayList<>();
        materias.add(m1);
        materias.add(m2);

        Carrera result = carreraDaoMemory.crearCarreraConMaterias(materias, repositorioValues.get(0));
        assertEquals(materias, result.getMateriasList());
    }

    @Test
    void modificarCarrera_Success() throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        Carrera c = new Carrera();
        c.setNombre("TUP");
        c.setId(0);
        List<Carrera> repositorioValues = new ArrayList<>();
        repositorioValues.add(c);

        Carrera esperada = new Carrera();
        esperada.setId(0);
        esperada.setNombre("Tecnicatura en programacion");

        when(repositorioCarrera.values()).thenReturn(repositorioValues);

        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "Tecnicatura en programacion");


        Carrera result = carreraDaoMemory.modificarCarrera(nuevosDatos, 0);


        Assertions.assertEquals(esperada, result);
    }

    @Test
    void modificarCarrera_Exception() throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        when(repositorioCarrera.values()).thenReturn(repositorioValues);
        HashMap<String, Object> nuevosDatos = new HashMap<>();

        nuevosDatos.put("incorrecto", "algo");
        Assertions.assertThrows(CarreraBadRequestException.class, () -> {
            carreraDaoMemory.modificarCarrera(nuevosDatos, 0);
        });
    }
    @Test
    void eliminarCarrera() throws CarreraNotFoundException {
        Carrera c = new Carrera();
        c.setId(0);
        c.setNombre("TUP");
        when(repositorioCarrera.remove(anyInt(), any(Carrera.class))).thenReturn(true);

        Carrera result = carreraDaoMemory.eliminarCarrera(c);
        Assertions.assertEquals(c, result);
    }

    @Test
    void crearCarrera() {
        Carrera c = new Carrera();
        c.setNombre("Ingenieria Electronica");
        c.setCantidadCuatrimestres(10);
        c.setIdDepartamento(2);
        c.setId(4);

        Carrera result = carreraDaoMemory.crearCarrera(c);

        assertEquals(c, result);
    }
}