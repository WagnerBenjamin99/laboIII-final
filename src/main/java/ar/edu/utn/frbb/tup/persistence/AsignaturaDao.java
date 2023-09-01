package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Asignatura;

public interface AsignaturaDao {
    Asignatura getAsignatura(int materiaId, long dni);
}
