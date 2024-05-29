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

        Partida partida = new Partida(new Personaje());


        //if escenario es una pelea
        Monstruo monstruo = new Monstruo();//reemplazar por el monstruo del escenario con monstruo
        int chequeoBatalla = 0;
        ArrayList<Item> inventario = partida.getJugador().getInventario();
        int itemIndex = 1;
        Item itemSeleccionado;
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

                    chequeoBatalla = partida.chequeoFinDeAtaque(monstruo);//El resultado de la batalla
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
                    break;

                case 2:
                    System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");

                    if (monstruo.ataqueMonstruo(partida.getJugador()) != -1) {
                        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");
                    }

                    chequeoBatalla = partida.chequeoFinDeAtaque(monstruo);
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
                            System.out.println("Error: Resultado de batalla invalido ");
                            break;

                    }
                    break;

                case 3:
                    System.out.println("0. Volver");
                    for (Item item : inventario) {
                        System.out.println(itemIndex + ". " + item.getNombre() + ")");
                        itemIndex++;

                    }
                    System.out.print("Elige un Item: ");
                    eleccion = scanner.nextInt();
                    itemSeleccionado = partida.getJugador().equiparDesdeInventario(eleccion); //manda la eleccion y retorna el item que se equipo
                    if (eleccion == 0) {
                        System.out.println("Saliste del inventario.");
                    } else if(itemSeleccionado == null){
                        System.out.println("Opción inválida.");
                    }
                    if(itemSeleccionado != null){

                        System.out.println("Seleccionaste: " + itemSeleccionado.getNombre());
                    }

                    break;

                default:
                    System.out.println("Decision invalida");
                    break;


            }

        }


    }
}
