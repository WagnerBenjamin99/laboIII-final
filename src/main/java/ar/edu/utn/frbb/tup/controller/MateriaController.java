package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
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
    public List<Materia> getMaterias() throws MateriaNotFoundException {
        return materiaService.getAllMaterias();
    }

    @PostMapping("/")
    public Materia crearMateria(@RequestBody MateriaDto materiaDto) {
        return materiaService.crearMateria(materiaDto);
    }

    @GetMapping("/{id}")
    public Materia getMateriaById(@PathVariable Integer id) throws MateriaNotFoundException {
        return materiaService.getMateriaById(id);
    }

    @DeleteMapping("/{id}")
    public Materia borrarMateria(@PathVariable Integer id) throws MateriaNotFoundException {
        return materiaService.borrarMateria(id);
    }

    @PatchMapping("/{id}")
    public Materia modificarMateria(@PathVariable int id, @RequestBody Map<String, Object> nuevosDatos) throws MateriaNotFoundException, MateriaBadRequestException {
        if(nuevosDatos.size() == 0 || nuevosDatos == null) throw new MateriaBadRequestException("Campos vacios");
        return materiaService.modificarMateria(nuevosDatos, id);
    }

    @GetMapping("/")
    public List<Materia> getMateriasOrdenadas(@RequestParam String ordenamiento) throws MateriaBadRequestException {
        return materiaService.ordenarMaterias(ordenamiento);
    }

    @GetMapping("/filtro")
    public Materia filtrarPorNombre(@RequestParam String nombre) throws MateriaBadRequestException {
        return materiaService.filtrarPorNombre(nombre);
    }
}
