package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;
import Modelos.Interfaces.IEfecto;

import java.io.Serializable;

public abstract class EfectoPocion implements IEfecto, Serializable {
    protected String tipoEfecto;

    public EfectoPocion(String tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }

    @Override
    public abstract void aplicarEfecto(Personaje personaje);

    @Override
    public String toString() {
        return
                "tipoEfecto='" + tipoEfecto + '\'';
    }
}


