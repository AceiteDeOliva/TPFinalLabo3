package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;
import Modelos.Interfaces.Efecto;
import Modelos.Items.Item;

public class Pocion <EfectoPocion extends Efecto> extends Item {

    private EfectoPocion efecto;

    public Pocion(String nombreP, String descripcionP, EfectoPocion efecto) {
        super(nombreP, descripcionP);
        this.efecto = efecto;
    }

    public EfectoPocion getEfecto() {
        return efecto;
    }

    public void setEfecto(EfectoPocion efecto) {
        this.efecto = efecto;
    }

    public void usar(Personaje personaje) {
        efecto.aplicarEfecto(personaje);

    }


}
