package ar.edu.utn.frbb.tup.persistence.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraBadRequestException;
import ar.edu.utn.frbb.tup.persistence.exception.CarreraNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import java.util.*;


@Repository
public class CarreraDaoMemoryImpl implements CarreraDao {
    private Map<Integer, Carrera> repositorioCarrera = new HashMap<>();
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
    public List<Carrera> getAllCarreras() throws CarreraNotFoundException {
        List<Carrera> carreras = new ArrayList<>(repositorioCarrera.values());
        if (carreras.size() == 0) throw new CarreraNotFoundException("No hay carreras disponibles");
        return carreras;
    }

    @Override
    public Carrera getCarreraById(int idCarrera) throws CarreraNotFoundException {
        for (Carrera c:
                repositorioCarrera.values()) {
            if (idCarrera == c.getId()) {
                return c;
            }
        }
        throw new CarreraNotFoundException("No se encontro la carrera que busca");
    }

    @Override
    public Carrera agregarMateria(Materia m, Carrera c) throws CarreraNotFoundException, CarreraBadRequestException {
        for (Carrera carrera : repositorioCarrera.values()){
            if (c.equals(carrera)){
                carrera.agregarMateria(m);
                return carrera;
            }
        }
        throw new CarreraBadRequestException("No se puedo agregar la materia");

    }

    @Override
    public Carrera crearCarreraConMaterias(List<Materia> materias, Carrera carrera) throws CarreraBadRequestException, CarreraNotFoundException {
        Random r = new Random();
        carrera.setId(r.nextInt(20));
        carrera.setCodigo(generarCodigo());
        repositorioCarrera.put(carrera.getId(), carrera);

        for (Materia m : materias)agregarMateria(m, carrera);
        return carrera;
    }

    @Override
    public Carrera modificarCarrera(Map<String, Object> nuevosDatos, int idCarrera) throws CarreraNotFoundException, CarreraBadRequestException, MateriaNotFoundException {
        Carrera c = getCarreraById(idCarrera);
        for (Map.Entry<String, Object> entry : nuevosDatos.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();

            if (campo.equals("nombre")) {
                c.setNombre((String) valor);
            } else if (campo.equals("departamentoId")) {
                c.setIdDepartamento((Integer) valor);
            } else if (campo.equals("cantidadCuatrimestres")) {
                c.setCantidadCuatrimestres((Integer) valor);
            }
            else if (campo.equals("materiaIds")){
                for (Integer idMateria : (ArrayList<Integer>) valor) {
                    Materia m = null;
                    try {
                        m = materiaService.getMateriaById(idMateria);
                    } catch (MateriaNotFoundException e) {
                        throw new MateriaNotFoundException("No se encontro la materia que desea agregar a " + c);
                    }
                    try {
                        agregarMateria(m, getCarreraById(idCarrera));
                    } catch (CarreraNotFoundException e) {
                        throw new CarreraNotFoundException("No se encontro la carrera que desea modificar");
                    }
                }
            }
            else throw new CarreraBadRequestException("El campo que desea modificar es incorrecto");

        };

        return c;
    }

    @Override
    public Carrera eliminarCarrera(Carrera carrera) throws CarreraNotFoundException {
        if (repositorioCarrera.containsValue(carrera))
        {
            repositorioCarrera.remove(carrera.getId(), carrera);
            return carrera;
        }
        throw new CarreraNotFoundException("No se ecnontro la carrera que desea eliminar");
    }

    @Override
    public Carrera crearCarrera(Carrera carrera) throws CarreraBadRequestException {
        Random r = new Random();
        carrera.setCodigo(generarCodigo());
        carrera.setId(r.nextInt(20));
        if (repositorioCarrera.put(carrera.getId(), carrera) == null) {
            throw new CarreraBadRequestException("No se pudo crear la carrera");
        }
        else return carrera;
    }
}
