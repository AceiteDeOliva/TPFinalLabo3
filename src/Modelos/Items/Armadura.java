package Modelos.Items;

public class Armadura extends Item {
    private int defensa;
    private int velocidad;

    //Constructores

    public Armadura() {
        super();
        this.defensa = 0;
        this.velocidad = 0;
    }

    public Armadura(String nombreP, String descripcionP, int defensaP, int velocidadP) {
        super(nombreP, descripcionP);
        this.defensa = defensaP;
        this.velocidad = velocidadP;
    }

    //Getters
    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    //Setters
    public void setDefensa(int defensaP) {
        this.defensa = defensaP;
    }

    public void setVelocidad(int velocidadP) {
        this.velocidad = velocidadP;
    }
}
