package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.business.exception.AsignaturaBadRequestException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Random;

@Component
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoDao alumnoDao;

    @Autowired
    private AsignaturaService asignaturaService;


    @Override
    public Asignatura aprobarAsignatura(int materiaId, int idAlumno, int nota) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException, AlumnoNotFoundException, CorrelatividadException, MateriaBadRequestException, AsignaturaNotFoundException, AlumnoBadRequestException, AsignaturaBadRequestException {
        Alumno alumno = buscarPorId(idAlumno);
        Asignatura asignatura = buscarAsignatura(materiaId, alumno);
        if (nota >= 6 && nota <= 10) {
            if(asignatura.getMateria().getCorrelatividades().size() != 0) {
                for (Materia correlativa :
                        asignatura.getMateria().getCorrelatividades()) {
                    chequearCorrelatividad(correlativa, alumno);
                }
                return alumnoDao.aprobarAsignatura(alumno, materiaId, nota);
            }
            else return alumnoDao.aprobarAsignatura(alumno, materiaId, nota);

        }
        else throw new AsignaturaBadRequestException("Nota incorrecta, debe estar en entre 6 y 10 para aprobar");
    }

    @Override
    public Alumno crearAlumno(AlumnoDto alumno) throws AlumnoBadRequestException {
        Alumno a = new Alumno();
        a.setNombre(alumno.getNombre());
        a.setApellido(alumno.getApellido());
        a.setDni(alumno.getDni());
        Random random = new Random();
        a.setId(random.nextLong());
        alumnoDao.saveAlumno(a);
        return a;
    }

    @Override
    public Alumno buscarAlumno(String apellido) throws AlumnoNotFoundException, AlumnoBadRequestException {
        if (apellido == null || apellido.length() == 0) throw new AlumnoBadRequestException("El apellido es nulo o vacio");
        return alumnoDao.findAlumno(apellido);
    }

    public Alumno buscarPorId(int id) throws AlumnoNotFoundException, AlumnoBadRequestException {
        if(id >= 0) return alumnoDao.buscarPorId(id);
        throw new AlumnoBadRequestException("El id no puede ser negativo");
    }

    @Override
    public Alumno borrarAlumno(int id) throws AlumnoNotFoundException {
        Alumno alumno = alumnoDao.buscarPorId(id);

        if (alumno == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el alumno que desea eliminar");
        return alumnoDao.borrarAlumno(alumno);
    }

    @Override
    public Alumno editarAlumno(int id, Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, AlumnoBadRequestException, MateriaBadRequestException {
        if (nuevosDatos.size()==0)throw new AlumnoBadRequestException("Debe incluir datos a modificar");
        else return alumnoDao.editarAlumno(id, nuevosDatos);
    }

    void chequearCorrelatividad(Materia correlativa, Alumno alumno) throws CorrelatividadesNoAprobadasException {
        for (Asignatura a: alumno.obtenerListaAsignaturas()) {
            if (correlativa.getNombre().equals(a.getNombreAsignatura())) {
                if (!EstadoAsignatura.APROBADA.equals(a.getEstado())) {
                    throw new CorrelatividadesNoAprobadasException("No aprobo " + a.getNombreAsignatura());
                }
            }
        }
    }


    @Override
    public Asignatura buscarAsignatura(int idAsignatura, Alumno a) throws AsignaturaNotFoundException {
        return alumnoDao.buscarAsignatura(idAsignatura, a);
    }

    @Override
    public Asignatura cursarAsignatura(int idAlumno, int idAsignatura) throws AlumnoNotFoundException, AlumnoBadRequestException, AsignaturaNotFoundException {
        Alumno a = buscarPorId(idAlumno);
        return alumnoDao.cursarAsignatura(a, idAsignatura);
    }

    @Override
    public Asignatura recursarAsignatura(int idAlumno, int idAsignatura) throws AlumnoNotFoundException, AlumnoBadRequestException, AsignaturaNotFoundException, AsignaturaBadRequestException {
        Alumno alumno = buscarPorId(idAlumno);
        Asignatura asignatura = buscarAsignatura(idAsignatura, alumno);

        return alumnoDao.perderRegularidad(alumno, idAsignatura);
    }


}
