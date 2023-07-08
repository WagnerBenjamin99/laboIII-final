package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface MateriaService {
    Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException;

    List<Materia> getAllMaterias();

    Materia getMateriaById(int idMateria);

    Materia borrarMateria(Integer idMateria) throws MateriaNotFoundException;

    Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria);

    List<Materia> ordenarMaterias(String ordenamiento);

    Materia filtrarPorNombre(String nombre);

    Materia asignarCarrera(Carrera c, Materia m);
}
