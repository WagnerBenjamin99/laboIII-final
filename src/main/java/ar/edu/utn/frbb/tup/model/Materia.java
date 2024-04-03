package ar.edu.utn.frbb.tup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Materia {
    private int id;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private Profesor profesor;
    private String codigo;
    private List<Materia> correlatividades;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = false)
    private Carrera carrera;

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setCorrelatividades(List<Materia> correlatividades) {
        this.correlatividades = correlatividades;
    }

    public Materia(){}

    public Materia(String nombre, int anio, int cuatrimestre, Profesor profesor) {
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.nombre = nombre;
        this.profesor = profesor;

        correlatividades = new ArrayList<>();
    }

    public void agregarCorrelatividad(Materia m){
        this.correlatividades.add(m);

    }

    public List<Materia> getCorrelatividades(){
        return this.correlatividades;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMateriaId() {
        return id;
    }

    public void setMateriaId(int materiaId) {
        this.id = materiaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return anio == materia.anio && cuatrimestre == materia.cuatrimestre && Objects.equals(nombre, materia.nombre) && Objects.equals(profesor, materia.profesor) && Objects.equals(codigo, materia.codigo) && Objects.equals(correlatividades, materia.correlatividades) && Objects.equals(carrera, materia.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, anio, cuatrimestre, profesor, codigo, correlatividades, carrera);
    }

    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", cuatrimestre=" + cuatrimestre +
                ", profesor=" + profesor +
                ", codigo='" + codigo + '\'' +
                ", correlatividades=" + correlatividades +
                ", carrera=" + carrera +
                '}';
    }
}
