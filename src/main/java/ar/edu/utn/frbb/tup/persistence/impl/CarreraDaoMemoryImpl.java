package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;


@Repository
public class CarreraDaoMemoryImpl implements CarreraDao {
    private static final Map<Integer, Carrera> repositorioCarrea = new HashMap<>();
    private static final Set<String> codigosUsados = new HashSet<>();

    @Autowired
    private MateriaService materiaService;
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
        c.agregarMateria(m);
        return repositorioCarrea.put(c.getId(), c);
    }

    @Override
    public Carrera crearCarreraConMaterias(List<Materia> materias, Carrera carrera)  {
        Random r = new Random();
        carrera.setId(r.nextInt(20));
        carrera.setCodigo(generarCodigo());
        repositorioCarrea.put(carrera.getId(), carrera);

        for (Materia m : materias)agregarMateria(m, carrera);
        return carrera;
    }

    @Override
    public Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera) {
        Carrera c = getCarreraById(idCarrera);
        nuevosDatos.forEach((campo, valor) -> {

            if (campo.equals("nombre")) {
                c.setNombre((String) valor);
            } else if (campo.equals("departamentoId")) {
                c.setIdDepartamento((Integer) valor);
            } else if (campo.equals("cantidadCuatrimestres")) {
                c.setCantidadCuatrimestres((Integer) valor);
            }
            else if (campo.equals("materiaIds")){
                for (Integer idMateria : (ArrayList<Integer>) valor) {
                    Materia m = materiaService.getMateriaById(idMateria);
                    agregarMateria(m, getCarreraById(idCarrera));
                }
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos proporcionados son incorrectos");;
        });

        return c;
    }

    @Override
    public Carrera crearCarrera(Carrera carrera) {
        Random r = new Random();
        carrera.setCodigo(generarCodigo());
        carrera.setId(r.nextInt(20));
        repositorioCarrea.put(carrera.getId(), carrera);
        return carrera;
    }
}
