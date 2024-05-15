package Modelos.Items;

import Modelos.Interfaces.Habilidades;

public class Arma extends Item implements Habilidades {

    private int danio;


    @Override
    public int atacar() {
        return danio;
    }

    @Override
    public int ataqueEspecial() {
        return danio * 2;
    }
}
