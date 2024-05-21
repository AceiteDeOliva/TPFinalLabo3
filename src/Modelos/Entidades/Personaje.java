package Modelos.Entidades;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;

import java.util.ArrayList;

public class Personaje extends Entidad {
    private final ArrayList<Item> inventario = new ArrayList<>();
    private Arma arma;
    private Armadura armadura;

    //Constructores
    public Personaje () {
        super();
        this.arma = new Arma();
        this.armadura = new Armadura();
    }

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

    //Metodos
    public void agregarItem (Item unItem) {
        inventario.add(unItem);
    }
    
    @Override
    public  boolean estaVivo() { //devuelve si wl jugador esta viva o no
        boolean vivo;
        vivo = getSalud() > 0;
        return vivo;
    }
    
    @Override
    public void recibirDanio(int danio){
        setSalud(getSalud() - danio);
    }

}
