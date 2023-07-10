package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private CarreraDao carreraDao;


    @Override
    public Carrera crearCarrera(CarreraDto carreraDto) {
        Carrera carrera = new Carrera();
        List<Materia> materias = new ArrayList<>();

        carrera.setNombre(carreraDto.getNombre());
        carrera.setCantidadCuatrimestres(carreraDto.getCantidadCuatrimestres());
        carrera.setIdDepartamento(carreraDto.getDepartamentoId());

        if (carreraDto.getMateriaIds() != null){
            for(int i = 0; i < carreraDto.getMateriaIds().size(); i ++){
                Materia m = materiaService.getMateriaById(carreraDto.getMateriaIds().get(i));
                materias.add(m);
            }
            return carreraDao.crearCarreraConMaterias(materias, carrera);
        }
        else{
            return carreraDao.crearCarrera(carrera);
        }
    }

    @Override
    public List<Carrera> getAllCarreras() {
        return carreraDao.getAllCarreras();
    }

    @Override
    public Carrera getCarreraById(int idCarrera) {
        return carreraDao.getCarreraById(idCarrera);
    }

    @Override
    public Carrera agregarMateria(int idCarrera, int idMateria) {
        Carrera carrera = carreraDao.getCarreraById(idCarrera);
        Materia materia = materiaService.getMateriaById(idMateria);
        return carreraDao.agregarMateria(materia, carrera);
    }

    @Override
    public Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera) {
        if (nuevosDatos != null) return carreraDao.modificarCarrera(nuevosDatos, idCarrera);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe incluir datos a modificar");
    }
}
