package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.CarreraService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    private MateriaDao dao;

    @Autowired
    private ProfesorService profesorService;

    private CarreraService carreraService;

    public MateriaServiceImpl(@Lazy CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @Override
    public Materia crearMateria(MateriaDto materia) throws IllegalArgumentException{
        Materia m = new Materia();
        m.setNombre(materia.getNombre());
        m.setAnio(materia.getAnio());
        m.setCuatrimestre(materia.getCuatrimestre());
        m.setProfesor(profesorService.buscarProfesor(materia.getProfesorId()));


        return dao.save(m);
    }

    @Override
    public List<Materia> getAllMaterias() throws MateriaNotFoundException {
        return dao.getAllMaterias();
    }

    @Override
    public Materia getMateriaById(int idMateria) throws MateriaNotFoundException {
        return dao.findById(idMateria);
    }

    @Override
    public Materia borrarMateria(Integer idMateria) throws MateriaNotFoundException {
        Materia materia = dao.findById(idMateria);
        return dao.borrarMateria(materia);
    }

    @Override
    public Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria) throws MateriaNotFoundException, MateriaBadRequestException {
        if (nuevosDatos != null) return dao.modificarMateria(nuevosDatos, idMateria);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe incluir datos a modificar");
    }

    @Override
    public List<Materia> ordenarMaterias(String ordenamiento) throws MateriaBadRequestException {
        return dao.ordenarMaterias(ordenamiento);
    }

    @Override
    public Materia filtrarPorNombre(String nombre) throws MateriaBadRequestException {
        return dao.filtrarPorNombre(nombre);
    }

    @Override
    public Materia asignarCarrera(Carrera c, Materia m) {
        return null;
    }


}
