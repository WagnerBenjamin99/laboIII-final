package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AsignaturaDaoMemoryImpl implements AsignaturaDao {

    private static final Map<Integer, Asignatura> repositorioAsignatura = new HashMap<>();
    @Override
    public Asignatura getAsignatura(int materiaId, long dni) {
        return null;
    }
}
