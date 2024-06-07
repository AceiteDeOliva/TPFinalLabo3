package Modelos.Escenarios;

public class Escenario {
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
}
