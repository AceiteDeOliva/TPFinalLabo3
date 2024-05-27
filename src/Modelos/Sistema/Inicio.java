package Modelos.Sistema;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;

import java.util.ArrayList;


public class Inicio  {
    public static void Inicio() {

        //Pasar objetos de los archivos de inventario, armas y armaduras a arrays
        Archivo  archivo = new Archivo();
        ArrayList<Arma> ListaArmas = new ArrayList<Arma>(archivo.leerArchivoAArray("Armas.dat"));
        ArrayList<Armadura> ListaArmaduras = new ArrayList<Armadura>(archivo.leerArchivoAArray("Armaduras.dat"));
        ArrayList<Item> ListaInventario = new ArrayList<Item>(archivo.leerArchivoAArray("Inventario.dat"));
        //Pasar info de partida
        ArrayList<Partida> ListaPartidas = new ArrayList<Partida>(archivo.leerArchivoAArray("Partidas.dat"));
        //Pasar info de monstruo
        //Pasar info de personaje
        //pasar info de escenarios
        archivo.grabarArchivo(ListaPartidas,"Partidas.dat");
    }




}




