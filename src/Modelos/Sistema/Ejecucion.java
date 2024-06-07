package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;
import Modelos.Entidades.TipoDePersonaje;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejecucion {
    private static final Scanner scan = new Scanner(System.in);
    private static Escenario escenarioActual;
    private static ArrayList<Partida> listaPartidas;

    public static void ejecucion() throws JSONException {

        // Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo archivo = new Archivo();

        // Pasar info de partida
        listaPartidas = archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre());

        // pasar info de escenarios con json
        HashSet<Escenario> escenarios = new HashSet<>();

        archivo.jsonAEscenarioMonstruo(escenarios);

        archivo.jsonAEscenarioItem(escenarios);


        menuPrincipal(escenarios);

        archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre());
    }


    public static void menuPrincipal(HashSet<Escenario> listaEscenarios) {

        int eleccion;
        int indice;
        Partida partida;
        do {

            limpiarConsola();
            System.out.println(Ejecucion.titulo());
            // Presentar las opciones del menú
            System.out.println("1.Jugar");
            System.out.println("2.Salir del juego");
            // Leer la elección del usuario
            try {
                eleccion = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Se esperaba un valor entero. " + e.getMessage());
                scan.next();
                eleccion = -1;
            }


            // Realizar acciones basadas en la elección del usuario usando un switch
            switch (eleccion) {
                case 1:
                    //CONTROLA LA ELECCION Y CREACION DE PARTIDAS
                    indice = menuPartida();
                    //SI NO QUIERE VOLVER AL MENU INICIAL
                    if (indice != -1) {
                        //Esta es la partida que cambiara mientras juega
                        partida = listaPartidas.get(indice);
                        partida.escenariosAHashMap(listaEscenarios);
                        manejarEncuentro(partida);
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
    public static int elegirPartida() {
        // Inicialización del contador para enumerar las partidas disponibles.
        int contador = 0;

        // Mostrar las partidas disponibles.
        System.out.println("Partidas:");
        System.out.println("0.Volver"); // Opción para volver atrás.
        // Iterar sobre la lista de partidas.
        for (int i = 0; i < listaPartidas.size(); i++) {
            // Verificar si la partida no está vacía.
            if (!listaPartidas.get(i).chequearExistencia(listaPartidas.get(i))) {
                contador++; // Incrementar el contador.
                // Mostrar el número de la partida y el nombre del jugador asociado.
                System.out.println(contador + ". " + listaPartidas.get(i).getJugador().getNombre());
            }
        }

        // Solicitar al usuario que ingrese el número de la partida.
        System.out.println("Ingrese el número de la partida:");
        int eleccion = 0;
        boolean entradaValida = false;

        // Bucle para esperar una entrada válida del usuario.
        while (!entradaValida) {
            try {
                // Intentar leer la entrada del usuario como un entero.
                eleccion = scan.nextInt();
                // Verificar si la elección está dentro del rango de opciones disponibles.
                if (eleccion >= 0 && eleccion <= contador) { // Includes 0
                    entradaValida = true; // Marcar la entrada como válida.
                } else {
                    // Mostrar un mensaje de error si la selección es inválida.
                    System.out.println("Selección invalida. Ingrese nuevamente:");
                }
            } catch (InputMismatchException e) {
                // Capturar excepción si la entrada no es un entero.
                System.out.println("Entrada no valida. Ingrese un numero:");
                scan.next(); // Limpiar el buffer del scanner.
            }
        }

        // Devolver la elección de partida seleccionada, ajustada para la indexación de listas basada en cero.
        return eleccion - 1;
    }

    public static int menuPartida() {
        // Agregar partidas vacías al inicio, si no hay ninguna
        Partida.agregarPartidasVacias(listaPartidas);
        int eleccion = -1; // Variable para almacenar la elección del usuario
        int indice = -1; // Variable para almacenar el índice de la partida seleccionada

        // Bucle que se ejecuta mientras el usuario no elija salir (opción 0)
        while (eleccion != 0) {
            // Mostrar las opciones del menú
            System.out.println("1. Nueva partida");

            // Mostrar la opción de continuar partida solo si hay partidas guardadas
            if (Partida.saberSiContienePartidas(listaPartidas)) {
                System.out.println("2. Continuar partida");
            }
            System.out.println("0. Salir");

            try {
                // Leer la elección del usuario
                eleccion = scan.nextInt();

                // Verificar si la elección es válida
                if ((eleccion != 1 && eleccion != 2 && eleccion != 0) || (eleccion == 2 && !Partida.saberSiContienePartidas(listaPartidas))) {
                    System.out.println("No es una opción correcta. Por favor, elige otra opción.");
                } else if (eleccion != 0) { // Si la elección no es salir
                    // Manejar la elección del usuario y obtener el índice de la partida seleccionada
                    indice = manejarPartidas(eleccion);
                    eleccion = 0; // Establecer eleccion en 0 para salir del bucle

                    // Si el índice es -1, significa que el usuario eligió volver atrás
                    if (indice == -1) {
                        eleccion = -1; // Reiniciar la variable eleccion para continuar el bucle
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scan.next(); // Limpiar el buffer del scanner
            }
        }

        // Devolver el índice de la partida seleccionada
        return indice;
    }

    public static int manejarPartidas(int eleccion) {
        int indice = -1; // Índice de la partida seleccionada
        Archivo archivo = new Archivo(); // Instancia de la clase Archivo para trabajar con archivos

        String nombre;
        int volver = 0; // Variable para controlar el flujo de ejecución en caso de volver atrás

        switch (eleccion) {
            case 1: // Crear nueva partida
                for (int i = 0; i < listaPartidas.size(); i++) {
                    if (listaPartidas.get(i).chequearExistencia(listaPartidas.get(i))) {
                        indice = i;
                    }
                }
                // Si no hay espacio para nuevas partidas, se le pide al usuario que elija una para eliminar
                if (indice == -1) {
                    System.out.println("No hay espacio para nuevas partidas");
                    System.out.println("Elija qué partida eliminar");
                    indice = elegirPartida();
                    if (indice != -1) {
                        // Si se elige una partida para eliminar, se realiza la eliminación
                        if (Partida.eliminarPartida(listaPartidas, listaPartidas.get(indice))) {
                            System.out.println("Partida eliminada exitosamente");
                            // Después de eliminar la partida, se agrega una partida vacía para mantener el tamaño de la lista
                            Partida.agregarPartidasVacias(listaPartidas);
                            indice = 2; // Se establece el índice en 2 para indicar que se creará una nueva partida
                        }
                    } else {
                        volver = indice; // Si el usuario elige volver atrás, se guarda la elección en la variable volver
                    }
                }
                // Si no se elige volver atrás, se procede a crear una nueva partida
                if (volver != -1) {
                    Partida partida;
                    partida = CrearPersonaje(); // Se crea un nuevo personaje para la partida
                    listaPartidas.set(indice, partida); // Se guarda la nueva partida en la lista de partidas
                    archivo.grabarArchivoPartidas(listaPartidas, NombreArchivos.Partidas.getNombre()); // Se guarda la lista de partidas en el archivo
                }
                break;

            case 2: // Continuar partida
                indice = elegirPartida(); // Se elige una partida para continuar
                listaPartidas.get(indice).construirHashMap(); // Se construye el HashMap relacionado con la partida seleccionada
                break;

            case 0: // Volver atrás
                indice = -1; // El usuario eligió volver atrás, por lo que se establece el índice en -1
                break;

            default: // Opción no válida
                throw new IllegalStateException("Unexpected value: " + eleccion); // Se lanza una excepción
        }

        return indice; // Se devuelve el índice de la partida seleccionada
    }

    public static Partida CrearPersonaje() {
        scan.nextLine(); // Limpiar el buffer
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
        Partida partida = new Partida(nuevoPersonaje);
        return partida;
    }


    public static void manejarEncuentro(Partida partida) {//funcion que maneja la eleccion de encuentros
        int respuesta = -1;

        while (partida.getJugador().estaVivo() && partida.getNivelActual() < 7) {
            limpiarConsola();//limpia la consola || en realidad solo imprime 50 lines vacias
            respuesta = elegirEncuentro(partida); //funcion en la que elegis un encuentro
            if (respuesta == 0) {// si la respuesta de elegir encuentro es 0 termina la funcion
                break;
            }
            if (escenarioActual instanceof EscenarioMonstruo) { // chequeo el tipo de instancia elegida
                encuentro(partida, (EscenarioMonstruo) escenarioActual); //se llama a la funcion de escenario correspondiente(polimorfismo)

            } else if (escenarioActual instanceof EscenarioItem) { //idem if the arriba
                encuentro(partida, (EscenarioItem) escenarioActual);
            }

            if (partida.guardarPartida(listaPartidas)) {//Se guarda la partida || imprime mensaje si lo logro
                System.out.println("Se guardo la partida");

            } else {

                System.out.println("Error: No se pudo guardar la partida");
            }
            ingresarParaContinuar();


        }
        if (partida.getNivelActual() == 7) {//chequea si ganaste el juego

            pantallaGanador();
            esperar(5);

        } else if (!partida.getJugador().estaVivo()) { //cuando el jugador muere se elimina su partida
            Partida.eliminarPartida(listaPartidas, partida);

        }
    }

    public static int elegirEncuentro(Partida partida) {

        Escenario escenario1 = partida.escenarioPosible(); // Recibir 2 escenarios random
        Escenario escenario2;
        do {
            escenario2 = partida.escenarioPosible();
        } while (escenario2.equals(escenario1) && partida.getNivelActual() != 6);// chequea que no te de las mismas opciones excepto en el 6 que es nivel unico

        int eleccion = -1;

        System.out.println("Estas en una bifurcacion en el camino.");
        System.out.println("A un lado ves:");
        System.out.println("-----------------------------------------------------------------------------------------");
        manejoLineas(escenario1.getDescripcion());//divide la descripcion del escenario en lineas para que sea mas facil de leer
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Al otro lado ves:");
        System.out.println("-----------------------------------------------------------------------------------------");
        manejoLineas(escenario2.getDescripcion());//divide la descripcion del escenario en lineas para que sea mas facil de leer
        System.out.println("-----------------------------------------------------------------------------------------");
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

        switch (eleccion) { //Se selecciona el escenario
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

        return eleccion; //devuelve la eleccion

    }

    public static void encuentro(Partida partida, EscenarioMonstruo escenario) {


        Monstruo monstruo = escenario.elegirMounstruo();
        while (partida.getJugador().estaVivo() && monstruo.estaVivo()) {

            limpiarConsola();
            System.out.println("\n" + escenario.getNombre());
            manejoLineas(escenario.getDescripcion()); //divide la descripcion del escenario en lineas para que sea mas facil de leer
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
                scan.nextLine(); // limpia el buffer
                System.out.println("Error: Ingrese un numero valido.");
                continue;
            }

            switch (eleccion) {

                case 1:
                    limpiarConsola();
                    if (partida.compararVelocidad(monstruo)) {//compara quien es mas rapido si el monstruo o el jugador
                        ataqueJugadorPrimero(partida, monstruo);
                    } else {
                        ataqueMonstruoPrimero(partida, monstruo);
                    }

                    chequeoBatalla(partida, monstruo); // Chequea el resultado de la batalla
                    ingresarParaContinuar();
                    break;

                case 2:
                    limpiarConsola();
                    if (partida.getJugador().getEspecialTEspera() <= 0) { //chequea si el ataque especial esta disponible
                        if (partida.compararVelocidad(monstruo)) { //compara quien es mas rapido si el monstruo o el jugador
                            ataqueEspecialJugadorPrimero(partida, monstruo);
                        } else {
                            ataqueEspecialMonstruoPrimero(partida, monstruo);
                        }

                        chequeoBatalla(partida, monstruo); // Chequea el resultado de la batalla
                    } else {
                        System.out.println("Ataque especial en espera." + "\n Quedan " + partida.getJugador().getEspecialTEspera() + " turnos.");

                    }
                    ingresarParaContinuar();
                    break;

                case 3:
                    limpiarConsola();
                    mostrarInventario(partida); // Muestra y maneja el inventario
                    ingresarParaContinuar();
                    break;

                case 4:
                    limpiarConsola();
                    System.out.println(partida.getJugador().getArma().toString()); // Muestra arma equipada
                    System.out.println(" ");
                    System.out.println(partida.getJugador().getArmadura().toString()); // Muestra armadura equipada
                    ingresarParaContinuar();

                    break;

                default:

                    System.out.println("Opcion invalida. Solo se permiten 1, 2, 3 o 4.");
                    ingresarParaContinuar();
                    break;

            }
        }

    }


    public static void encuentro(Partida partida, EscenarioItem escenario) { //encuentro de item
        System.out.println(escenario.getNombre());
        System.out.println("Exploras el lugar...");
        String itemEncontrado = partida.itemEncontrado(escenario); //devulve un string con los datos del item
        System.out.println("Econtraste:" + itemEncontrado + "!!!");
        ingresarParaContinuar();
    }

    public static void ataqueJugadorPrimero(Partida partida, Monstruo monstruo) { //Situacion en la que el jugador ataca primero

        System.out.println(partida.getJugador().getNombre() + " inflige " + partida.getJugador().ataqueJugador(monstruo) + " puntos de danio");//El danio que inflige el jugador

        if (monstruo.estaVivo()) {
            System.out.println(monstruo.getNombre() + " inflige " + monstruo.ataqueMonstruo(partida.getJugador()) + " puntos de danio");//el danio que inflige el monstruo
        }

    }


    public static void ataqueMonstruoPrimero(Partida partida, Monstruo monstruo) { //situacion en la que el monstruo ataca primero

        System.out.println(monstruo.getNombre() + " inflige " + monstruo.ataqueMonstruo(partida.getJugador()) + " puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println(partida.getJugador().getNombre() + " inflige " + partida.getJugador().ataqueJugador(monstruo) + " puntos de danio");//El danio que inflige el jugador

        }

    }

    public static void ataqueEspecialJugadorPrimero(Partida partida, Monstruo monstruo) { //situacion en la que el usa un ataque especial primero

        System.out.println(partida.getJugador().getNombre() + " inflige " + partida.getJugador().ataqueEspecialJugador(monstruo) + " puntos de danio");

        if (monstruo.estaVivo()) {
            System.out.println(monstruo.getNombre() + " inflige " + monstruo.ataqueMonstruo(partida.getJugador()) + " puntos de danio");
        }

    }

    public static void ataqueEspecialMonstruoPrimero(Partida partida, Monstruo monstruo) {// el monstruo es mas rapido y el jugador usa un ataque especial

        System.out.println("El monstruo inflige " + monstruo.ataqueMonstruo(partida.getJugador()) + " puntos de danio");//el danio que inflige el monstruo

        if (partida.getJugador().estaVivo()) {
            System.out.println(partida.getJugador().getNombre() + " inflige " + partida.getJugador().ataqueEspecialJugador(monstruo) + " puntos de danio");//El danio que inflige el jugador

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
                ingresarParaContinuar();
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

    public static void pantallaGanador() {
        String asciiArt =
                "                                                                                             \n" +
                        "                      __ __   ___ _____ _  _  _  __ __                                     \n" +
                        "                     |_ |_ |   | /   | | \\|_|| \\|_ (_  |                                      \n" +
                        "                     |  |__|___|_\\___|_|_/| ||_/|____) o                                      \n" +
                        " __                                                    _         _                           \n" +
                        "|_ __     |  _  _     _  _ __ |_  __ _  _    __  _    |_) __ _ _|_   __  _| _  _             \n" +
                        "|__| |    | (_|_>    _> (_)||||_) | (_|_>    |||_>    |   | (_) | |_|| |(_|(_|_>  /          \n" +
                        "                                                                            _                \n" +
                        " _ __  _  _ __ _|_ __ _  _ _|_ _     |  _     |     _     _| _     |  _    (_| |  _  __ o  _ \n" +
                        "(/_| |(_ (_)| | |_ | (_|_>  |_(/_    | (_|    | |_| /_   (_|(/_    | (_|   __| | (_) |  | (_| ";

        System.out.println(asciiArt);


        String creditos = "Juego desarrollado por: Jose Valentin Letamendia Muzio, Milagros Abril Rodriguez, Chiara Ciardi";

        System.out.println(asciiArt);
        System.out.println("\n" + creditos);


    }


    public static void esperar(int segundos) { //frena el programa por unos segundos
        try {
            Thread.sleep((long) segundos * 1000); // Convertimos 'segundos' a 'long' antes de multiplicar
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void limpiarConsola() { // limpia la consola para que sea mas claro
        for (int i = 0; i < 50; i++) {
            System.out.println(" ");

        }
    }

    public static void ingresarParaContinuar() { //frena la ejecucion hasta que el usuario desee continuar
        System.out.println("Ingrese algo para continuar.");
        scan.next();
        scan.nextLine();
    }

    public static void manejoLineas(String str) {
        int inicio = 0;
        int longitud = 100; // La longitud máxima de cada línea

        while (inicio < str.length()) {
            int end = Math.min(inicio + longitud, str.length());

            // Si no estamos al final del string, ajustar el 'end' al último espacio dentro del rango
            if (end < str.length()) {
                int lastSpace = str.lastIndexOf(' ', end);
                if (lastSpace > inicio) {
                    end = lastSpace;
                }
            }

            System.out.println(str.substring(inicio, end));
            // Ajustamos 'inicio' al siguiente carácter después del espacio
            inicio = end + 1;
        }
    }
}

