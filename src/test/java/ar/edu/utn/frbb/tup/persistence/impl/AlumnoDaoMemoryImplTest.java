package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoDaoMemoryImplTest {
    @Mock
    private Map<Long, Alumno> repositorioAlumnos;

    @InjectMocks
    private AlumnoDaoMemoryImpl alumnoDaoMemoryImpl;

    private static List<Alumno> repositorioValues;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.reset(repositorioAlumnos);

        repositorioValues = new ArrayList<>();

        Alumno alumno = new Alumno("Benjamin", "Wagner", 41878485);
        Alumno alumno1 =  new Alumno("Emilce", "Caba", 32619889);
        Alumno alumno2 =  new Alumno("Matias", "Dupont", 46198651);

        alumno.setId(0);
        alumno1.setId(1);
        alumno2.setId(2);

        HashMap<Long, Alumno> repo = new HashMap<>();
        repositorioValues.add(alumno);
        repositorioValues.add(alumno1);
        repositorioValues.add(alumno2);

    }

    @Test
    void testSaveAlumno_Success() throws AlumnoBadRequestException {
        Alumno alumnoAGuardar = new Alumno("nombre", "apellido", 0L);
        when(repositorioAlumnos.put(anyLong(), any(Alumno.class))).thenReturn(alumnoAGuardar);

        Alumno resultado = alumnoDaoMemoryImpl.saveAlumno(alumnoAGuardar);

        assertNotNull(resultado);
        assertEquals(alumnoAGuardar, resultado);
    }


    @Test
    void testFindAlumno_Success() throws AlumnoNotFoundException {
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

        Alumno alumno = new Alumno("Benjamin", "Wagner", 41878485);
        Alumno result = alumnoDaoMemoryImpl.findAlumno("Wagner");
        assertEquals(alumno, result);
    }

    @Test
    void testFindAlumno_Exception() {
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

      assertThrows(AlumnoNotFoundException.class, () -> {
          alumnoDaoMemoryImpl.findAlumno("Apellido inexistente");
      });
    }

    @Test
    void testBuscarPorId_Success() throws AlumnoNotFoundException {
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

        Alumno a = new Alumno("Emilce", "Caba", 32619889);
        a.setId(1);

        Alumno result = alumnoDaoMemoryImpl.buscarPorId(1);
        assertEquals(a, result);
    }
    @Test
    void testBuscarPorId_Exception(){
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoDaoMemoryImpl.buscarPorId(4);
        });
    }

    @Test
    void testBorrarAlumno() throws AlumnoNotFoundException {

        Alumno alumno = new Alumno("Benjamin", "Wagner", 41878485);
        when(repositorioAlumnos.remove(anyLong(), any(Alumno.class))).thenReturn(true);

        Alumno result = alumnoDaoMemoryImpl.borrarAlumno(alumno);

        assertEquals(alumno, result);
    }

    @Test
    void testBorrarAlumno_Exception(){
        Alumno a = new Alumno("Benjamin", "Wagner", 41878485);
        assertThrows(AlumnoNotFoundException.class, () -> {
           alumnoDaoMemoryImpl.borrarAlumno(a);
        });
    }

    @Test
    void testEditarAlumno() throws AlumnoNotFoundException, AlumnoBadRequestException {
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "pepe");
        Alumno result = alumnoDaoMemoryImpl.editarAlumno(0, nuevosDatos);
        assertEquals(new Alumno("pepe", "Wagner", 41878485), result);
    }

    @Test
    void testEditarAlumno_Exception() throws AlumnoNotFoundException, AlumnoBadRequestException {
        when(repositorioAlumnos.values()).thenReturn(repositorioValues);

        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("compo incorrecto", "pepe");

        assertThrows(AlumnoBadRequestException.class, () -> {
            alumnoDaoMemoryImpl.editarAlumno(0, nuevosDatos);
        });
    }

    @Test
    void testAprobarAsignatura() throws MateriaBadRequestException, AsignaturaNotFoundException, AlumnoNotFoundException {
        Alumno alumno = new Alumno("Benjamin", "Wagner", 41878485);
        Materia m1 = new Materia("labo I", 1, 1, null);
        Materia m2 = new Materia("labo II", 1, 2, null);
        m2.agregarCorrelatividad(m1);
        Asignatura a1 = new Asignatura(m1);
        a1.setEstado(EstadoAsignatura.CURSADA);
        Asignatura a2 = new Asignatura(m2);

        alumno.agregarAsignatura(a1);
        alumno.agregarAsignatura(a2);

        Asignatura esperada = new Asignatura(new Materia("labo I", 1, 1, null));
        esperada.setEstado(EstadoAsignatura.APROBADA);
        esperada.setNota(10);

        Asignatura result = alumnoDaoMemoryImpl.aprobarAsignatura(alumno, 0, 10);
        assertEquals(esperada, result);
    }

    @Test
    void testCursarAsignatura_Success() throws AsignaturaNotFoundException {
        Alumno a = new Alumno("Bnejamin", "Wagner", 55468684);
        a.setId(0);
        Materia m = new Materia("labo", 2, 1, null);
        m.setId(0);
        Asignatura asignatura = new Asignatura(m);
        a.agregarAsignatura(asignatura);

        Asignatura result = alumnoDaoMemoryImpl.cursarAsignatura(a, 0);
        assertEquals(EstadoAsignatura.CURSADA, result.getEstado());

    }

    @Test
    void testCursarAsignatura_Excpetion() throws AsignaturaNotFoundException {
        Alumno a = new Alumno("Bnejamin", "Wagner", 55468684);
        a.setId(0);

        assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoDaoMemoryImpl.cursarAsignatura(a, 10);
        });

    }
    @Test
    void testRecursarAsignatura_Success() throws AsignaturaBadRequestException, AsignaturaNotFoundException {
        Alumno a = new Alumno("Benjamin", "Wagner" ,1161616);
        Materia m = new Materia("Labo I", 1, 1, null);
        m.setId(0);
        Asignatura asignatura = new Asignatura(m);
        asignatura.setEstado(EstadoAsignatura.CURSADA);
        a.agregarAsignatura(asignatura);

        Asignatura result = alumnoDaoMemoryImpl.perderRegularidad(a, 0);
        assertEquals(EstadoAsignatura.NO_CURSADA, result.getEstado());
    }

    @Test
    void testRecursarAsignatura_Exception() throws AsignaturaBadRequestException, AsignaturaNotFoundException {
        Alumno a = new Alumno("Benjamin", "Wagner" ,1161616);
        Materia m = new Materia("Labo I", 1, 1, null);
        m.setId(0);
        Asignatura asignatura = new Asignatura(m);
        asignatura.setEstado(EstadoAsignatura.APROBADA);
        a.agregarAsignatura(asignatura);

        assertThrows(AsignaturaBadRequestException.class, () -> {
            alumnoDaoMemoryImpl.perderRegularidad(a, 0);
        });
    }

    @Test
    void testBuscarAsignatura_Success() throws AsignaturaNotFoundException {
        Alumno a = new Alumno("Benjamin", "Wagner" ,1161616);
        Materia m = new Materia("Labo I", 1, 1, null);
        m.setId(0);
        Asignatura asignatura = new Asignatura(m);
        a.agregarAsignatura(asignatura);

        Asignatura result = alumnoDaoMemoryImpl.buscarAsignatura(0, a);
        assertEquals(asignatura, result);
    }

    @Test
    void testBuscarAsignatura_Exception() throws AsignaturaNotFoundException {
        Alumno a = new Alumno("Benjamin", "Wagner" ,1161616);

        assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoDaoMemoryImpl.buscarAsignatura(1, a);
        });
    }
}

