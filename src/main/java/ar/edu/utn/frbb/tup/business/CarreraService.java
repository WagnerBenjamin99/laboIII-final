package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;

import java.util.List;
import java.util.Map;

public interface CarreraService {
    Carrera crearCarrera(CarreraDto carreraDto);

    List<Carrera> getAllCarreras();

    Carrera getCarreraById(int idCarrera);

    Carrera agregarMateria(int idCarrera, int idMateria);

    Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera);
}
