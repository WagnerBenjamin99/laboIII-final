package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    private MateriaDao dao;

    @Autowired
    private ProfesorService profesorService;

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
    public List<Materia> getAllMaterias() {
        return dao.getAllMaterias();
    }

    @Override
    public Materia getMateriaById(int idMateria){
        return dao.findById(idMateria);
    }

    @Override
    public Materia borrarMateria(Integer idMateria){
        Materia materia = dao.findById(idMateria);
        return dao.borrarMateria(materia);
    }

    @Override
    public Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria) {
        if (nuevosDatos != null) return dao.modificarMateria(nuevosDatos, idMateria);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe incluir datos a modificar");
    }

    @Override
    public List<Materia> ordenarMaterias(String ordenamiento) {
        return dao.ordenarMaterias(ordenamiento);
    }
}
