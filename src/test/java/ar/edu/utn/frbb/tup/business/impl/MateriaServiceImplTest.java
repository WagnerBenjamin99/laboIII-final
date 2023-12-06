package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @Mock
    private MateriaDao materiaDao;
    @Mock ProfesorService profesorService;
    @InjectMocks
    private MateriaServiceImpl materiaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMateria_Success() throws MateriaBadRequestException {
        when(profesorService.buscarProfesor(anyLong())).thenReturn(new Profesor("Luciano", "Salotto", "Lic."));
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica");
        materiaDto.setAnio(2023);
        materiaDto.setCuatrimestre(1);


        Materia materiaCreada = new Materia();
        materiaCreada.setNombre(materiaDto.getNombre());
        materiaCreada.setAnio(materiaDto.getAnio());
        materiaCreada.setCuatrimestre(materiaDto.getCuatrimestre());

        when(materiaDao.save(any(Materia.class))).thenReturn(materiaCreada);


        Materia materiaGuardada = materiaService.crearMateria(materiaDto);

        assertNotNull(materiaGuardada);
        assertEquals(materiaDto.getNombre(), materiaGuardada.getNombre());
        assertEquals(materiaDto.getAnio(), materiaGuardada.getAnio());
        assertEquals(materiaDto.getCuatrimestre(), materiaGuardada.getCuatrimestre());

    }

    @Test
    void testCrearMateria_Exception() throws MateriaBadRequestException {

        when(profesorService.buscarProfesor(anyLong())).thenReturn(new Profesor("Luciano", "Salotto", "Lic."));
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica");
        materiaDto.setAnio(2023);
        materiaDto.setCuatrimestre(1);
        materiaDto.setProfesorId(1);

        Materia materiaCreada = new Materia();
        materiaCreada.setNombre(materiaDto.getNombre());
        materiaCreada.setAnio(materiaDto.getAnio());
        materiaCreada.setCuatrimestre(materiaDto.getCuatrimestre());

        when(materiaDao.save(any(Materia.class))).thenThrow(MateriaBadRequestException.class);

        assertThrows(MateriaBadRequestException.class, () -> {
            materiaService.crearMateria(materiaDto);
        });
    }
@Test
    void getAllMaterias_Success() throws MateriaNotFoundException {
        Materia m1 = new Materia("labo 1", 1, 1, null);
        Materia m2 = new Materia("labo 2", 1, 2, null);
        List<Materia> materias = new ArrayList<>();
        materias.add(m1);
        materias.add(m2);

        when(materiaDao.getAllMaterias()).thenReturn(materias);
        assertNotNull(materiaService.getAllMaterias());
        assertEquals(materias, materiaService.getAllMaterias());
    }

    @Test
    void getAllMaterias_Exception() throws MateriaNotFoundException {

        when(materiaDao.getAllMaterias()).thenThrow(MateriaNotFoundException.class);
        assertThrows(MateriaNotFoundException.class, () -> {
            materiaService.getAllMaterias();
        });
    }

    @Test
    void getMateriaById_Success() throws MateriaNotFoundException {
        Materia m = new Materia("labo", 1, 1, null);
        m.setId(0);

        when(materiaDao.findById(0)).thenReturn(m);
        assertEquals(m, materiaService.getMateriaById(0));
    }
    @Test
    void getMateriaById_Exception() throws MateriaNotFoundException {
        Materia m = new Materia("labo", 1, 1, null);
        m.setId(0);

        when(materiaDao.findById(1)).thenThrow(MateriaNotFoundException.class);
        assertThrows(MateriaNotFoundException.class, () -> {
            materiaService.getMateriaById(1);
        });
    }
    @Test
    void borrarMateria_Success() throws MateriaNotFoundException {
        Materia m = new Materia("labo", 1, 1, null);
        when(materiaDao.borrarMateria(m)).thenReturn(m);
        when(materiaService.getMateriaById(0)).thenReturn(m);

        assertEquals(m, materiaService.borrarMateria(0));
    }
    @Test
    void borrarMateria_Exception() throws MateriaNotFoundException {
        Materia m = new Materia("labo", 1, 1, null);
        when(materiaDao.borrarMateria(m)).thenThrow(MateriaNotFoundException.class);
        when(materiaService.getMateriaById(0)).thenReturn(m);

        assertThrows(MateriaNotFoundException.class, () -> {
            materiaService.borrarMateria(0);
        });
    }
    @Test
    void modificarMateria_Success() throws MateriaNotFoundException, MateriaBadRequestException {
        Materia m = new Materia("labo", 1, 1, null);
        when(materiaDao.modificarMateria(new HashMap<>(), 0)).thenReturn(m);

        assertEquals(m, materiaService.modificarMateria(new HashMap<>(), 0));
    }
    @Test
    void modificarMateria_Exception() throws MateriaNotFoundException, MateriaBadRequestException {
        Materia m = new Materia("labo", 1, 1, null);
        when(materiaDao.modificarMateria(new HashMap<>(), 0)).thenThrow(MateriaBadRequestException.class);

        assertThrows(MateriaBadRequestException.class, () -> {
            materiaService.modificarMateria(new HashMap<>(), 0);
        });
    }
    @Test
    void ordenarMaterias_Success() throws MateriaBadRequestException {
        Materia m1 = new Materia("labo 1", 1, 1, null);
        Materia m2 = new Materia("labo 2", 1, 2, null);
        List<Materia> materias = new ArrayList<>();
        materias.add(m1);
        materias.add(m2);

        when(materiaDao.ordenarMaterias("asc")).thenReturn(materias);
        assertNotNull(materiaService.ordenarMaterias("asc"));
        assertEquals(materias, materiaService.ordenarMaterias("asc"));
    }

    @Test
    void ordenarMaterias_Exception() throws MateriaBadRequestException {

        when(materiaDao.ordenarMaterias("desc")).thenThrow(MateriaBadRequestException.class);
        assertThrows(MateriaBadRequestException.class, () -> {
            materiaService.ordenarMaterias("desc");
        });
    }

    @Test
    void asignarCarrera_Success() throws MateriaBadRequestException {
        Carrera c = new Carrera();
        c.setNombre("tup");
        Materia m = new Materia("labo", 1, 1, null);
        m.setCarrera(c);

        when(materiaDao.asignarCarrera(c, m)).thenReturn(m);

        assertEquals(m, materiaService.asignarCarrera(c, m));

    }
    @Test
    void asignarCarrera_Exception() throws MateriaBadRequestException {
        Carrera c = new Carrera();
        c.setNombre("tup");
        Materia m = new Materia("labo", 1, 1, null);

        when(materiaDao.asignarCarrera(c, m)).thenThrow(MateriaBadRequestException.class);
        assertThrows(MateriaBadRequestException.class, () -> {
           materiaService.asignarCarrera(c, m);
        });
    }
}