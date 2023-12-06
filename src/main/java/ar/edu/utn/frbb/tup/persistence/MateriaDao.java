package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface MateriaDao {
    Materia save(Materia materia) throws MateriaBadRequestException;

    Materia findById(int idMateria) throws MateriaNotFoundException;

    Materia borrarMateria(Materia materia) throws MateriaNotFoundException;

    List<Materia> getAllMaterias() throws MateriaNotFoundException;

    Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria) throws MateriaNotFoundException, MateriaBadRequestException;

    String generarCodigo();

    List<Materia> ordenarMaterias(String ordenamiento) throws MateriaBadRequestException;

    Materia asignarCarrera(Carrera c, Materia m) throws MateriaBadRequestException;
}
