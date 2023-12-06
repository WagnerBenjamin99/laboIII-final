package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<Materia>> getMaterias() throws MateriaNotFoundException {
        List<Materia> materias =  materiaService.getAllMaterias();
        return ResponseEntity.ok(materias);
    }

    @PostMapping("/")
    public ResponseEntity<Materia> crearMateria(@RequestBody MateriaDto materiaDto) throws MateriaBadRequestException {
        Materia materia = materiaService.crearMateria(materiaDto);
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> getMateriaById(@PathVariable Integer id) throws MateriaNotFoundException {
        Materia materia = materiaService.getMateriaById(id);
        return ResponseEntity.ok(materia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Materia> borrarMateria(@PathVariable Integer id) throws MateriaNotFoundException {
        Materia materia = materiaService.borrarMateria(id);
        return ResponseEntity.ok(materia);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Materia> modificarMateria(@PathVariable int id, @RequestBody Map<String, Object> nuevosDatos) throws MateriaNotFoundException, MateriaBadRequestException {
        if(nuevosDatos.size() == 0 || nuevosDatos == null) throw new MateriaBadRequestException("Campos vacios");
        Materia materia = materiaService.modificarMateria(nuevosDatos, id);
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/")
    public ResponseEntity<List<Materia>> getMateriasOrdenadas(@RequestParam String ordenamiento) throws MateriaBadRequestException {
        List<Materia> materias = materiaService.ordenarMaterias(ordenamiento);
        return ResponseEntity.ok(materias);
    }

}
