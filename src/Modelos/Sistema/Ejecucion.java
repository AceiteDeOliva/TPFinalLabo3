package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Exceptions.ExcepcionSwitch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejecucion {
    private static Escenario escenarioActual;

    public static void ejecucion() throws ExcepcionSwitch {

        //Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        //Pasar info de partida

        ArrayList<Partida> listaPartidas = archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre());
        // si la lista es menor a 3 agregar partidas vacias
        //pasar info de escenarios con json
        HashSet<EscenarioMonstruo> escenarioMonstruos = new HashSet<>(archivo.jsonAEscenario());

        archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre());

        Partida partida = comenzarPartida(listaPartidas);

        //Pasar info de monstruo
        //Pasar info de personaje
        manejarEncuentro(partida);


    }

    public static void manejarEncuentro(Partida partida) throws ExcepcionSwitch {
        try {
            elegirEncuentro(partida);
        } catch (ExcepcionSwitch e) {
            throw new RuntimeException(e);
        }
        if (escenarioActual instanceof EscenarioMonstruo) {
            encuentro(partida, (EscenarioMonstruo) escenarioActual);

        } else if (escenarioActual instanceof EscenarioItem) {
            encuentro(partida, (EscenarioItem) escenarioActual);
        }

    }

    public static void elegirEncuentro(Partida partida) throws ExcepcionSwitch {
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
        System.out.println("1. Primer camino.");
        System.out.println("2. Segundo camino.");
        while (eleccion != 1 && eleccion != 2) {
            try {
                eleccion = scanner.nextInt();
                if (eleccion != 1 && eleccion != 2) {
                    throw new ExcepcionSwitch("Opcion invalida. Solo se permiten 1 o 2.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Error: Ingrese un numero valido (1 or 2).");
            }
        }
        switch (eleccion) {
            case 1:
                escenarioActual = escenario1;
                break;
            case 2:
                escenarioActual = escenario2;
                break;

        }
    }

    public static void encuentro(Partida partida, EscenarioMonstruo escenario) throws ExcepcionSwitch {
        Scanner scanner = new Scanner(System.in);
        Monstruo monstruo = escenario.elegirMounstruo();
        boolean velocidadComparada;
        while (partida.getJugador().estaVivo() && monstruo.estaVivo()) {

            System.out.println("\n" +escenario.getNombre());
            System.out.println(escenario.getDescripcion());
            System.out.println(monstruo);
            System.out.println(partida.getJugador());


            int eleccion;
            System.out.println("Que desea hacer?");
            System.out.println("1. Ataque basico");
            System.out.println("2. Ataque especial");
            System.out.println("3. Abrir inventario");
            System.out.println("4. Ver equipamiento");

            try {
                eleccion = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Limpiar la entrada no válida del scanner
                System.out.println("Error: Ingrese un numero valido.");
                continue;
            }
            switch (eleccion) {

                case 1:
                    velocidadComparada = partida.compararVelocidad(monstruo); //devuelve true si el jugador es igual o mas rapido que el monstruo y si no false
                    if(velocidadComparada){
                        ataqueJugadorPrimero(partida,monstruo);
                    }else{
                        ataqueMonstruoPrimero(partida,monstruo);
                    }

                    chequeoBatalla(partida, monstruo);
                    break;

                case 2:
                    velocidadComparada = partida.compararVelocidad(monstruo); //devuelve true si el jugador es igual o mas rapido que el monstruo y si no false
                    if(velocidadComparada){
                        ataqueEspecialJugadorPrimero(partida,monstruo);
                    }else{
                        ataqueEspecialMonstruoPrimero(partida,monstruo);
                    }

                    chequeoBatalla(partida, monstruo);
                    break;

                case 3:
                    mostrarInventario(partida);
                    break;

                case 4:
                    System.out.println(partida.getJugador().getArma().toString());
                    System.out.println(" ");
                    System.out.println(partida.getJugador().getArmadura().toString());
                    break;

                default:
                    throw new ExcepcionSwitch("Opción inválida. Solo se permiten 1, 2, 3 o 4.");



            }


        }

    }

    public static void ataqueJugadorPrimero(Partida partida, Monstruo monstruo){
        System.out.println("El jugador inflige" + partida.getJugador().ataqueJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        if (monstruo.estaVivo()) {
            System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo
        }

    }

    public static void ataqueMonstruoPrimero(Partida partida, Monstruo monstruo){
        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println("El jugador inflige" + partida.getJugador().ataqueJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        }

    }

    public static void ataqueEspecialJugadorPrimero(Partida partida, Monstruo monstruo){
        System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");

        if (monstruo.estaVivo()) {
            System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");
        }

    }

    public static void ataqueEspecialMonstruoPrimero(Partida partida, Monstruo monstruo){
        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        }

    }


    public static void encuentro(Partida partida, EscenarioItem escenario) {
        System.out.println(escenario.getNombre());
        System.out.println("Exploras el lugar...");
        String itemEncontrado = partida.itemEncontrado(escenario);
        System.out.println("Econtraste:" + itemEncontrado + "!!!");

    }

    //Funciones de print
    public static void chequeoBatalla(Partida partida, Monstruo monstruo) throws ExcepcionSwitch {
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
                throw  new ExcepcionSwitch("Error: Resultado de batalla invalido");



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
        System.out.println("0. Volver");
        for (int i = 0; i < inventarioNombres.size(); i++) {
            System.out.println((i + 1) + ". " + inventarioNombres.get(i) + ")");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Elige un Item: ");
        int eleccion = scanner.nextInt();
        seleccionItem(partida, eleccion);

    }

    public static Partida comenzarPartida(ArrayList<Partida> listaPartidas) {
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
        return listaPartidas.get(eleccion + 1);
    }


}

