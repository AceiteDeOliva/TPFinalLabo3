package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.Pocion;
import org.json.JSONException;
import Modelos.Sistema.Partida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Ejecucion {

    public static void Ejecucion() {

        //Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        //Pasar info de partida

        ArrayList<Partida> listaPartidas = new ArrayList<>();
        listaPartidas = archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre());
        // si la lista es menor a 3 agregar partidas vacias
        //pasar info de escenarios con json
        HashSet<EscenarioMonstruo> escenarioMonstruos = new HashSet<>(archivo.jsonAEscenario());

        archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre());

        Partida partida = listaPartidas.getFirst();//por ahora

        //Pasar info de monstruo
        //Pasar info de personaje

        Escenario escenario = partida.escenarioPosible();

        if (escenario instanceof EscenarioMonstruo) {
            encuentro(partida, (EscenarioMonstruo) escenario);

        } else if (escenario instanceof EscenarioItem) {
            encuentro(partida, (EscenarioItem) escenario);

        }


    }

    public static void elegirEncuentro(Partida partida) {
        Escenario escenario1 = partida.escenarioPosible();
        Escenario escenario2 = partida.escenarioPosible();
        Scanner scanner = new Scanner(System.in);
        int eleccion = -1;

        System.out.println("Estas en una bifurcacion en el camino.");
        System.out.println("A un lado ves:");
        System.out.println(escenario1.getDescripcion());
        System.out.println("Al otro lado ves:");
        System.out.println(escenario2.getDescripcion());
        System.out.println("Que camino deseas tomar?");
        System.out.println("1. Primer camino");
        System.out.println("2. Segundo camino");
        while (eleccion != 1 && eleccion != 2) {
            eleccion = scanner.nextInt();
        }
        switch (eleccion) {


            //if escenario es una pelea
            Monstruo monstruo = new Monstruo();//reemplazar por el monstruo del escenario con monstruo
            while (partida.getJugador().estaVivo() && monstruo.estaVivo()) {


                case 1:
                    //no se donde se guardaria esto
                    break;
                case 2:
                    //idem lo de arriba
                    break;
            }
        }
    }

    public static void encuentro(Partida partida, EscenarioMonstruo escenario) {
        Scanner scanner = new Scanner(System.in);
        Monstruo monstruo = escenario.elegirMounstruo();
        while (partida.getJugador().estaVivo() && monstruo.estaVivo()) {

            /*desc escenario
            nombre monstruo
            vida de monstruo
            datos del jugador
            todo lo de arriba habria que ponerlo lindo */

            int eleccion = -1;
            System.out.println("Que desea hacer?");
            System.out.println("1. Ataque basico");
            System.out.println("2. Ataque especial");
            System.out.println("3. Abrir inventario");
            System.out.println("4. Ver equipamiento");

            eleccion = scanner.nextInt();
            switch (eleccion) {

                case 1:
                    System.out.println("El jugador inflige" + partida.getJugador().ataqueJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

                    if (monstruo.ataqueMonstruo(partida.getJugador()) != -1) { //Si es -1 el monstruo esta muerto
                        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo
                    }

                    chequeoBatalla(partida, monstruo);
                    break;

                case 2:
                    System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");

                    if (monstruo.ataqueMonstruo(partida.getJugador()) != -1) {
                        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");
                    }

                    chequeoBatalla(partida, monstruo);
                    break;

                case 3:
                    mostrarInventario(partida);

                    break;

                default:
                    System.out.println("Decision invalida");
                    break;


            }

        }

    }

    public static void encuentro(Partida partida, EscenarioItem escenario) {
        System.out.println(escenario.getNombre());
        System.out.println("Exploras el lugar");
        String itemEncontrado = partida.itemEncontrado(escenario);
        System.out.println("Econtraste:" + itemEncontrado + "!!!");

    }

    //Funciones de print
    public static void chequeoBatalla(Partida partida, Monstruo monstruo) {
        int chequeoBatalla = partida.chequeoFinDeAtaque(monstruo);//El resultado de la batalla || 1 si gano || 0 si continua || -1 si perdio
        switch (chequeoBatalla) {
            case 1:
                System.out.println("Ha ganado la batalla.");

                break;

            case 0:
                System.out.println("La batalla continua"); //Esto se puede dejar vacio tambien

                break;

            case -1:
                System.out.println("Ha perdido la batalla");
                break;

            default:
                System.out.println("Error: Resultado de batalla invalido");
                break;


        }

    }


    public static void seleccionItem(Partida partida, int eleccion) {
        String itemSeleccionado = partida.getJugador().equiparDesdeInventario(eleccion); //manda la eleccion y retorna el item que se equipo

        if (eleccion == 0) {
            System.out.println("Saliste del inventario.");
        } else if (itemSeleccionado == null) {
            System.out.println("Opción inválida.");
        } else {
            System.out.println("Seleccionaste: " + itemSeleccionado);
        }

    }

    public static void mostrarInventario(Partida partida) {
        ArrayList<String> inventarioNombres = partida.getJugador().getInventarioNombres();
        int itemIndex = 1;
        System.out.println("0. Volver");
        for (int i = 0; i < inventarioNombres.size(); i++) {
            System.out.println((i + 1) + ". " + inventarioNombres.get(i) + ")");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Elige un Item: ");
        int eleccion = scanner.nextInt();
        seleccionItem(partida, eleccion);


    }
    public Partida ComenzarPartida(ArrayList<Partida> listaPartidas) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Partidas:");
        for (int i = 0; i < listaPartidas.size(); i++) {
            System.out.println((i + 1) + ". " + listaPartidas.get(i).getJugador());
        }
        System.out.println("Ingrese el número de la partida:");
        int eleccion = scan.nextInt();
        while (eleccion < 1 || eleccion > listaPartidas.size()) {
            System.out.println("Selección inválida. Ingrese nuevamente:");
            eleccion = scan.nextInt();
        }
        scan.close();
        Partida partidaActual = listaPartidas.get(eleccion + 1);
        return partidaActual;
    }



}

