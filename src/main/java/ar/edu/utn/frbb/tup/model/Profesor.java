package ar.edu.utn.frbb.tup.model;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Profesor {

    private final long id = 12;
    private final String nombre;
    private final String apellido;
    private final String titulo;

    private List<Materia> materiasDictadas;

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Materia> getMateriasDictadas() {
        return materiasDictadas;
    }

    public void setMateriasDictadas(List<Materia> materiasDictadas) {
        this.materiasDictadas = materiasDictadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return id == profesor.id && Objects.equals(nombre, profesor.nombre) && Objects.equals(apellido, profesor.apellido) && Objects.equals(titulo, profesor.titulo) && Objects.equals(materiasDictadas, profesor.materiasDictadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, titulo, materiasDictadas);
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", titulo='" + titulo + '\'' +
                ", materiasDictadas=" + materiasDictadas +
                '}';
    }
}
