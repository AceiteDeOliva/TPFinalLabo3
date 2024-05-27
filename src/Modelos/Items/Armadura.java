package Modelos.Items;

public class Armadura extends Item {
    private int defensa;
    private int velocidad;

    //Constructores
    public Armadura () {
        super();
        this.defensa = 0;
        this.velocidad = 0;
    }

    public Armadura(String nombre, int defensa, int velocidad) {
        super(nombre);
        this.defensa = defensa;
        this.velocidad = velocidad;
    }

    //Getters
    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    //Setters
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
