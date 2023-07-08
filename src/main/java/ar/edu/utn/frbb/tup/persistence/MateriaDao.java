package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;

import java.util.List;
import java.util.Map;

public interface MateriaDao {
    Materia save(Materia materia);

    Materia findById(int idMateria);

    Materia borrarMateria(Materia materia);

    List<Materia> getAllMaterias();

    Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria);

    String generarCodigo();

    List<Materia> ordenarMaterias(String ordenamiento);

    Materia filtrarPorNombre(String nombre);

    Materia asignarCarrera(Carrera c, Materia m);
}
