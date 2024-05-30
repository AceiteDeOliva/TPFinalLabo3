package Modelos.Sistema;

import Modelos.Items.Item;

import java.util.ArrayList;

public class Ejecucion {
    public static void ejecucion() {


        Archivo  archivo = new Archivo();

        //Pasar info de partida
ArrayList<Partida> listaPartidas = new ArrayList<>()(archivo.leerArchivoPartidas(NombreArchivos.Partidas.getNombre()));


        //pasar info de escenarios con json


        archivo.grabarArchivoPartidas(listaPartidas,NombreArchivos.Partidas.getNombre());

    }
}
