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
    public Partida() {
        this.jugador = "";
        this.nivelActual = nivelInicial;
        this.escenarios = new HashMap<>();
    }

    public Partida(Personaje jugadorP) {
        this.jugador = jugadorP;
        this.nivelActual = nivelInicial;
        this.escenarios = new HashMap<>();
    }

    //getters & setters
    public Personaje getJugador() {
        return jugador;
    }

    public void setJugador(Personaje jugador) {
        this.jugador = jugador;
    }

    public HashMap<Integer, ArrayList<Escenario>> getEscenarios() {
        return escenarios;
    }

    public void setEscenarios(HashMap<Integer, ArrayList<Escenario>> escenarios) {
        this.escenarios = escenarios;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    //metodos
    //metodos de escenario
    public Escenario escenarioPosible(){ //devuelve una de las opciones de escenario para el jugador

        Escenario escenario = null; // Inicializa como null por defecto

        if (cantidadEscenariosXnivel() > 0) {
            int indice = indice();
            escenario = escenarios.get(nivelActual).get(indice);
        }
        return escenario;
    }

    public int indice(){ //devuelve un indice

        int max = cantidadEscenariosXnivel();
        int indice = numeroAleatorio(max);
        return indice;
    }
    public int cantidadEscenariosXnivel() { //devuelve la cantidad de escenario en cierto nivel
        int cantidad = 0;

        if (escenarios.containsKey(nivelActual)) {
            cantidad = escenarios.get(nivelActual).size();
        }

        return cantidad;
    }

    public int numeroAleatorio(int maximo) { //devuelve un numero aleatorio con un maximo que corresponde a la cantidad de escenarios en el nivel actual

        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(maximo);
        return indiceAleatorio;
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
