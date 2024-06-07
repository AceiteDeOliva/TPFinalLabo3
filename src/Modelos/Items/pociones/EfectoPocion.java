package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;
import Modelos.Interfaces.IEfecto;

public abstract class EfectoPocion implements IEfecto {
    protected String tipoEfecto;

    public EfectoPocion(String tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }

    @Override
    public abstract void aplicarEfecto(Personaje personaje);
}


