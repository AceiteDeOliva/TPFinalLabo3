package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;

import java.io.Serializable;

public class EfectoVelocidad extends EfectoPocion implements Serializable {

    private int cantidadVelocidad;

    public int getCantidadVelocidad() {
        return cantidadVelocidad;
    }

    public void setCantidadVelocidad(int cantidadVelocidad) {
        this.cantidadVelocidad = cantidadVelocidad;
    }

    public EfectoVelocidad(int cantidadVelocidad) {
        super("Velocidad");
        this.cantidadVelocidad = cantidadVelocidad;
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.aumentarVelocidad(cantidadVelocidad);
    }

}
