package Modelos.Items.pociones;

import Modelos.Entidades.Personaje;

public class EfectoCuracion extends EfectoPocion{
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
