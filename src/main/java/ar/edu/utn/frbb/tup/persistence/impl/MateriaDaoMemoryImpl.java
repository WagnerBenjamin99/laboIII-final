package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    private Map<Integer, Materia> repositorioMateria = new HashMap<>();
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
    public Materia findById(int idMateria) throws MateriaNotFoundException {
        for (Materia m:
             repositorioMateria.values()) {
            if (idMateria == m.getId()) {
                return m;
            }
        }
        throw new MateriaNotFoundException("Materia no encontrada");
    }

    @Override
    public Materia borrarMateria(Materia materia) throws MateriaNotFoundException {
        if (repositorioMateria.remove(materia.getId(), materia))return materia;
        throw new MateriaNotFoundException("No se encontro la materia que desea eliminar");
    }

    @Override
    public List<Materia> getAllMaterias() throws MateriaNotFoundException {
        ArrayList<Materia> mats = new ArrayList<>();

        if (repositorioMateria.size() == 0) {
            throw new MateriaNotFoundException("No hay materias disponibles");
        }
        for (Materia mat : repositorioMateria.values())mats.add(mat);
        return mats;
    }

    @Override
    public Materia modificarMateria(Map<String, Object> nuevosDatos, int idMateria) throws MateriaNotFoundException, MateriaBadRequestException {
        Materia a = findById(idMateria);

        for (Map.Entry<String, Object> entry : nuevosDatos.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            if (campo.equals("nombre")) {
                a.setNombre((String) valor);
            } else if (campo.equals("anio")) {
                a.setAnio((Integer) valor);
            } else if (campo.equals("cuatrimestre")) {
                a.setCuatrimestre((Integer) valor);
            } else throw new MateriaBadRequestException("El campo que desea modificar no existe");
        }

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
    public List<Materia> ordenarMaterias(String ordenamiento) throws MateriaBadRequestException {

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
                throw new MateriaBadRequestException("Metodo de ordenamiento incorrecto");
        }
        return materias;
    }

    @Override
    public Materia filtrarPorNombre(String nombre) throws MateriaBadRequestException {

        for (Materia m: repositorioMateria.values()) {
            if (m.getNombre().equals(nombre)){
                return m;
            }
        }
        throw new MateriaBadRequestException("Materia no encontrada");
    }

    @Override
    public Materia asignarCarrera(Carrera c, Materia m) throws MateriaBadRequestException {
        for (Materia materia : repositorioMateria.values()){
            if (m.equals(materia))
            {
                materia.setCarrera(c);
                return materia;
            }
        }
        throw new MateriaBadRequestException("Materia no encontrada");
    }
}
