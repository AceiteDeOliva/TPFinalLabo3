package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;

import java.io.Serializable;

public class EfectoCuracion extends EfectoPocion implements Serializable {
    private int cantidadCuracion;

    public int getCantidadCuracion() {
        return cantidadCuracion;
    }

    public void setCantidadCuracion(int cantidadCuracion) {
        this.cantidadCuracion = cantidadCuracion;
    }

    public EfectoCuracion(int cantidadCuracion) {
        super("Curacion");
        this.cantidadCuracion = cantidadCuracion;
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.curar(cantidadCuracion);
    }
}
