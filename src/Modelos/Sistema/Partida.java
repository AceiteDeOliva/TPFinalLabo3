package Modelos.Sistema;

import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;

import java.util.ArrayList;
import java.util.HashSet;

public class Partida {

private Personaje jugador;
private HashSet<ArrayList<Escenario>> escenarios;

    public Partida(Personaje jugador) {
        this.jugador = jugador;
        this.escenarios = new HashSet<ArrayList<Escenario>>();
    }


    


    /*public boolean pelea() {
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
    }*/

}
