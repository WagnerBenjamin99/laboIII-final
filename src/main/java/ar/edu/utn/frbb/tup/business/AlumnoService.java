package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;

import java.util.Map;

public interface AlumnoService {
    Asignatura aprobarAsignatura(int materiaId, int nota, int idAlumno) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException, AlumnoNotFoundException, CorrelatividadException, MateriaBadRequestException, AsignaturaNotFoundException, AlumnoBadRequestException, AsignaturaBadRequestException;

    Alumno crearAlumno(AlumnoDto alumno) throws AlumnoBadRequestException;

    Alumno buscarAlumno(String apellidoAlumno) throws AlumnoNotFoundException, AlumnoBadRequestException;

    Alumno buscarPorId(int id) throws AlumnoNotFoundException, AlumnoBadRequestException;

    Alumno borrarAlumno(int id) throws AlumnoNotFoundException;

    Alumno editarAlumno(int id, Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, MateriaBadRequestException, AlumnoBadRequestException;


    Asignatura buscarAsignatura(int idAsignatura, Alumno a) throws AsignaturaNotFoundException;

    Asignatura cursarAsignatura(int idAlumno, int idAsignatura) throws AlumnoNotFoundException, AlumnoBadRequestException, AsignaturaNotFoundException;

    Asignatura recursarAsignatura(int idAlumno, int idAsignatura) throws AlumnoNotFoundException, AlumnoBadRequestException, AsignaturaNotFoundException, AsignaturaBadRequestException;

}
