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


    public void fight() {
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

        // Esto no se si va en el main
        if (jugador.estaVivo()) {
            System.out.println("Ganaste la pelea");
        } else {
            System.out.println("Perdiste");
        }
    }

}
