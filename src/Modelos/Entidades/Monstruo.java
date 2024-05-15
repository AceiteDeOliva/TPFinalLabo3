package Modelos.Entidades;

import Modelos.Interfaces.Habilidades;

public class Monstruo extends Entidad implements Habilidades {

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
