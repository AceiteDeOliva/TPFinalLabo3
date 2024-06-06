package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;

public class EfectoVelocidad extends EfectoPocion{

    private int cantidadVelocidad;

    public int getCantidadVelocidad() {
        return cantidadVelocidad;
    }

    public void setCantidadVelocidad(int cantidadVelocidad) {
        this.cantidadVelocidad = cantidadVelocidad;
    }

    public EfectoVelocidad(int cantidadVelocidad) {
        super("Curacion");
        this.cantidadVelocidad = cantidadVelocidad;
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.curar(cantidadVelocidad);
    }

}
