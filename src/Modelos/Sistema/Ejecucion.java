package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Exceptions.ExcepcionSwitch;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.Pocion;
import org.json.JSONException;
import Modelos.Sistema.Partida;

import java.util.*;

public class Ejecucion {
    private static Escenario escenarioActual;

    public static void ejecucion() {
        // Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        // Pasar info de partida
        ArrayList<Partida> listaPartidas = archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre());
        // si la lista es menor a 3 agregar partidas vacias
        // pasar info de escenarios con json
        HashSet<EscenarioMonstruo> escenarioMonstruos = new HashSet<>();
        escenarioMonstruos= archivo.jsonAEscenario();

        Scanner scan = new Scanner(System.in);
        // Menu
        int eleccion;
        // Leer la elección del usuario

        do {
            System.out.println(Ejecucion.titulo());
            // Presentar las opciones del menú
            System.out.println("1.Jugar");
            System.out.println("2.Salir del juego");
            // Leer la elección del usuario
            eleccion = scan.nextInt();

            // Realizar acciones basadas en la elección del usuario usando un switch
            switch (eleccion) {
                case 1:
                    // Si el usuario elige jugar, manejar encuentros
                    // Partida partida = listaPartidas.getFirst();
                    // Esto asigna la primera partida de la lista, pero deberías implementar la lógica para seleccionar o crear una partida
                    // manejarEncuentro(partida); // Llamar al método para manejar los encuentros del juego
                    System.out.println("Iniciando el juego...");
                    break;
                case 2:
                    // Si el usuario elige salir, terminar el programa
                    System.out.println("Saliendo del juego...");
                    break;
                default:
                    // Si la elección no es válida, mostrar un mensaje de error
                    System.out.println("Elección no válida. Por favor, selecciona una opción válida.");
                    break;
            }
        } while (eleccion != 1 && eleccion != 2);
        archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre());

    }


    public static void manejarEncuentro(Partida partida) {
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
        System.out.println("1. Primer camino");
        System.out.println("2. Segundo camino");
        while (eleccion != 1 && eleccion != 2) {
            try {
                eleccion = scanner.nextInt();
                // Check for invalid choice (1 or 2) and throw exception if needed
                if (eleccion != 1 && eleccion != 2) {
                    throw new ExcepcionSwitch("Opcion invalida. Solo se permiten 1 o 2.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input from the scanner
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

    public static int comenzarPartida(ArrayList<Partida> listaPartidas) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Partidas:");
        System.out.println("0. Volver");
        for (int i = 0; i < listaPartidas.size(); i++) {
            System.out.println((i + 1) + ". " + listaPartidas.get(i).getJugador());
        }
        System.out.println("Ingrese el número de la partida:");
        int eleccion = scan.nextInt();
        while (eleccion < 1 || eleccion > listaPartidas.size() + 1) {
            System.out.println("Selección inválida. Ingrese nuevamente:");
            eleccion = scan.nextInt();
        }
        scan.close();

        return eleccion - 1;
    }

    public static void figuras(){
        // Mostrar las figuras ASCII

        String art1 = """
                     _,.
                    ,` -.)
                   ( _/-\\\\-._
                  /,|`--._,-^|            ,
                  \\_| |`-._/||          ,'|
                    |  `-, / |         /  /
                    |     || |        /  /
                     `r-._||/   __   /  /
                 __,-<_     )`-/  `./  /
                '  \\   `---'   \\   /  /
                    |           |./  /
                    /           //  /
                \\_/' \\         |/  /
                 |    |   _,^-'/  /
                 |    , ``  (\\/  /_
                  \\,.->._    \\X-=/^
                  (  /   `-._//^`
                   `Y-.____(__}
                    |     {__)
                          ()""";

        String art2 = """
                       ,     .
                        /(     )\\               A
                   .--.( `.___.' ).--.         /_\\
                   `._ `%_&%#%$_ ' _.'     /| <___> |\\
                      `|(@\\*%%/@)|'       / (  |L|  ) \\
                       |  |%%#|  |       J d8bo|=|od8b L
                        \\ \\$#%/ /        | 8888|=|8888 |
                        |\\|%%#|/|        J Y8P"|=|"Y8P F
                        | (.".)%|         \\ (  |L|  ) /
                    ___.'  `-'  `.___      \\|  |L|  |/
                  .'#*#`-       -'$#*`.       / )|
                 /#%^#%*_ *%^%_  #  %$%\\    .J (__)
                 #&  . %%%#% ###%*.   *%\\.-'&# (__)
                 %*  J %.%#_|_#$.\\J* \\ %'#%*^  (__)
                 *#% J %$%%#|#$#$ J\\%   *   .--|(_)
                 |%  J\\ `%%#|#%%' / `.   _.'   |L|
                 |#$%||` %%%$### '|    `-'     |L|
                 (#%%||` #$#$%%% '|            |L|
                 | ##||  $%%.%$%  |            |L|
                 |$%^||   $%#$%   |            |L|
                """;


        String[] art1Lines = art1.split("\n");
        String[] art2Lines = art2.split("\n");

        for (int i = 0; i < Math.max(art1Lines.length, art2Lines.length); i++) {
            String line1 = i < art1Lines.length ? art1Lines[i] : "";
            String line2 = i < art2Lines.length ? art2Lines[i] : "";
            System.out.printf("%-80s %s%n", line1, line2);
        }

    }
    public static String titulo()
    {
        return
                """
                        _______  _____   ______  ______  _____  _______ _______ _______ __   _      ______  _______  _____  _     _ _______ _______
                         |______ |     | |_____/ |  ____ |     |    |       |    |______ | \\  |      |     \\ |______ |_____] |_____|    |    |______
                         |       |_____| |    \\_ |_____| |_____|    |       |    |______ |  \\_|      |_____/ |______ |       |     |    |    ______|
                        """;


    }
}

