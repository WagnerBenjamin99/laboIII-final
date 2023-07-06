package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public List<Materia> getMaterias() {
        return materiaService.getAllMaterias();
    }

    @PostMapping("/")
    public Materia crearMateria(@RequestBody MateriaDto materiaDto) {
        return materiaService.crearMateria(materiaDto);
    }

    @GetMapping("/{idMateria}")
    public Materia getMateriaById(@PathVariable Integer idMateria) throws MateriaNotFoundException {
        return materiaService.getMateriaById(idMateria);
    }

    @DeleteMapping("/{idMateria}")
    public Materia borrarMateria(@PathVariable Integer idMateria) throws MateriaNotFoundException {
        return materiaService.borrarMateria(idMateria);
    }

    @PatchMapping("/{idMateria}")
    public Materia modificarMateria(@PathVariable int idMateria, @RequestBody Map<String, Object> nuevosDatos){
        return materiaService.modificarMateria(nuevosDatos, idMateria);
    }

    @GetMapping("/")
    public List<Materia> getMateriasOrdenadas(@RequestParam String ordenamiento){
        return materiaService.ordenarMaterias(ordenamiento);
    }
}
