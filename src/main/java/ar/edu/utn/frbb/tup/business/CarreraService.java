package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface CarreraService {
    Carrera crearCarrera(CarreraDto carreraDto);

    List<Carrera> getAllCarreras();

    Carrera getCarreraById(int idCarrera);

    Carrera agregarMateria(int idCarrera, int idMateria);

    Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera);

    Carrera eliminarCarrera(int idCarrera) throws MateriaNotFoundException;
}
