package Modelos.Escenarios;

import Modelos.Sistema.Partida;

import java.io.Serializable;
import java.util.Objects;

public class Escenario implements Serializable {
    private  String nombre;
    private int nivel;
    private String descripcion;
    public Escenario() {
        this.nombre = "";
        this.nivel = 0;
        this.descripcion = "";
    }
    public Escenario(String nombre, int nivel, String descripcion) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }

    @SuppressWarnings("unused")
    public String getNombre() {
        return nombre;
    }

    @SuppressWarnings("unused")
    public int getNivel() {
        return nivel;
    }

    @SuppressWarnings("unused")
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    @SuppressWarnings("unused")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @SuppressWarnings("unused")
    public String getDescripcion() {
        return descripcion;
    }

    @SuppressWarnings("unused")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Escenario {" +
                "\n  Nombre: '" + nombre + '\'' +
                "\n  Nivel: " + nivel +
                "\n  Descripcion: '" + descripcion + '\'' +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        boolean resultado = false;
        if(o instanceof Escenario escenarioAcomparar){
            String nombreEscenario = nombre;
            String nombreEscenarioOtro = escenarioAcomparar.nombre;
            resultado = Objects.equals(nombreEscenario, nombreEscenarioOtro);
        }

        return resultado;
    }
    @Override
    public int hashCode() {
        return 1;
    }
}
