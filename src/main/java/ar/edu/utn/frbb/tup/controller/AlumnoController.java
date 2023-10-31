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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/{idAlumno}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable int idAlumno) throws AlumnoNotFoundException, AlumnoBadRequestException {
        Alumno alumno = alumnoService.borrarAlumno(idAlumno);
        return ResponseEntity.ok(alumno);
    }

    @PostMapping("/")
    public ResponseEntity<Alumno> crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        Alumno alumno = alumnoService.crearAlumno(alumnoDto);
        return ResponseEntity.ok(alumno);

    }
    @GetMapping
    public ResponseEntity<Alumno> buscarAlumno(@RequestParam String apellido) throws AlumnoNotFoundException, AlumnoBadRequestException {
        Alumno alumno = alumnoService.buscarAlumno(apellido);
        return ResponseEntity.ok(alumno);
    }

    @DeleteMapping("/")
    public ResponseEntity<Alumno> borrarAlumno(@RequestParam int id) throws AlumnoNotFoundException {
        Alumno alumno = alumnoService.borrarAlumno(id);
        return ResponseEntity.ok(alumno);
    }

    @PatchMapping("/{idAlumno}")
    public ResponseEntity<Alumno> editarAlumno(@PathVariable int id, @RequestBody Map<String, Object> nuevosDatos) throws AlumnoNotFoundException, MateriaBadRequestException, AlumnoBadRequestException {
        Alumno alumno = alumnoService.editarAlumno(id, nuevosDatos);
        return ResponseEntity.ok(alumno);
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<Asignatura> pasarNota(@PathVariable int idAlumno, @PathVariable int idAsignatura,
                                @RequestParam(required = false, defaultValue = "6") int nota,
                                @RequestParam char estadoAsignatura ) throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AlumnoNotFoundException, CorrelatividadException, MateriaBadRequestException, AsignaturaNotFoundException, AlumnoBadRequestException {

        switch (estadoAsignatura){
            case 'A': return ResponseEntity.ok( alumnoService.aprobarAsignatura(idAsignatura,  idAlumno, nota) );
            case 'C': return  ResponseEntity.ok( alumnoService.cursarAsignatura(idAlumno, idAsignatura) );
            default: return ResponseEntity.ok( alumnoService.recursarAsignatura(idAlumno, idAsignatura) );
        }
    }

   
}
