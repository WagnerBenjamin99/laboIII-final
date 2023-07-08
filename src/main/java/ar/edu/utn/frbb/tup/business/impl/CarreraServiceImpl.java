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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private CarreraDao carreraDao;


    @Override
    public Carrera crearCarrera(CarreraDto carreraDto) {
        Carrera carrera;

        if (carreraDto.getMateriaIds() != null){
            Carrera.CarreraStrategy carreraStrategy = new Carrera.CarreraConMaterias();
            Set<Materia> materias = new HashSet<>();
            Materia m;
            for (int i = 0; i < carreraDto.getMateriaIds().size(); i ++){
               m = materiaService.getMateriaById(carreraDto.getMateriaIds().get(i));
               materias.add(m);
            }
            carrera = carreraStrategy.crearCarrera(carreraDto.getNombre(), carreraDto.getCantidadCuatrimestres(),
                    carreraDto.getDepartamentoId(), materias);
        }
        else{
            Carrera.CarreraStrategy carreraStrategy = new Carrera.CarreraSinMaterias();
            carrera = carreraStrategy.crearCarrera(carreraDto.getNombre(), carreraDto.getCantidadCuatrimestres(),
                    carreraDto.getDepartamentoId(), new HashSet<>());
        }


        return carreraDao.crearCarrera(carrera);
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
}
