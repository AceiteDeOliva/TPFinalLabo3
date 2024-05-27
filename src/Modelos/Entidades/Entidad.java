package Modelos.Entidades;

public abstract class Entidad {
    private String nombre;
    private int salud;

    //Constructor
    public Entidad () {
        this.nombre = "";
        this.salud = 0;
    }
    
    public Entidad(String nombreP, int saludP) {
        this.nombre = nombreP;
        this.salud = saludP;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    //Setters

    public void setNombre(String nombreP) {
        this.nombre = nombreP;
    }

    public void setSalud(int saludP) {
        this.salud = saludP;
    }

    //Metodos

    abstract boolean estaVivo();
    abstract void recibirDanio(int danio);


}
