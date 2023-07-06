package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {

        return alumnoService.crearAlumno(alumnoDto);

    }
    @GetMapping
    public Alumno buscarAlumno(@RequestParam String apellido) {

       return alumnoService.buscarAlumno(apellido);
    }

    @DeleteMapping("/")
    public Alumno borrarAlumno(@RequestParam int id){
        return alumnoService.borrarAlumno(id);
    }

    @PatchMapping("/")
    public Alumno editarAlumno(@RequestParam int id, @RequestBody Map<String, Object> nuevosDatos){
        return alumnoService.editarAlumno(id, nuevosDatos);
    }
}
