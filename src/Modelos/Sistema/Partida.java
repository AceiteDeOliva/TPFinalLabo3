package Modelos.Sistema;

import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Partida {

    private Personaje jugador;
    private HashMap<Integer, ArrayList<Escenario>> escenarios;
    private int nivelActual;
    private static final int nivelInicial = 1;

    public Partida(Personaje jugador) {
        this.jugador = jugador;
        this.nivelActual = nivelInicial;
        this.escenarios = new HashMap<Integer, ArrayList<Escenario>>();
    }

    public int cantidadEscenariosXnivel() { //devuelve la cantidad de escenario en cierto nivel
        int cantidad = 0;

        if (escenarios.containsKey(nivelActual)) {
            cantidad = escenarios.get(nivelActual).size();
        }

        return cantidad;
    }

    public int indiceAleatorio(int maximo) { //devuelve un numero aleatorio que corresponde al indice de alguno de los escenarios del ArrayList del nivel Actual

        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(maximo);
        return indiceAleatorio;
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
