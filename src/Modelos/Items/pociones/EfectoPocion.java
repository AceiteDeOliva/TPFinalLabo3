package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;
import Modelos.Interfaces.Efecto;

public abstract class EfectoPocion implements Efecto {
    protected String tipoEfecto;

    public EfectoPocion(String tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }

    @Override
    public abstract void aplicarEfecto(Personaje personaje);
}


