package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;


    @PostMapping("/")
    public Carrera crearCarrera(@RequestBody CarreraDto carreraDto){
        return carreraService.crearCarrera(carreraDto);
    }

    @GetMapping
    public List<Carrera> getAllCarreras(){
        return carreraService.getAllCarreras();
    }

    @GetMapping("/{idCarrera}")
    public Carrera getCarreraById(@PathVariable int idCarrera){
        return carreraService.getCarreraById(idCarrera);
    }

    @PatchMapping("/{idCarrera}/materias/{idMateria}")
    public Carrera agregarMateria(@PathVariable int idCarrera, @PathVariable int idMateria){
        return carreraService.agregarMateria(idCarrera, idMateria);
    }
}
