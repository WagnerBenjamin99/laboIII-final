package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.impl.CarreraDaoMemoryImpl;
import ar.edu.utn.frbb.tup.persistence.impl.MateriaDaoMemoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarreraServiceImplTest {
    @Mock
    private CarreraDaoMemoryImpl carreraDao;
    @Mock
    private MateriaServiceImpl materiaService;
    @Mock
    private Map<Integer, Carrera> repositorioCarrera;

    @InjectMocks
    private CarreraServiceImpl carreraService;
    private CarreraDto carreraDto;
    private Carrera carrera;
    private List<Carrera> repositorioValues;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        repositorioValues = new ArrayList<>();
        Carrera c1 = new Carrera();
        c1.setNombre("LOI");
        c1.setCantidadCuatrimestres(8);
        c1.setIdDepartamento(2);

        carreraDto = new CarreraDto();
        carreraDto.setNombre("TUP");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCantidadCuatrimestres(4);

        carrera = new Carrera();
        carrera.setNombre(carreraDto.getNombre());
        carrera.setIdDepartamento(carreraDto.getDepartamentoId());
        carrera.setCantidadCuatrimestres(carreraDto.getCantidadCuatrimestres());

        repositorioValues.add(carrera);
        repositorioValues.add(c1);


    }
    @Test
    void crearCarrera_Success() throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        Mockito.when(carreraDao.crearCarrera(Mockito.any(Carrera.class))).thenReturn(carrera);
        Carrera result = carreraService.crearCarrera(carreraDto);
        assertEquals(carrera, result);
    }

    @Test
    void crearCarrera_Exception() throws CarreraBadRequestException, MateriaNotFoundException, CarreraNotFoundException {
        when(carreraDao.crearCarrera(any())).thenThrow(CarreraBadRequestException.class);
        Assertions.assertThrows(CarreraBadRequestException.class, () -> {
            carreraService.crearCarrera(carreraDto);
        });
    }

    @Test
    void getAllCarreras_Success() throws CarreraNotFoundException {

        when(carreraDao.getAllCarreras()).thenReturn(repositorioValues);
        List<Carrera> result = carreraService.getAllCarreras();
        assertEquals(repositorioValues, result);
    }

    @Test
    void getAllCarreras_Exception() throws CarreraNotFoundException {

        when(carreraDao.getAllCarreras()).thenThrow(CarreraNotFoundException.class);

        assertThrows(CarreraNotFoundException.class, () -> {
            carreraService.getAllCarreras();
        });
    }
    @Test
    void getCarreraById_Success() throws CarreraNotFoundException {
        when(carreraDao.getCarreraById(anyInt())).thenReturn(carrera);
        Carrera result = carreraService.getCarreraById(1);
        assertEquals(carrera, result);
    }
    @Test
    void getCarreraById_Exception() throws CarreraNotFoundException {
        when(carreraDao.getCarreraById(anyInt())).thenThrow(CarreraNotFoundException.class);
        assertThrows(CarreraNotFoundException.class, () -> {
            carreraService.getCarreraById(2);
        });
    }

    @Test
    void agregarMateria_Success() throws CarreraBadRequestException, CarreraNotFoundException, MateriaNotFoundException {
        Materia m = new Materia("Labo III", 2, 1, null);
        when(carreraDao.getCarreraById(anyInt())).thenReturn(carrera);
        when(materiaService.getMateriaById(anyInt())).thenReturn(m);
        carrera.agregarMateria(m);
        when(carreraDao.agregarMateria(any(), any())).thenReturn(carrera);

        Carrera result = carreraService.agregarMateria(1, 1);
        assertEquals(carrera, result);
    }

    @Test
    void agregarMateria_Exception() throws CarreraBadRequestException, CarreraNotFoundException, MateriaNotFoundException {
        Materia m = new Materia("Labo III", 2, 1, null);
        when(carreraDao.getCarreraById(anyInt())).thenReturn(carrera);
        when(materiaService.getMateriaById(anyInt())).thenReturn(m);
        carrera.agregarMateria(m);

        when(carreraDao.agregarMateria(any(), any())).thenThrow(CarreraBadRequestException.class);
        assertThrows(CarreraBadRequestException.class, () -> {
            carreraService.agregarMateria(1, 2);
        });
    }

    @Test
    void modificarCarrera_Success() throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        when(carreraDao.modificarCarrera(any(), anyInt())).thenReturn(carrera);
        Carrera result = carreraService.modificarCarrera(new HashMap<>(), 0);
        assertEquals(carrera, result);

    }
    @Test
    void modificarCarrera_Exception() {
        assertThrows(CarreraBadRequestException.class, () -> {
            carreraService.modificarCarrera(null, 1);
        });
    }

    @Test
    void eliminarCarrera_Success() throws CarreraNotFoundException, MateriaNotFoundException {
        when(carreraDao.eliminarCarrera(any())).thenReturn(carrera);
        when(carreraService.getCarreraById(anyInt())).thenReturn(carrera);

        Carrera result = carreraService.eliminarCarrera(1);
        assertEquals(carrera, result);
    }
    @Test
    void eliminarCarrera_Exception() throws CarreraNotFoundException {
        when(carreraService.getCarreraById(anyInt())).thenReturn(carrera);
        when(carreraDao.eliminarCarrera(carrera)).thenThrow(CarreraNotFoundException.class);
        assertThrows(CarreraNotFoundException.class, () -> {
           carreraService.eliminarCarrera(0);
        });
    }
}