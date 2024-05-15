package Modelos.Entidades;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;

import java.util.HashSet;

public class Personaje extends Entidad {
    private final HashSet<Item> inventario = new HashSet<>();
    private Arma arma;
    private Armadura armadura;

    public Personaje(String nombre, int salud, Arma arma, Armadura armadura) {
        super(nombre, salud);
        this.arma = arma;
        this.armadura = armadura;
    }

    //Getters
    public Arma getArma() {
        return arma;
    }

    public Armadura getArmadura() {
        return armadura;
    }

    //Setters
    public void EquiparArma(Arma NuevaArma) {
        this.arma = NuevaArma;
    }

    public void equiparArmadura(Armadura NuevaArmadura){

        this.armadura = NuevaArmadura;

    }

}
