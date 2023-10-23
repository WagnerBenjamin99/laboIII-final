package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface CarreraDao {
    Carrera crearCarrera(Carrera carrera);
    String generarCodigo();

    List<Carrera> getAllCarreras() throws CarreraNotFoundException;

    Carrera getCarreraById(int idCarrera) throws CarreraNotFoundException;

    Carrera agregarMateria(Materia m, Carrera idCarrera) throws CarreraNotFoundException, CarreraBadRequestException;

    Carrera crearCarreraConMaterias(List<Materia> materias, Carrera carrera) throws CarreraBadRequestException, CarreraNotFoundException;

    Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera) throws CarreraNotFoundException, CarreraBadRequestException, MateriaNotFoundException;

    Carrera eliminarCarrera(Carrera carrera) throws CarreraNotFoundException;
}
