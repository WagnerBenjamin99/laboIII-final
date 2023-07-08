package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    private static Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        Random random = new Random();
        alumno.setId(random.nextLong(10));
        return repositorioAlumnos.put(alumno.getDni(), alumno);
    }

    @Override
    public Alumno findAlumno(String apellidoAlumno) {
        for (Alumno a: repositorioAlumnos.values()) {
            if (a.getApellido().equals(apellidoAlumno)){
                return a;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen alumnos con esos datos."
        );
    }

    @Override
    public Alumno loadAlumno(Long dni) {
        return null;
    }

    @Override
    public Alumno buscarPorId(int id) {
        Alumno filtrado = null;
        for (Alumno a: repositorioAlumnos.values()){
            if (a.getId() == id)filtrado = a;
        }
        if (filtrado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el alumno que desea eliminar");
        }
        return filtrado;
    }

    @Override
    public Alumno borrarAlumno(Alumno alumno) {
        Boolean flag = repositorioAlumnos.remove(alumno.getDni(), alumno);
        if (flag) return alumno;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar");
    }

    @Override
    public Alumno editarAlumno(int id, Map<String, Object> nuevosDatos) {
        Alumno a = buscarPorId(id);
        if (a == null)  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el alumno que desea editar");
        else{
            nuevosDatos.forEach((campo, valor) -> {

                if (campo.equals("nombre")) {
                    a.setNombre((String) valor);
                } else if (campo.equals("apellido")) {
                    a.setApellido((String) valor);
                } else if (campo.equals("dni")) {
                    a.setDni((Long) valor);
                }
                else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos proporcionados son incorrectos");;
            });
        }
        return a;
    }

}
