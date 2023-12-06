package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;

import java.util.Map;

public interface AlumnoDao {

    Alumno saveAlumno(Alumno a) throws AlumnoBadRequestException;

    Alumno findAlumno(String apellidoAlumno) throws AlumnoNotFoundException;


    Alumno buscarPorId(int id) throws AlumnoNotFoundException;

    Alumno borrarAlumno(Alumno alumno) throws AlumnoNotFoundException;

    Alumno editarAlumno(int id, Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, MateriaBadRequestException, AlumnoBadRequestException;


    Asignatura perderRegularidad(Alumno alumno, int idAsignatura) throws AsignaturaBadRequestException, AsignaturaNotFoundException;

    Asignatura cursarAsignatura(Alumno a, int materiaId) throws AsignaturaNotFoundException;

    Asignatura buscarAsignatura(int idAsignatura, Alumno alumno) throws AsignaturaNotFoundException;
    public Asignatura aprobarAsignatura(Alumno alumno, int idAsignatura, int nota) throws CorrelatividadException, MateriaBadRequestException, AlumnoNotFoundException, AsignaturaNotFoundException;
}
