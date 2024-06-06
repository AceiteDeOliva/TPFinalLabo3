package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;
import Modelos.Interfaces.IEfecto;
import Modelos.Items.Item;

public class Pocion <E extends IEfecto> extends Item {

    private E efecto;


    public Pocion(String nombreP, String descripcionP, E efecto) {
        super(nombreP, descripcionP);
        this.efecto = efecto;
    }

    public E getEfecto() {
        return efecto;
    }

    public void setEfecto(E efecto) {
        this.efecto = efecto;
    }

    public void usar(Personaje personaje) {
        efecto.aplicarEfecto(personaje);

    }

}
