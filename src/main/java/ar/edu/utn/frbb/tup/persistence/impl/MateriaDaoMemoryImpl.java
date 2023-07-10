package ar.edu.utn.frbb.tup.persistence.impl;


import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    private static final Map<Integer, Materia> repositorioMateria = new HashMap<>();
    private static final Set<String> codigosUsados = new HashSet<>();



    @Override
    public Materia save(Materia materia) {
        Random random = new Random();
        materia.setMateriaId(random.nextInt(20));
        materia.setCodigo(generarCodigo());
        repositorioMateria.put(materia.getId(), materia);
        return materia;
    }

    @Override
    public Materia findById(int idMateria){
        for (Materia m:
             repositorioMateria.values()) {
            if (idMateria == m.getId()) {
                return m;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro la materia que busca");
    }

    @Override
    public Materia borrarMateria(Materia materia) {
        if (repositorioMateria.remove(materia.getId(), materia))return materia;
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo eliminar la materia");
    }

    @Override
    public List<Materia> getAllMaterias() {
        ArrayList<Materia> mats = new ArrayList<>();

        if (repositorioMateria.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay materias disponibles");
        }
        for (Materia mat : repositorioMateria.values())mats.add(mat);
      return mats;
    }

    @Override
    public Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria) {
        Materia a = findById(idMateria);
        nuevosDatos.forEach((campo, valor) -> {

            if (campo.equals("nombre")) {
                a.setNombre((String) valor);
            } else if (campo.equals("anio")) {
                a.setAnio((Integer) valor);
            } else if (campo.equals("cuatrimestre")) {
                a.setCuatrimestre((Integer) valor);
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los datos proporcionados son incorrectos");;
        });

        return a;
    }

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
    public List<Materia> ordenarMaterias(String ordenamiento) {

        ArrayList<Materia> materias = new ArrayList<>(repositorioMateria.values());

        switch (ordenamiento) {
            case "nombre_asc":
                materias.sort(Comparator.comparing(Materia::getNombre));
                break;
            case "nombre_desc":
                materias.sort(Comparator.comparing(Materia::getNombre).reversed());
                break;
            case "codigo_asc":
                materias.sort(Comparator.comparing(Materia::getCodigo));
                break;
            case "codigo_desc":
                materias.sort(Comparator.comparing(Materia::getCodigo).reversed());
                break;
            default:
                materias.sort(Comparator.comparing(Materia::getNombre));
                break;
        }
        return materias;
    }

    @Override
    public Materia filtrarPorNombre(String nombre) {

        for (Materia m: repositorioMateria.values()) {
            if (m.getNombre().equals(nombre)){
                return m;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen materias con ese nombre."
        );
    }

    @Override
    public Materia asignarCarrera(Carrera c, Materia m) {
        for (Materia materia : repositorioMateria.values()){
            if (m.equals(materia))materia.setCarrera(c);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Materia inexistente"
        );
    }
}
