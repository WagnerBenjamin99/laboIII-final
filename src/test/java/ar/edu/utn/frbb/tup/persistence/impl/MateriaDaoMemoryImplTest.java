package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MateriaDaoMemoryImplTest {
    @Mock
    private Map<Integer, Materia> repositorioMateria;
    @InjectMocks
    private MateriaDaoMemoryImpl materiaDaoMemoryImpl;
    private static List<Materia> repositorioValues;



    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        Mockito.reset(repositorioMateria);

        repositorioValues = new ArrayList<>();

        Materia m1 = new Materia("labo I", 1, 1, null);
        Materia m2 = new Materia("labo II", 1, 2, null);
        Materia m3 = new Materia("labo III", 2, 1, null);
        Materia m4 = new Materia("labo IV", 2, 2, null);
        m1.setId(0);
        m2.setId(1);
        m3.setId(2);
        m4.setId(3);

        repositorioValues.add(m1);
        repositorioValues.add(m2);
        repositorioValues.add(m3);
        repositorioValues.add(m4);
    }


    @Test
    void testSave() {
        Materia result = materiaDaoMemoryImpl.save(new Materia("nombre", 0, 0, new Profesor("nombre", "apellido", "titulo")));
        //SETEO ATRIBUTOS QUE SE GENERAN ALEATORIAMENTE
        result.setCodigo(null);
        result.setId(0);
        Assertions.assertEquals(new Materia("nombre", 0, 0, new Profesor("nombre", "apellido", "titulo")), result);
    }

    @Test
    void testFindById_Exception() throws MateriaNotFoundException {

        Assertions.assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.findById(0);
        });
    }
    @Test
    void testFindById_Success() throws MateriaNotFoundException {

        Materia m = new Materia("labo I", 1, 1, null);
        m.setId(0);

        when(repositorioMateria.values()).thenReturn(repositorioValues);

        Materia result = materiaDaoMemoryImpl.findById(0);
        Assertions.assertEquals(m, result);
    }

    @Test
    void testBorrarMateria_Exception() {
        Materia m = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));
        Assertions.assertThrows(MateriaNotFoundException.class, () -> {
        materiaDaoMemoryImpl.borrarMateria(m);
    });
    }

    @Test
    void testBorrarMateria_Success() throws MateriaNotFoundException {
        Materia m = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));
        when(repositorioMateria.remove(anyInt(), any(Materia.class))).thenReturn(true);

        Materia result = materiaDaoMemoryImpl.borrarMateria(m);
        Assertions.assertEquals(m, result);
    }

    @Test
    void testGetAllMaterias_Exception(){
        Materia mat = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));Materia m = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));
        Assertions.assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.getAllMaterias();
        });
    }

    @Test
    void testModificarMateria_Success() throws MateriaNotFoundException, MateriaBadRequestException {
        Materia mat = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));
        mat.setMateriaId(0);
        List<Materia> repoValues = new ArrayList<>();

        repoValues.add(mat);
        when(repositorioMateria.values()).thenReturn(repoValues);

        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "labo III");


        Materia result = materiaDaoMemoryImpl.modificarMateria(nuevosDatos, 0);
        Materia esperada = new Materia("labo III", 2, 1, new Profesor("luciano", "salotto", "lic"));
        esperada.setMateriaId(0);

        Assertions.assertEquals(esperada, result);
        System.out.println("ACA "  +  repositorioValues);
    }

    @Test
    void testModificarMateria_Exception() throws MateriaNotFoundException, MateriaBadRequestException {
        Materia mat = new Materia("labo", 2, 1, new Profesor("luciano", "salotto", "lic"));
        when(repositorioMateria.values()).thenReturn(repositorioValues);

        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("campoErroneo", "algo");

        Assertions.assertThrows(MateriaBadRequestException.class, () -> {
            materiaDaoMemoryImpl.modificarMateria(nuevosDatos, 0);
        });
        System.out.println(repositorioValues);
    }


    @Test
    void testFiltrarPorNombre_Success() throws MateriaBadRequestException {
        Materia esperada = new Materia("labo II", 1, 2, null);
        esperada.setId(1);
        when(repositorioMateria.values()).thenReturn(repositorioValues);
        Materia result = materiaDaoMemoryImpl.filtrarPorNombre("labo II");
        Assertions.assertEquals(esperada, result);

    }
    @Test
    void testFiltrarPorNombre_Exception() {
        when(repositorioMateria.values()).thenReturn(repositorioValues);

        Assertions.assertThrows(MateriaBadRequestException.class, () -> {
            materiaDaoMemoryImpl.filtrarPorNombre("programacion");
        });


    }
    @Test
    void testOrdenarMaterias_Success() throws MateriaBadRequestException {
        List<Materia> result = new ArrayList<>();
        List<Materia> esperadas = new ArrayList<>();

        esperadas.add(new Materia("labo I", 1, 1, null));
        esperadas.add(new Materia("labo II", 1, 2, null));
        esperadas.add( new Materia("labo III", 2, 1, null));
        esperadas.add(new Materia("labo IV", 2, 2, null));
        when(repositorioMateria.values()).thenReturn(repositorioValues);

        result  = materiaDaoMemoryImpl.ordenarMaterias("nombre_asc");

        Assertions.assertEquals(esperadas, result);
        System.out.println(repositorioValues);
    }

    @Test
    void testOrdenarMateria_Exception(){
        Assertions.assertThrows(MateriaBadRequestException.class, () -> {
            materiaDaoMemoryImpl.ordenarMaterias("incorrecto");
        });
    }


    @Test
    void testAsignarCarrera_Success() throws MateriaBadRequestException {
        Carrera carrera = new Carrera();
        carrera.setNombre("TUP");

        when(repositorioMateria.values()).thenReturn(repositorioValues);
        Materia result = materiaDaoMemoryImpl.asignarCarrera(carrera, new Materia("labo III", 2, 1, null));
        Assertions.assertEquals(carrera, result.getCarrera());
    }
    @Test
    void testAsignarCarrera_Exception(){
        Carrera carrera = new Carrera();
        carrera.setNombre("TUP");
        when(repositorioMateria.values()).thenReturn(repositorioValues);


        Assertions.assertThrows(MateriaBadRequestException.class, () -> {
            materiaDaoMemoryImpl.asignarCarrera(carrera, new Materia("progra III", 2, 1, null));
        });
    }
}
