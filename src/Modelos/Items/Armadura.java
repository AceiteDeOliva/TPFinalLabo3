package Modelos.Items;

public class Armadura extends Item {
    private int defensa;

    private int velocidad;

    public Armadura(String nombre, int defensa, int velocidad) {
        super(nombre);
        this.defensa = defensa;
        this.velocidad = velocidad;
    }

    //getters
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
