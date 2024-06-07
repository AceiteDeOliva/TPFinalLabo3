package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Entidades.TipoDePersonaje;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejecucion {
    private static final Scanner scan = new Scanner(System.in);
    private static Escenario escenarioActual;

    public static void ejecucion() throws JSONException {

        // Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        // Pasar info de partida
        ArrayList<Partida> listaPartidas = archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre());

        // pasar info de escenarios con json
        HashSet<Escenario> escenarioMonstruos = new HashSet<>();

        //archivo.jsonAEscenarioMonstruo(escenarioMonstruos);

        archivo.jsonAEscenarioItem(escenarioMonstruos);
        listaPartidas.add(new Partida(new Personaje("mili", TipoDePersonaje.ASESINO)));


        menuPrincipal(listaPartidas, escenarioMonstruos);

        archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre());
    }


    public static void menuPrincipal(ArrayList<Partida> listaPartidas, HashSet<Escenario> listaEscenarios) {

        int eleccion;
        int indice;
        Partida partida;
        do {

            for (int i = 0; i < 50; ++i) System.out.println();
            System.out.println(Ejecucion.titulo());
            // Presentar las opciones del menú
            System.out.println("1.Jugar");
            System.out.println("2.Salir del juego");
            // Leer la elección del usuario
            eleccion = scan.nextInt();


            // Realizar acciones basadas en la elección del usuario usando un switch
            switch (eleccion) {
                case 1:
                    //CONTROLA LA ELECCION Y CREACION DE PARTIDAS
                    indice = menuPartida(listaPartidas);
                    //SI NO QUIERE VOLVER AL MENU INICIAL
                    if (indice != -1) {
                        //Esta es la partida que cambiara mientras juega
                        partida = listaPartidas.get(indice);
                        partida.escenariosAHashMap(listaEscenarios);
                        manejarEncuentro(partida, listaPartidas);
                    }

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
        } while (eleccion != 2);

    }

    //Elegir dentro de las partidas existentes en el caso de no elegir una devuelve -1
    public static int elegirPartida(ArrayList<Partida> listaPartidas) {
        int contador = 0;

        System.out.println("Partidas:");
        System.out.println("0.Volver");
        for (int i = 0; i < listaPartidas.size(); i++) {
            if (!listaPartidas.get(i).chequearExistencia(listaPartidas.get(i))) {
                System.out.println((i + 1) + ". " + listaPartidas.get(i).getJugador().getNombre());
                contador++;
            }
        }

        System.out.println("Ingrese el número de la partida:");
        int eleccion = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                eleccion = scan.nextInt();
                if (eleccion >= 0 && eleccion <= contador) {
                    entradaValida = true;
                } else {
                    System.out.println("Selección invalida. Ingrese nuevamente:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Ingrese un numero:");
                scan.next(); // Limpiamos el buffer del scanner
            }
        }

        return eleccion - 1;

    }


    public static int menuPartida(ArrayList<Partida> listaPartidas) {
        Partida.agregarPartidasVacias(listaPartidas);
        int eleccion = -1;
        int indice = -1;

        while (eleccion != 0) {
            System.out.println("1. Nueva partida");

            if (Partida.saberSiContienePartidas(listaPartidas)) {
                System.out.println("2. Continuar partida");
            }
            System.out.println("0. Salir");

            try {
                eleccion = scan.nextInt();


                if ((eleccion != 1 && eleccion != 2 && eleccion != 0) || (eleccion == 2 && !Partida.saberSiContienePartidas(listaPartidas))) {
                    System.out.println("No es una opción correcta. Por favor, elige otra opción.");
                } else if (eleccion != 0) {
                    indice = manejarPartidas(eleccion, listaPartidas);
                    eleccion = 0;
                    // Si el índice es 0, significa que el usuario eligió volver atrás
                    if (indice == -1) {
                        eleccion = -1; // Reiniciar la variable eleccion para continuar el bucle
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scan.next();
            }
        }


        return indice;
    }

    public static int manejarPartidas(int eleccion, ArrayList<Partida> listaPartidas) {
        int indice = -1;


        String nombre;
        int volver = 0;
        switch (eleccion) {
            case 1:
                for (int i = 0; i < listaPartidas.size(); i++) {
                    if (listaPartidas.get(i).chequearExistencia(listaPartidas.get(i))) {
                        indice = i;
                    }
                }
                if (indice == -1) {
                    System.out.println("No hay espacio para nuevas partidas");
                    System.out.println("Elija que partida eliminar");
                    indice = elegirPartida(listaPartidas);
                    if (indice != -1) {
                        if (Partida.eliminarPartida(listaPartidas, listaPartidas.get(indice))) {
                            System.out.println("Partida eliminada exitosamente");
                            Partida.agregarPartidasVacias(listaPartidas);
                            indice = 2;
                        }
                    } else {
                        volver = indice;
                    }
                }
                if (volver != -1) {
                    Partida partida = listaPartidas.get(indice);
                    partida = CrearPersonaje();
                    listaPartidas.add(indice,partida);
                }

                break;

            case 2:
                indice = elegirPartida(listaPartidas);

                break;

            case 0:
                // El usuario eligió volver atrás
                indice = -1;
                break;
        }

        return indice;
    }

    public static Partida CrearPersonaje() {

        System.out.println("Ingrese un nombre para su personaje:");
        String nombrePersonaje = scan.nextLine();

        // Solicitar al usuario que elija un tipo de personaje mediante un número
        System.out.println("Seleccione el tipo de su personaje:");
        System.out.println("1. GUERRERO");
        System.out.println("2. MAGO");
        System.out.println("3. ASESINO");
        int opcion = scan.nextInt();
        scan.nextLine(); // Limpiar el buffer

        // Validar la opción ingresada por el usuario y crear el personaje
        TipoDePersonaje clasePersonaje;
        switch (opcion) {
            case 1:
                clasePersonaje = TipoDePersonaje.GUERRERO;
                break;
            case 2:
                clasePersonaje = TipoDePersonaje.MAGO;
                break;
            case 3:
                clasePersonaje = TipoDePersonaje.ASESINO;
                break;
            default:
                System.out.println("Opción no válida. Se utilizará la clase predeterminada.");
                clasePersonaje = TipoDePersonaje.GUERRERO; // Clase predeterminada en caso de error
                break;
        }

        // Crear un nuevo personaje con el nombre y la clase elegida por el usuario y asignarlo a la partida
        Personaje nuevoPersonaje = new Personaje(nombrePersonaje, clasePersonaje);
        Partida partida = new Partida(nuevoPersonaje); // Asumiendo que partida es una variable accesible aquí
        return partida;
    }


    public static void manejarEncuentro(Partida partida, ArrayList<Partida> listaPartidas) {//funcion que maneja la eleccion de encuentros
        int respuesta = -1;
        while (partida.getJugador().estaVivo() && respuesta != 0) {
            try {
                LimpiarConsola.limpiarConsola();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            respuesta = elegirEncuentro(partida); //funcion en la que elegis un encuentro
            if(respuesta == 0){
                break;
            }
            if (escenarioActual instanceof EscenarioMonstruo) { // cheque0 el tipo de instancia elegida
                encuentro(partida, (EscenarioMonstruo) escenarioActual); //se llama a la funcion de escenario correspondiente(polimorfismo)

            } else if (escenarioActual instanceof EscenarioItem) { //idem if the arriba
                encuentro(partida, (EscenarioItem) escenarioActual);
            }
            partida.guardarPartida();//Se guarda la partida

        }
    }

    public static int elegirEncuentro(Partida partida) {

        Escenario escenario1 = partida.escenarioPosible(); // Recibir 2 escenarios random
        Escenario escenario2 = partida.escenarioPosible();


        int eleccion = -1;

        System.out.println("Estas en una bifurcacion en el camino.");
        System.out.println("A un lado ves:");
        System.out.println(escenario1.getDescripcion());
        System.out.println("Al otro lado ves:");
        System.out.println(escenario2.getDescripcion());
        System.out.println("Que camino deseas tomar?");
        System.out.println("1. Primer camino.");
        System.out.println("2. Segundo camino.");
        System.out.println("0. volver");

        // Validacion
        try {
            eleccion = scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine();
            System.out.println("Error: Ingrese un numero valido.");

        }

        switch (eleccion) {
            case 1:
                escenarioActual = escenario1;
                break;
            case 2:
                escenarioActual = escenario2;
                break;
            case 0:
                System.out.println("Volves para continuar otro dia.");
                break;
            default:
                System.out.println("Error: Opcion invalida.");
                break;
        }
        return eleccion;


    }

    public static void encuentro(Partida partida, EscenarioMonstruo escenario) {


        Monstruo monstruo = escenario.elegirMounstruo();
        while (partida.getJugador().estaVivo() && monstruo.estaVivo()) {

            System.out.println("\n" + escenario.getNombre());
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
                eleccion = scan.nextInt();
            } catch (InputMismatchException e) {
                scan.nextLine(); // Clear the buffer after the exception
                System.out.println("Error: Ingrese un numero valido.");
                continue;
            }

            switch (eleccion) {

                case 1:
                    if (partida.compararVelocidad(monstruo)) {
                        ataqueJugadorPrimero(partida, monstruo);
                    } else {
                        ataqueMonstruoPrimero(partida, monstruo);
                    }

                    chequeoBatalla(partida, monstruo); // Check battle outcome
                    break;

                case 2:
                    if (partida.compararVelocidad(monstruo)) {
                        ataqueEspecialJugadorPrimero(partida, monstruo);
                    } else {
                        ataqueEspecialMonstruoPrimero(partida, monstruo);
                    }

                    chequeoBatalla(partida, monstruo); // Check battle outcome
                    break;

                case 3:
                    mostrarInventario(partida); // Display and manage inventory
                    break;

                case 4:
                    System.out.println(partida.getJugador().getArma().toString()); // Muestra arma equipada
                    System.out.println(" ");
                    System.out.println(partida.getJugador().getArmadura().toString()); // Muestra armadura equipada
                    break;

                default:

                    System.out.println("Opcion invalida. Solo se permiten 1, 2, 3 o 4.");
                    break;

            }
        }

    }


    public static void encuentro(Partida partida, EscenarioItem escenario) { //encuentro de item
        System.out.println(escenario.getNombre());
        System.out.println("Exploras el lugar...");
        String itemEncontrado = partida.itemEncontrado(escenario);
        System.out.println("Econtraste:" + itemEncontrado + "!!!");
    }

    public static void ataqueJugadorPrimero(Partida partida, Monstruo monstruo) { //Situacion en la que el jugador ataca primero

        System.out.println("El jugador inflige" + partida.getJugador().ataqueJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        if (monstruo.estaVivo()) {
            System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo
        }

    }


    public static void ataqueMonstruoPrimero(Partida partida, Monstruo monstruo) { //situacion en la que el monstruo ataca primero

        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println("El jugador inflige" + partida.getJugador().ataqueJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        }

    }

    public static void ataqueEspecialJugadorPrimero(Partida partida, Monstruo monstruo) { //situacion en la que el usa un ataque especial primero

        System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");

        if (monstruo.estaVivo()) {
            System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");
        }

    }

    public static void ataqueEspecialMonstruoPrimero(Partida partida, Monstruo monstruo) {// el monstruo es mas rapido y el jugador usa un ataque especial

        System.out.println("El monstruo inflige" + monstruo.ataqueMonstruo(partida.getJugador()) + "puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println("El jugador inflige" + partida.getJugador().ataqueEspecialJugador(monstruo) + "puntos de danio");//El danio que inflige el jugador

        }

    }

    //Funciones de print
    public static void chequeoBatalla(Partida partida, Monstruo monstruo) {// chequea y printea el resultado de la batalla
        int resultado = partida.chequeoFinDeAtaque(monstruo);//El resultado de la batalla || 1 si gano || 0 si continua || -1 si perdio
        switch (resultado) {
            case 1:
                System.out.println("Ha ganado la batalla.");
                System.out.println("El botin encontrado es: " + monstruo.tirarBotin().toString());

                break;

            case 0:
                System.out.println("La batalla continua"); //Esto se puede dejar vacio tambien

                break;

            case -1:
                System.out.println("Ha perdido la batalla");
                break;

            default:
                throw new RuntimeException("Resultado de batalla invalido: " + resultado);


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

    public static void mostrarInventario(Partida partida) { //muestra el inventario y recibe la seleccion del jugador
        ArrayList<String> inventarioNombres = partida.getJugador().getInventarioNombres();

        // Display "Volver" option and inventory items
        System.out.println("0. Volver");
        for (int i = 0; i < inventarioNombres.size(); i++) {
            System.out.println((i + 1) + ". " + inventarioNombres.get(i) + ")");
        }


        int eleccion = -1;
        boolean eleccionValida = false;

        do {
            System.out.print("Elige un Item: ");

            try {
                eleccion = scan.nextInt();
                if (eleccion >= 0 && eleccion <= inventarioNombres.size()) { //chequea si la eleccion esta dentro de los rangos validos
                    eleccionValida = true;
                } else {
                    System.out.println("Error: Opcion invalida. Intenta nuevamente.");
                }
            } catch (InputMismatchException e) {
                scan.nextLine(); // Clear the buffer after the exception
                System.out.println("Error: Ingresa un numero valido.");
            }
        } while (!eleccionValida);

        seleccionItem(partida, eleccion);

    }


    public static void figuras() {
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
        System.out.println(Ejecucion.titulo());
        for (int i = 0; i < Math.max(art1Lines.length, art2Lines.length); i++) {
            String line1 = i < art1Lines.length ? art1Lines[i] : "";
            String line2 = i < art2Lines.length ? art2Lines[i] : "";
            System.out.printf("%-80s %s%n", line1, line2);
        }


    }

    public static void pantallaDeCarga() {
        String patron =
                """
                                      ______  ______   ______   ______   ______   ______   _____    ______ \s
                                      | |     | |  | | | |  | \\ | | ____ | |  | | | |  \\ \\ | | \\ \\  / |  | \\\s
                                      | |     | |__| | | |__| | | |  | | | |__| | | |  | | | |  | | | |  | |\s
                                      |_|____ |_|  |_| |_|  \\_\\ |_|__|_| |_|  |_| |_|  |_| |_|_/_/  \\_|__|_/ \
                        """;
        System.out.println(patron);
    }

    public static String titulo() {
        return
                """
                        _______  _____   ______  ______  _____  _______ _______ _______ __   _      ______  _______  _____  _     _ _______ _______
                         |______ |     | |_____/ |  ____ |     |    |       |    |______ | \\  |      |     \\ |______ |_____] |_____|    |    |______
                         |       |_____| |    \\_ |_____| |_____|    |       |    |______ |  \\_|      |_____/ |______ |       |     |    |    ______|
                        """;


    }

    public static void esperar(int segundos) {
        try {
            Thread.sleep((long) segundos * 1000); // Convertimos 'segundos' a 'long' antes de multiplicar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

