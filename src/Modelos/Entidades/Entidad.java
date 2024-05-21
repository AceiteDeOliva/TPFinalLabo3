package Modelos.Entidades;

public abstract class Entidad {
    private String nombre;
    private int salud;

    //constructor
    public Entidad(String nombre, int salud) {
        this.nombre = nombre;
        this.salud = salud;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    abstract boolean estaVivo();
    abstract void recibirDanio(int danio);


}
