package Modelos.Sistema;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;

import java.awt.*;
import java.util.ArrayList;


public class Inicio  {
    public static void Inicio() {

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




