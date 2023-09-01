package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/{idAlumno}")
    public Alumno getAlumno(@PathVariable int idAlumno) throws AlumnoNotFoundException {
        System.out.println("ACA");
        return alumnoService.buscarPorId(idAlumno);
    }

    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {

        return alumnoService.crearAlumno(alumnoDto);

    }
    @GetMapping
    public Alumno buscarAlumno(@RequestParam String apellido) throws AlumnoNotFoundException {

       return alumnoService.buscarAlumno(apellido);
    }

    @DeleteMapping("/")
    public Alumno borrarAlumno(@RequestParam int id) throws AlumnoNotFoundException {
        return alumnoService.borrarAlumno(id);
    }

    @PatchMapping("/{idAlumno}")
    public Alumno editarAlumno(@PathVariable int id, @RequestBody Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, MateriaBadRequestException, AlumnoBadRequestException {
        return alumnoService.editarAlumno(id, nuevosDatos);
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public Asignatura pasarNota(@PathVariable int idAlumno, @PathVariable int idAsignatura,
                                @RequestParam(value = "6", required = false, defaultValue = "6") int nota,
                                @RequestParam char estadoAsignatura ) throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AlumnoNotFoundException, CorrelatividadException, MateriaBadRequestException, AsignaturaNotFoundException {

        switch (estadoAsignatura){
            case 'A': return  alumnoService.aprobarAsignatura(idAsignatura,  idAlumno, nota);
            case 'C': return  alumnoService.cursarAsignatura(idAlumno, idAsignatura);
            default: return alumnoService.recursarAsignatura(idAlumno, idAsignatura);
        }
    }

   
}
