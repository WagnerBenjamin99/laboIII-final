package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Repository
public class CarreraDaoMemoryImpl implements CarreraDao {
    private static final Map<Integer, Carrera> repositorioCarrea = new HashMap<>();
    private static final Set<String> codigosUsados = new HashSet<>();

    @Override
    public String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        String codigo = "";
        Random r = new Random();
        Boolean flag = true;

        while(flag) {
            for (int i = 0; i <= 5; i++) codigo += caracteres.toCharArray()[r.nextInt(36)];
            if (!codigosUsados.contains(codigo)) {
                codigosUsados.add(codigo);
                flag = false;
            }
        }
        return codigo;
    }

    @Override
    public List<Carrera> getAllCarreras() {
        List<Carrera> carreras = new ArrayList<>(repositorioCarrea.values());
        return carreras;
    }

    @Override
    public Carrera getCarreraById(int idCarrera) {
        for (Carrera c:
                repositorioCarrea.values()) {
            if (idCarrera == c.getId()) {
                return c;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro la carrera que busca");
    }

    @Override
    public Carrera agregarMateria(Materia m, Carrera c) {
        Carrera carreraActualizada;
        repositorioCarrea.get(c.getId()).agregarMateria(m);
        carreraActualizada = repositorioCarrea.get(c.getId());
        return carreraActualizada;
    }

    @Override
    public Carrera crearCarrera(Carrera carrera) {
        carrera.setCodigo(generarCodigo());
        repositorioCarrea.put(carrera.getId(), carrera);
        return carrera;
    }
}
