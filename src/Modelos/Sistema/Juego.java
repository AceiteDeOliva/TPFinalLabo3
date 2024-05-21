package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;

public class Juego{

private Personaje jugador;
private Monstruo monstruo;

    public Juego(Personaje jugador, Monstruo monstruo) {
        this.jugador = jugador;
        this.monstruo = monstruo;
    }


    public boolean fight() {
        boolean estaVivo = false;
        while (jugador.estaVivo() && monstruo.estaVivo()) {
            // turno del jugador
            int danioJugador = jugador.getArma().atacar();
            monstruo.recibirDanio(danioJugador);

            if (monstruo.estaVivo()) {
                // turno del monstruo
                int danioMonstruo = monstruo.atacar();
                jugador.recibirDanio(danioMonstruo);
            }
        }
        
        if (jugador.estaVivo()) {
            estaVivo = true;
        } 
        return estaVivo;
    }

}
