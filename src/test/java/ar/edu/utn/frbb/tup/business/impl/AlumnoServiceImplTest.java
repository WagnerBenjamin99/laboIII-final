package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AlumnoServiceImplTest {

    @Mock
    private AlumnoDao alumnoDao;

    @Mock
    private AsignaturaService asignaturaService;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAprobarAsignatura() throws Exception, EstadoIncorrectoException, CorrelatividadException {
        // Configurar comportamiento de mocks
        Alumno alumno = new Alumno();
        when(alumnoDao.buscarPorId(anyInt())).thenReturn(alumno);

        Asignatura asignatura = new Asignatura();
        Materia materia = new Materia();
        materia.setCorrelatividades(new ArrayList<>());
        asignatura.setMateria(materia);
        when(alumnoService.buscarAsignatura(anyInt(), any(Alumno.class))).thenReturn(asignatura);

        // Llamar al método que se va a probar
        Asignatura result = alumnoService.aprobarAsignatura(1, 1, 10);

        // Verificar interacciones y resultados
        verify(alumnoDao).buscarPorId(1);
        verify(alumnoService).buscarAsignatura(1, alumno);
        verify(alumnoDao).aprobarAsignatura(alumno, 1, 10);
        assertEquals(asignatura, result);
    }

    @Test
    public void testCrearAlumno() {

        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Nombre");
        alumnoDto.setApellido("Apellido");
        alumnoDto.setDni(Long.parseLong("12345678"));

        when(alumnoDao.saveAlumno(any(Alumno.class))).thenReturn(new Alumno("Nombre", "Apellido", 12345678));

        Alumno result = alumnoService.crearAlumno(alumnoDto);
        result.setId(0);

        //VERIFICO QUE EL RESULTADO SEA EL ESPERADO
        verify(alumnoDao).saveAlumno(any(Alumno.class));
        assertEquals(new Alumno("Nombre", "Apellido", 12345678), result);

    }

    @Test
    public void testBuscarAlumno() throws AlumnoNotFoundException {
        // Configurar comportamiento de mocks
        Alumno alumnoEncontrado = new Alumno();
        when(alumnoDao.findAlumno(anyString())).thenReturn(alumnoEncontrado);

        // Llamar al método que se va a probar
        Alumno result = alumnoService.buscarAlumno("Apellido");

        // Verificar interacciones y resultados
        verify(alumnoDao).findAlumno("Apellido");
        assertEquals(alumnoEncontrado, result);


    }

    @Test
    public void testBorrarAlumno() throws AlumnoNotFoundException {
        // Configurar comportamiento de mocks
        Alumno alumno = new Alumno();
        when(alumnoDao.buscarPorId(anyInt())).thenReturn(alumno);
        when(alumnoDao.borrarAlumno(any(Alumno.class))).thenReturn(alumno);

        // Llamar al método que se va a probar
        Alumno result = alumnoService.borrarAlumno(1);

        // Verificar interacciones y resultados
        verify(alumnoDao).buscarPorId(1);
        verify(alumnoDao).borrarAlumno(alumno);
        assertEquals(alumno, result);
    }

    @Test
    public void testEditarAlumno() throws AlumnoNotFoundException, MateriaBadRequestException, AlumnoBadRequestException {
        // Configurar comportamiento de mocks
        Alumno alumno = new Alumno();
        when(alumnoDao.editarAlumno(anyInt(), anyMap())).thenReturn(alumno);

        // Llamar al método que se va a probar
        Alumno result = alumnoService.editarAlumno(1, new HashMap<>());

        // Verificar interacciones y resultados
        verify(alumnoDao).editarAlumno(1, new HashMap<>());
        assertEquals(alumno, result);
    }

    @Test
    public void testRecursarAsignatura() throws AlumnoNotFoundException {
        // Configurar comportamiento de mocks
        Alumno alumno = new Alumno();
        when(alumnoDao.buscarPorId(anyInt())).thenReturn(alumno);

        AsignaturaDto asignaturaDto = new AsignaturaDto();
        asignaturaDto.setIdAlumno(1);

        int idAsignatura = 42;  // Aquí debes proporcionar el ID de la asignatura correcto

        Asignatura asignatura = new Asignatura();
        when(alumnoService.buscarAsignatura(eq(idAsignatura), any(Alumno.class))).thenReturn(asignatura);

        Asignatura asignaturaPerdida = new Asignatura();
        when(alumnoDao.perderRegularidad(any(Alumno.class), eq(idAsignatura))).thenReturn(asignaturaPerdida);

        // Llamar al método que se va a probar
        Asignatura result = alumnoService.recursarAsignatura(asignaturaDto);

        // Verificar interacciones y resultados
        verify(alumnoDao).buscarPorId(1);
        verify(alumnoService).buscarAsignatura(eq(idAsignatura), eq(alumno));  // Corregir el ID de la asignatura aquí
        verify(alumnoDao).perderRegularidad(alumno, idAsignatura);  // Corregir el ID de la asignatura aquí
        assertEquals(asignaturaPerdida, result);
    }
    // Agregar más pruebas para otros métodos de la clase AlumnoServiceImpl
}