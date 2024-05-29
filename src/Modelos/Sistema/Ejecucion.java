package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.Pocion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Ejecucion {
    private static Partida partida = new Partida(new Personaje());

    public static void Ejecucion() {

        //Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        ArrayList<Item> ListaItems = new ArrayList<>(archivo.leerArchivoItems(NombreArchivos.Items.getNombre()));
        //Pasar info de partida

        //Pasar info de monstruo
        //Pasar info de personaje
        //pasar info de escenarios
        archivo.grabarArchivo(ListaItems, NombreArchivos.Items.getNombre());

        Scanner scanner = new Scanner(System.in);


        //if escenario es una pelea
        Monstruo monstruo = new Monstruo();//reemplazar por el monstruo del escenario con monstruo
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

                    chequeoBatalla(monstruo);
                    break;

                case 2:
                    System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");

                    if (monstruo.ataqueMonstruo(partida.getJugador()) != -1) {
                        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");
                    }

                    chequeoBatalla(monstruo);
                    break;

                case 3:
                    mostrarInventario();

                    break;

                default:
                    System.out.println("Decision invalida");
                    break;


            }

        }

    }

    //Funciones de print
    public static void chequeoBatalla(Monstruo monstruo) {
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

    public static void mostrarInventario() {
        ArrayList<String> inventarioNombres = partida.getJugador().getInventarioNombres();
        int itemIndex = 1;
        System.out.println("0. Volver");
        for (int i = 0; i < inventarioNombres.size(); i++) {
            System.out.println((i + 1) + ". " + inventarioNombres.get(i) + ")");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Elige un Item: ");
        int eleccion = scanner.nextInt();
        seleccionItem(eleccion);


    }

    public static void seleccionItem(int eleccion) {
        String itemSeleccionado = partida.getJugador().equiparDesdeInventario(eleccion); //manda la eleccion y retorna el item que se equipo

        if (eleccion == 0) {
            System.out.println("Saliste del inventario.");
        } else if (itemSeleccionado == null) {
            System.out.println("Opción inválida.");
        }else {
            System.out.println("Seleccionaste: " + itemSeleccionado);
        }

    }
}
