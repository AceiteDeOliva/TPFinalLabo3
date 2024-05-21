package Modelos.Entidades;

public abstract class Entidad {
    private String nombre;
    private int salud;

    //Constructor
    public Entidad () {
        this.nombre = "";
        this.salud = 0;
    }
    
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

    //Metodos

    abstract boolean estaVivo();
    abstract void recibirDanio(int danio);


}
