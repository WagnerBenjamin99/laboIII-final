package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;

import java.util.Map;

public interface AlumnoDao {

    Alumno saveAlumno(Alumno a);

    Alumno findAlumno(String apellidoAlumno);

    Alumno loadAlumno(Long dni);

    Alumno buscarPorId(int id);

    Alumno borrarAlumno(Alumno alumno);

    Alumno editarAlumno(int id, Map<String, Object> nuevosDatos);
}
