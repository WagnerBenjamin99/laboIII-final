package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.impl.AlumnoDaoMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AlumnoServiceImplTest {
    @Mock
    private AlumnoDao alumnoDaoMock;
    @InjectMocks
    private AlumnoServiceImpl alumnoServiceImpl;
    private Alumno alumno;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        Mockito.reset(alumnoDaoMock);
        Materia m = new Materia("Labo I", 1, 1, null);
        m.setId(0);
        Asignatura a1 = new Asignatura(m);


        alumno = new Alumno("Benjamin", "Wagner", 41515451);
        alumno.agregarAsignatura(a1);
        alumno.setId(0);
    }

    @Test
    void aprobarAsignatura() throws MateriaBadRequestException, AsignaturaNotFoundException, AlumnoNotFoundException, CorrelatividadesNoAprobadasException, AlumnoBadRequestException, EstadoIncorrectoException, AsignaturaBadRequestException, CorrelatividadException {

        Materia m = new Materia("labo", 1, 1, null);
        m.setId(0);
        Asignatura a = new Asignatura(m);
        a.setEstado(EstadoAsignatura.APROBADA);
        alumno.agregarAsignatura(a);

        when(alumnoServiceImpl.buscarAsignatura(eq(0), eq(alumno))).thenReturn(a);
        when(alumnoServiceImpl.buscarPorId(0)).thenReturn(alumno);
        when(alumnoDaoMock.aprobarAsignatura(eq(alumno), eq(0), eq(9))).thenReturn(a);

        Asignatura result = alumnoServiceImpl.aprobarAsignatura(0, 0, 9);
        assertEquals(EstadoAsignatura.APROBADA, result.getEstado());
    }

    @Test
    void aprobarAsignatura_Exception() throws MateriaBadRequestException, AsignaturaNotFoundException, AlumnoNotFoundException, CorrelatividadesNoAprobadasException, AlumnoBadRequestException, EstadoIncorrectoException, AsignaturaBadRequestException, CorrelatividadException {

        Materia m = new Materia("labo", 1, 1, null);
        m.setId(0);
        Asignatura a = new Asignatura(m);
        a.setEstado(EstadoAsignatura.APROBADA);
        alumno.agregarAsignatura(a);

        when(alumnoServiceImpl.buscarAsignatura(eq(0), eq(alumno))).thenReturn(a);
        when(alumnoServiceImpl.buscarPorId(0)).thenReturn(alumno);
        when(alumnoDaoMock.aprobarAsignatura(eq(alumno), eq(0), eq(9))).thenThrow(AsignaturaNotFoundException.class);

        assertThrows(AsignaturaNotFoundException.class, () -> {
           alumnoServiceImpl.aprobarAsignatura(0, 0, 9);
        });
    }

    @Test
    void crearAlumno_Success() throws AlumnoBadRequestException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("benja");
        alumnoDto.setApellido("wagner");
        alumnoDto.setDni(412464651);
        Alumno a = new Alumno("benja", "wagner", 412464651);
        a.setId(0);

        Alumno result = alumnoServiceImpl.crearAlumno(alumnoDto);
        result.setId(0);

        assertEquals(a, result);
    }

    @Test
    void crearAlumno_Exception() throws AlumnoBadRequestException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("benja");
        alumnoDto.setApellido("wagner");
        alumnoDto.setDni(412464651);

        when(alumnoDaoMock.saveAlumno(any())).thenThrow(AlumnoBadRequestException.class);
        assertThrows(AlumnoBadRequestException.class, () -> {
            alumnoServiceImpl.crearAlumno(alumnoDto);
        });
    }

    @Test
    void buscarAlumno_Success() throws AlumnoNotFoundException, AlumnoBadRequestException {
        when(alumnoDaoMock.findAlumno("wagner")).thenReturn(alumno);
        Alumno result = alumnoServiceImpl.buscarAlumno("wagner");
        assertEquals(alumno, result);
    }

    @Test
    void buscarAlumno_Exception() throws AlumnoNotFoundException{
        when(alumnoDaoMock.findAlumno("wagner")).thenThrow(AlumnoNotFoundException.class);
        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoServiceImpl.buscarAlumno("wagner");
        });
    }
    @Test
    void buscarPorId_Success() throws AlumnoNotFoundException, AlumnoBadRequestException {
        when(alumnoDaoMock.buscarPorId(1)).thenReturn(alumno);
        Alumno result = alumnoServiceImpl.buscarPorId(1);
        assertEquals(alumno, result);
    }

    @Test
    void buscarPorId_Exception() throws AlumnoNotFoundException{
        when(alumnoDaoMock.buscarPorId(1)).thenThrow(AlumnoNotFoundException.class);
        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoServiceImpl.buscarPorId(1);
        });
    }

    @Test
    void borrarAlumno_Success() throws AlumnoBadRequestException, AlumnoNotFoundException {
        when(alumnoServiceImpl.buscarPorId(anyInt())).thenReturn(alumno);
        when(alumnoDaoMock.borrarAlumno(alumno)).thenReturn(alumno);

        Alumno result = alumnoServiceImpl.borrarAlumno(0);
        assertEquals(alumno, result);
    }

    @Test
    void borrarAlumno_Exception() throws AlumnoBadRequestException, AlumnoNotFoundException {
        when(alumnoServiceImpl.buscarPorId(anyInt())).thenReturn(alumno);
        when(alumnoDaoMock.borrarAlumno(alumno)).thenThrow(AlumnoNotFoundException.class);

        assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoServiceImpl.borrarAlumno(0);
        });
    }
    @Test
    void editarAlumno_Success() throws AlumnoNotFoundException, AlumnoBadRequestException, MateriaBadRequestException {
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "nuevo nombre");
        Alumno a = new Alumno("pepe", "fernandez", 238379827);

        when(alumnoDaoMock.editarAlumno(0, nuevosDatos)).thenReturn(a);
        Alumno result = alumnoServiceImpl.editarAlumno(0, nuevosDatos);
        assertEquals("pepe", result.getNombre());
    }
    @Test
    void editarAlumno_Exception() throws AlumnoNotFoundException, AlumnoBadRequestException, MateriaBadRequestException {
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", "nuevo nombre");
        when(alumnoDaoMock.editarAlumno(anyInt(), any())).thenThrow(AlumnoBadRequestException.class);
        assertThrows(AlumnoBadRequestException.class, () -> {
            alumnoServiceImpl.editarAlumno(0, nuevosDatos);
        });
    }

    @Test
    void recursarAsignatura() throws AsignaturaBadRequestException, AsignaturaNotFoundException, AlumnoBadRequestException, AlumnoNotFoundException, InterruptedException {
        Materia m1 = new Materia("labo", 1, 1, null);
        m1.setId(2);
        Asignatura asignatura = new Asignatura(m1);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDaoMock.buscarPorId(0)).thenReturn(alumno);

        when(alumnoDaoMock.perderRegularidad(eq(alumno), eq(0))).thenReturn(asignatura);
        Asignatura result = alumnoServiceImpl.recursarAsignatura(0, 0);
        verify(alumnoDaoMock, times(1)).buscarPorId(0);
        verify(alumnoDaoMock, times(1)).perderRegularidad(alumno, 0);
        assertEquals(EstadoAsignatura.NO_CURSADA, result.getEstado());

    }
    @Test
    void recursarAsignatura_Exception() throws AsignaturaBadRequestException, AsignaturaNotFoundException, AlumnoBadRequestException, AlumnoNotFoundException, InterruptedException {
        Materia m1 = new Materia("labo", 1, 1, null);
        m1.setId(2);
        Asignatura asignatura = new Asignatura(m1);
        alumno.agregarAsignatura(asignatura);
        when(alumnoDaoMock.buscarPorId(0)).thenReturn(alumno);

        when(alumnoDaoMock.perderRegularidad(eq(alumno), eq(0))).thenThrow(AsignaturaNotFoundException.class);

        assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoServiceImpl.recursarAsignatura(0, 0);
        });

    }

    @Test
    void cursarAsignatura() throws AsignaturaNotFoundException, AlumnoNotFoundException, AlumnoBadRequestException {
        Materia m1 = new Materia("labo", 1, 1, null);
        m1.setId(2);
        Asignatura asignatura = new Asignatura(m1);
        asignatura.setEstado(EstadoAsignatura.CURSADA);
        when(alumnoDaoMock.buscarPorId(0)).thenReturn(alumno);
        when(alumnoDaoMock.cursarAsignatura(eq(alumno), eq(0))).thenReturn(asignatura);

        Asignatura result = alumnoServiceImpl.cursarAsignatura(0, 0);
        assertEquals(EstadoAsignatura.CURSADA, result.getEstado() );

    }

    @Test
    void cursarAsignatura_Exception() throws AsignaturaNotFoundException, AlumnoNotFoundException, AlumnoBadRequestException {
        Materia m1 = new Materia("labo", 1, 1, null);
        m1.setId(2);
        Asignatura asignatura = new Asignatura(m1);
        asignatura.setEstado(EstadoAsignatura.CURSADA);
        when(alumnoDaoMock.buscarPorId(0)).thenReturn(alumno);
        when(alumnoDaoMock.cursarAsignatura(eq(alumno), eq(0))).thenThrow(AsignaturaNotFoundException.class);

        assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoServiceImpl.cursarAsignatura(0, 0);
        });

    }

    @Test
    void chequearCorrelatividades_Success() throws CorrelatividadesNoAprobadasException {
        Alumno alumno = new Alumno("benja", "wagner", 41546468);
        Materia m = new Materia("labo 1", 1, 1, null);
        Materia m2 = new Materia("labo 2", 1, 1, null);

        m2.agregarCorrelatividad(m);
        Asignatura a = new Asignatura(m);
        Asignatura a2 = new Asignatura(m2);
        a.setEstado(EstadoAsignatura.APROBADA);
        alumno.agregarAsignatura(a);
        alumno.agregarAsignatura(a2);

        alumnoServiceImpl.chequearCorrelatividad(m, alumno);
    }

    @Test
    void chequearCorrelatividades_Exception() throws CorrelatividadesNoAprobadasException {
        Alumno alumno = new Alumno("benja", "wagner", 41546468);
        Materia m = new Materia("labo 1", 1, 1, null);
        Materia m2 = new Materia("labo 2", 1, 1, null);

        m2.agregarCorrelatividad(m);
        Asignatura a = new Asignatura(m);
        Asignatura a2 = new Asignatura(m2);
        a.setEstado(EstadoAsignatura.APROBADA);
        alumno.agregarAsignatura(a);
        alumno.agregarAsignatura(a2);

        assertThrows(CorrelatividadesNoAprobadasException.class, () -> {
            alumnoServiceImpl.chequearCorrelatividad(m2, alumno);
        });
    }
}