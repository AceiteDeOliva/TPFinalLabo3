package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Item;

import javax.swing.text.html.HTMLDocument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashSet;

import java.util.Iterator;

import java.util.Random;


public class Partida implements Serializable {

    private Personaje jugador;
    private transient HashMap<Integer, ArrayList<Escenario>> escenarios; //transient porque no se guarda en el archivo; clave del hashmap es el nivel; Valor es todos los escenarios pertenecientes al nivel
    private int nivelActual;
    private static final int nivelInicial = 1;

    @SuppressWarnings("unused")
    public Partida() {
        this.jugador = null;
        this.nivelActual = nivelInicial;
        this.escenarios = new HashMap<>();
    }

    public Partida(Personaje jugadorP) {
        this.jugador = jugadorP;
        this.nivelActual = nivelInicial;
        this.escenarios = new HashMap<>();
    }

    //getters & setters
    @SuppressWarnings("unused")
    public Personaje getJugador() {
        return jugador;
    }

    @SuppressWarnings("unused")
    public void setJugador(Personaje jugador) {
        this.jugador = jugador;
    }

    @SuppressWarnings("unused")
    public HashMap<Integer, ArrayList<Escenario>> getEscenarios() {
        return escenarios;
    }

    @SuppressWarnings("unused")
    public void setEscenarios(HashMap<Integer, ArrayList<Escenario>> escenarios) {
        this.escenarios = escenarios;
    }

    @SuppressWarnings("unused")
    public int getNivelActual() {
        return nivelActual;
    }

    @SuppressWarnings("unused")
    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    //metodos
    //metodos de escenario

    public Escenario escenarioPosible() { //devuelve una de las opciones de escenario para el jugador

        Escenario escenario = null; // Inicializa como null por defecto

        if (cantidadEscenariosXnivel() > 0) {
            int indice = indice();
            escenario = escenarios.get(nivelActual).get(indice); //agarra un escenario del arrayList del nivel
        }
        return escenario;
    }

    public int indice() { //devuelve un indice

        int max = cantidadEscenariosXnivel();
        return numeroAleatorio(max); //devuelve un indice
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
        return rand.nextInt(maximo); //devuelve un numero aleatorio con valor maximo maximo
    }

    public String itemEncontrado(EscenarioItem escenarioItem){
        Item nuevoItem = escenarioItem.elegirItem();
        jugador.agregarItem(nuevoItem);
        return "" + nuevoItem;
    }

    public void escenariosAHashMap(HashSet<Escenario> listaEscenarios) { //funcion que ordena los monstruos por nivel en listas
        Iterator<Escenario> iterator = listaEscenarios.iterator(); //Se crea iterator para recorrer la lista

        while (iterator.hasNext()) {
            Escenario escenarioIterado = iterator.next();
            //recibe la lista del nivel y si la lista no existe crea una nueva lista
            ArrayList<Escenario> listaNivel = escenarios.computeIfAbsent(escenarioIterado.getNivel(), k -> new ArrayList<>());
            listaNivel.add(escenarioIterado); // Agrega el escenario al nivel
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // funciones de batalla
    public int chequeoFinDeAtaque(Monstruo monstruo) { //Chequea al final de un ataque si el monstruo y/o el jugador esta vivo
        int resultado = 0; //devuelve 0 si la batalla continua
        if (jugador.estaVivo()) {
            if (!monstruo.estaVivo()) {
                resultado = 1; //devuelve 1 si el jugador ha ganado la batalla
                nivelActual += 1;
                jugador.agregarItem(monstruo.tirarBotin());
            }
        } else {
            resultado = -1; //devuelve -1 si el jugador a perdido la batalla
        }
        return resultado;

    }

    public boolean compararVelocidad(Monstruo monstruo){ //devuelve true si el jugador es igual o mas rapido y false si empieza el monstruo
        boolean respuesta = false;
            if (jugador.getArmadura().getVelocidad() >= monstruo.getVelocidad()) {
                respuesta = true;
            } else if (jugador.getArmadura().getVelocidad() < monstruo.getVelocidad()) {
                respuesta = true;
            }

        return respuesta;
    }

    // inicializar partidas
    public boolean chequearExistencia(Partida partida){
        //Chequea si la partida ingresada esta vacia y devuelve true si lo esta
        return partida.getJugador().getNombre().equals("Partida Vacia");


    }
    //Eliminar una partida de la lista
    public static boolean eliminarPartida(ArrayList<Partida> listaPartidas, Partida partidaEliminar) {
        Iterator<Partida> iter = listaPartidas.iterator();
        boolean flag = false;
        while (iter.hasNext()) {
            Partida partida = iter.next();
            if (partida.equals(partidaEliminar)) {
                iter.remove(); // Eliminar de forma segura utilizando el iterador
                flag = true;
            }
        }
        return flag;
    }
    public static boolean saberSiContienePartidas(ArrayList<Partida> listaPartidas) {
        boolean  flag=false;
        for(Partida partida: listaPartidas)
        {
            if (!partida.chequearExistencia(partida))
            {
                flag= true;
            }
        }
        return flag;
    }
    public static void agregarPartidasVacias (ArrayList<Partida>listaPartidas)
    {
        if (listaPartidas.size() < 3) {
            crearPartidasVacias(listaPartidas);
        }
    }
    public static void crearPartidasVacias(ArrayList<Partida> listaPartidas) {
        for (int i = 0; i < 3; i++) {
            if (listaPartidas.get(i) == null) {
                Personaje jugadorVacio = new Personaje();
                jugadorVacio.setNombre("Partida Vacia");
                Partida aux = new Partida(jugadorVacio);
                listaPartidas.set(i, aux);
            }
        }

    }

}



