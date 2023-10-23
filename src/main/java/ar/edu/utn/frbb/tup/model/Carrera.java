package ar.edu.utn.frbb.tup.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Carrera {
    private int id;
    private String nombre;
    private String codigo;
    private int cantidadCuatrimestres;
    private int idDepartamento;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Materia> materiasList;


    public Carrera() {
        this.materiasList = new ArrayList<>();
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

    public List<Materia> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materia> materiasList) {
        this.materiasList = materiasList;
    }

    public void agregarMateria(Materia m){

        materiasList.add(m);
        m.setCarrera(this);

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrera carrera = (Carrera) o;
        return id == carrera.id && cantidadCuatrimestres == carrera.cantidadCuatrimestres && idDepartamento == carrera.idDepartamento && Objects.equals(nombre, carrera.nombre) && Objects.equals(codigo, carrera.codigo) && Objects.equals(materiasList, carrera.materiasList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, codigo, cantidadCuatrimestres, idDepartamento, materiasList);
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cantidadCuatrimestres=" + cantidadCuatrimestres +
                ", idDepartamento=" + idDepartamento +
                ", materiasList=" + materiasList +
                '}';
    }
}

