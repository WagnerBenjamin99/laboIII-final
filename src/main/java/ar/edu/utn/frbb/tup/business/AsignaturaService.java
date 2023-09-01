package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaService {
    Asignatura getAsignatura(int materiaId, long dni);

    void actualizarAsignatura(Asignatura a);


}
