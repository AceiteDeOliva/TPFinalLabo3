package Modelos.Entidades;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;

import java.util.ArrayList;

public class Personaje extends Entidad {
    private ArrayList<Item> inventario;
    private Arma arma;
    private Armadura armadura;
    private TipoDePersonaje tipoDePersonaje;


    //Constructores
    public Personaje() {
        super();
        this.arma = new Arma();
        this.armadura = new Armadura();
        this.inventario = new ArrayList<>();

    }

    public Personaje(String nombreP, int saludP, TipoDePersonaje tipoDePersonajeP, ArrayList<Item> inventarioP) {
        super(nombreP, saludP);

        this.tipoDePersonaje = tipoDePersonajeP;
        this.inventario = inventarioP;

        switch (tipoDePersonajeP) {
            case GUERRERO:
                this.arma = new Arma("Hacha de Batalla basica", "Un hacha de gran tamaño diseñada para combates cuerpo a cuerpo.", 5);
                this.armadura = new Armadura("Armadura de Guerrero basico", "Una armadura robusta diseñada para protección en combate.", 5, 5); // Example, replace with real attributes
                break;
            case MAGO:
                this.arma = new Arma("Bastón Arcano basico", "Un bastón tallado con runas mágicas, canaliza el poder del elemento.", 5);
                this.armadura = new Armadura("Túnica Mágica basico", "Una túnica ligera infundida con magia protectora.", 5, 5); // Example, replace with real attributes
                break;
            case ASESINO:
                this.arma = new Arma("Daga de Asesino basica", "Una daga afilada diseñada para ataques rápidos y letales.", 5); // Example, replace with real attributes
                this.armadura = new Armadura("Ropas Ligeras basico", "Ropas ligeras que permiten movimientos ágiles y sigilosos.", 5, 5); // Example, replace with real attributes
                break;

        }

    }

    //Getters
    @SuppressWarnings("unused")
    public Arma getArma() {
        return arma;
    }

    public Armadura getArmadura() {
        return armadura;
    }

    public ArrayList<Item> getInventario() {
        return inventario;
    }

    //Setters
    public void equiparArma(Arma NuevaArma) {
        this.arma = NuevaArma;
    }

    public void equiparArmadura(Armadura NuevaArmadura) {
        this.armadura = NuevaArmadura;
    }

    public void setInventario(ArrayList<Item> inventarioP) {
        this.inventario = inventarioP;
    }

    //Metodos
    public void agregarItem(Item unItem) {
        inventario.add(unItem);
    }

    @Override
    public boolean estaVivo() { //devuelve si el jugador esta viva o no
        boolean vivo;
        vivo = getSalud() > 0;
        return vivo;
    }

    @Override
    public void recibirDanio(int danio) {
        float reduccionDanio = (float) armadura.getDefensa() / (armadura.getDefensa() + 100);
        int danioEfectivo = (int) (danio * (1 - reduccionDanio));
        setSalud(getSalud() - danioEfectivo);
    }

}
