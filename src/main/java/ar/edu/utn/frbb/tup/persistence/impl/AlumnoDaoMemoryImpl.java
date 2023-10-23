package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    private Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        Profesor p  = new Profesor("Luciano", "Salotto", "Lic.");
        Materia m = new Materia("Labo II", 2023, 1, p);
        Materia m2 = new Materia("Labo III", 2023, 1, p);
        m2.agregarCorrelatividad(m);
        m2.setMateriaId(1);

        Random random = new Random();
        alumno.setId(random.nextLong(10));

        Asignatura a = new Asignatura(m);
        Asignatura a2 = new Asignatura(m2);


        alumno.agregarAsignatura(a);
        alumno.agregarAsignatura(a2);

        return repositorioAlumnos.put(alumno.getDni(), alumno);
    }

    @Override
    public Alumno findAlumno(String apellidoAlumno) throws AlumnoNotFoundException {
        for (Alumno a: repositorioAlumnos.values()) {
            if (a.getApellido().equals(apellidoAlumno)){
                return a;
            }
        }
        throw new AlumnoNotFoundException("Alumno no encontrado");
    }


    @Override
    public Alumno buscarPorId(int id) throws AlumnoNotFoundException {
        Alumno filtrado = null;
        for (Alumno a: repositorioAlumnos.values()){
            if (a.getId() == id)filtrado = a;
        }
        if (filtrado == null) {
            throw new AlumnoNotFoundException("Alumno no encontrado");
        }
        return filtrado;
    }

    @Override
    public Alumno borrarAlumno(Alumno alumno) throws AlumnoNotFoundException {
        Boolean flag = repositorioAlumnos.remove(alumno.getDni(), alumno);
        if (flag) return alumno;
        throw new AlumnoNotFoundException("No se encontro el alumno que desea eliminar");
    }

    public Alumno editarAlumno(int id, Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, AlumnoBadRequestException {
        Alumno a = buscarPorId(id);
        if (a == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno que desea editar");
        } else {
            for (Map.Entry<String, Object> entry : nuevosDatos.entrySet()) {
                String campo = entry.getKey();
                Object valor = entry.getValue();
                if (campo.equals("nombre")) {
                    a.setNombre((String) valor);
                } else if (campo.equals("apellido")) {
                    a.setApellido((String) valor);
                } else if (campo.equals("dni")) {
                    a.setDni((Long) valor);
                } else {

                    throw new AlumnoBadRequestException("Campo incorrecto o nulo");
                }
            }
        }
        return a;
    }


    @Override
    public Asignatura perderRegularidad(Alumno alumno, int idAsignatura) {
        for (Asignatura a : alumno.obtenerListaAsignaturas()){
            if (a.getMateria().getId() == idAsignatura){
                a.setEstado(EstadoAsignatura.NO_CURSADA);
                return a;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asignatura no encontrada.");
    }

    @Override
    public Asignatura cursarAsignatura(Alumno alumno, int idAsignatura) {
        for (Asignatura a : alumno.obtenerListaAsignaturas()){
            if (a.getMateria().getId() == idAsignatura) a.setEstado(EstadoAsignatura.CURSADA);
            return a;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asignatura no encontrada");
    }

    @Override
    public Asignatura buscarAsignatura(int idAsignatura, Alumno alumno) {
        for (Asignatura a : alumno.obtenerListaAsignaturas()){
            if (a.getMateria().getId() == idAsignatura) return a;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asignatura no encontrada");
    }



    @Override
    public Asignatura aprobarAsignatura(Alumno alumno, int idAsignatura, int nota) throws MateriaBadRequestException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Asignatura actualizada = new Asignatura();
        if(alumno.obtenerListaAsignaturas().size()!=0) {
            for (Asignatura a : alumno.obtenerListaAsignaturas()) {

                if (a.getMateria().getId() == idAsignatura) {
                    a.setEstado(EstadoAsignatura.APROBADA);
                    a.setNota(nota);
                    return a;
                }

            }
            throw new AsignaturaNotFoundException("Asignatura no encontrada");
        }throw new AsignaturaNotFoundException("El alumno no esta inscripto en ninguna materia");
    }
}
