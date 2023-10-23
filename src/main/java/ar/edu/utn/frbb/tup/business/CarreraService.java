package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import java.util.List;
import java.util.Map;

public interface CarreraService {
    Carrera crearCarrera(CarreraDto carreraDto) throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException;

    List<Carrera> getAllCarreras() throws CarreraNotFoundException;

    Carrera getCarreraById(int idCarrera) throws CarreraNotFoundException;

    Carrera agregarMateria(int idCarrera, int idMateria) throws MateriaNotFoundException, CarreraNotFoundException, CarreraBadRequestException;

    Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera) throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException;

    Carrera eliminarCarrera(int idCarrera) throws MateriaNotFoundException, CarreraNotFoundException;
}
