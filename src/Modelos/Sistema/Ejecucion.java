package Modelos.Sistema;

import Modelos.Items.Item;

import java.util.ArrayList;

public class Ejecucion {
    public static void Ejecucion() {

        //Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo  archivo = new Archivo();

        ArrayList<Item> ListaItems = new ArrayList<>(archivo.leerArchivoItems(NombreArchivos.Items.getNombre()));
        //Pasar info de partida

        //Pasar info de monstruo
        //Pasar info de personaje
        //pasar info de escenarios
        archivo.grabarArchivo(ListaItems,NombreArchivos.Items.getNombre());
    }
}
