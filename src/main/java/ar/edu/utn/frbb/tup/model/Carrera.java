package ar.edu.utn.frbb.tup.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Carrera {

    private final String nombre;
    private String codigo;
    private int cantidadCuatrimestres;
    private int idDepartamento;
    private Set<Materia> materiasList;
    private int id;

    public Carrera(String nombre, int cantidadAnios, int idDepartamento, Set<Materia> materias) {
        this.nombre = nombre;
        this.cantidadCuatrimestres = cantidadAnios;
        this.idDepartamento = idDepartamento;
        this.materiasList = materias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidadCuatrimestres() {
        return cantidadCuatrimestres;
    }

    public void setCantidadCuatrimestres(int cantidadCuatrimestres) {
        this.cantidadCuatrimestres = cantidadCuatrimestres;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Set<Materia> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(Set<Materia> materiasList) {
        this.materiasList = materiasList;
    }

    public void agregarMateria(Materia m){

        this.materiasList.add(m);

    }


    public interface CarreraStrategy{
        Carrera crearCarrera(String nombre, int departamentoId, int cantidadCuatrimestres, Set<Materia> materias);
    }

    public static class CarreraConMaterias implements CarreraStrategy{

        @Override
        public Carrera crearCarrera(String nombre, int departamentoId, int cantidadCuatrimestres, Set<Materia> materias) {
            return new Carrera(nombre, departamentoId, cantidadCuatrimestres, materias);
        }
    }

    public static class CarreraSinMaterias implements CarreraStrategy{

        @Override
        public Carrera crearCarrera(String nombre, int departamentoId, int cantidadCuatrimestres, Set<Materia> materias) {
            return new Carrera(nombre, departamentoId, cantidadCuatrimestres, new HashSet<>());
        }
    }
}

