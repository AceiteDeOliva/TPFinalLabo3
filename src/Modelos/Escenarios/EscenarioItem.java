package Modelos.Escenarios;

import Modelos.Entidades.Personaje;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.pociones.Pocion;

import java.util.ArrayList;
import java.util.Random;

public class EscenarioItem extends Escenario {

    private ArrayList<Item> listaItems;

    public EscenarioItem(String nombre, int nivel, String descripcion) {
        super(nombre, nivel, descripcion);
        this.listaItems = new ArrayList<>();
    }

    public Item elegirItem ()
    {
        Item itemElegido = new Item();
        Random random = new Random();
        int indice = random.nextInt(listaItems.size());
        itemElegido = listaItems.get(indice);
        return itemElegido;
    }

    public String interactuar (Personaje personaje, Item item)
    {
        String interaccion = personaje.getNombre() + "ha encontrado un ";
        if(item instanceof Arma arma)
        {
            interaccion+= arma.toString()+"!";
        }else if(item instanceof Armadura armadura)
        {
            interaccion+= armadura.toString()+"!";
        }else if(item instanceof Pocion pocionDrop)
        {
            interaccion+= pocionDrop.toString()+"!";
        }
        return interaccion;
    }
}
