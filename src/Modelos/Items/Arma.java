package Modelos.Items;

import Modelos.Interfaces.Habilidades;

public class Arma extends Item implements Habilidades {

    private int danio;

    //Constructor
    public Arma(String nombre, int danio) {
        super(nombre);
        this.danio = danio;
    }

    //Getters
    public int getDanio() {
        return danio;
    }

    //Setters
    public void setDanio(int danio) {
        this.danio = danio;
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
}
