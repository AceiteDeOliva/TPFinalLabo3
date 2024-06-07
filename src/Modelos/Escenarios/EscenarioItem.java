package Modelos.Escenarios;

import Modelos.Entidades.Personaje;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.pociones.Pocion;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class EscenarioItem extends Escenario {

    private ArrayList<Item> listaItems;

    public EscenarioItem(String nombreP, int nivelP, String descripcionP ,ArrayList<Item> listaItemsP) {
        super(nombreP, nivelP, descripcionP);
        this.listaItems = listaItemsP;
    }

    public ArrayList<Item> getListaItems() {
        return listaItems;
    }

    public Item elegirItem ()
    {
        Item itemElegido;
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
        }else if(item instanceof Pocion<?> pocionDrop)
        {
            interaccion+= pocionDrop.toString()+"!";
        }
        return interaccion;
    }

    @Override
    public boolean equals(Object o) {
        boolean resultado = false;
        if(o != null && o instanceof EscenarioItem){
            EscenarioItem escenarioItem = (EscenarioItem)o;
            super.equals(escenarioItem);
        }
        return resultado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), listaItems);
    }
}
