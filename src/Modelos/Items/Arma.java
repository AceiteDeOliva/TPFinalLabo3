package Modelos.Items;

import Modelos.Interfaces.IHabilidades;

import java.io.Serializable;

public class Arma extends Item implements IHabilidades, Serializable {

    private int danio;

    //Constructores
    public Arma() {
        super();
        this.danio = 0;
    }

    public Arma(String nombreP, String descripcionP, int danioP) {
        super(nombreP, descripcionP);
        this.danio = danioP;
    }

    //Getters
    public int getDanio() {
        return danio;
    }

    //Setters
    public void setDanio(int danioP) {
        this.danio = danioP;
    }

    //Metodos
    @Override
    public int atacar() {
        return danio;
    }

    @Override
    public int ataqueEspecial() {
        return danio * 2;
    }

    @Override
    public String toString() {
        return "Arma: " + super.toString() +
                "\n  Danio: " + danio;
    }
}
