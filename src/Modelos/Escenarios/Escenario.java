package Modelos.Escenarios;

public class Escenario {
    private final String nombre;
    private int nivel;
    private String descripcion;

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
    public String getDescripcion() {
        return descripcion;
    }

    @SuppressWarnings("unused")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Escenario" + "Nombre='" + nombre + '\'' + ", Nivel=" + nivel + ", Descripcion='" + descripcion + '\'';
    }
}
