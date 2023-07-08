package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;

import java.util.List;

public interface CarreraDao {
    Carrera crearCarrera(Carrera carrera);
    String generarCodigo();

    List<Carrera> getAllCarreras();

    Carrera getCarreraById(int idCarrera);

    Carrera agregarMateria(Materia m, Carrera idCarrera);
}
