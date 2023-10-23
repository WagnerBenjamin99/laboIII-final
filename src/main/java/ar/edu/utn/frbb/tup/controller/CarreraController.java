package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.dto.CarreraDto;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;


    @PostMapping("/")
    public Carrera crearCarrera(@RequestBody CarreraDto carreraDto) throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        return carreraService.crearCarrera(carreraDto);
    }

    @GetMapping
    public List<Carrera> getAllCarreras() throws CarreraNotFoundException {
        return carreraService.getAllCarreras();
    }

    @GetMapping("/{idCarrera}")
    public Carrera getCarreraById(@PathVariable int idCarrera) throws CarreraNotFoundException {
        return carreraService.getCarreraById(idCarrera);
    }

    @PutMapping("/{idCarrera}/materias/{idMateria}")
    public Carrera agregarMateria(@PathVariable int idCarrera, @PathVariable int idMateria) throws MateriaNotFoundException, CarreraNotFoundException, CarreraBadRequestException {
        return carreraService.agregarMateria(idCarrera, idMateria);
    }

    @PatchMapping("/{idCarrera}")
    public Carrera modificarCarrera(@PathVariable int idCarrera, @RequestBody Map<String, Object> nuevosDatos) throws MateriaNotFoundException, CarreraBadRequestException, CarreraNotFoundException {
        return carreraService.modificarCarrera(nuevosDatos, idCarrera);
    }

    @DeleteMapping("/{idCarrera}")
    public Carrera eliminarCarrera(@PathVariable int  idCarrera) throws MateriaNotFoundException, CarreraNotFoundException {
        return carreraService.eliminarCarrera(idCarrera);
    }
}
