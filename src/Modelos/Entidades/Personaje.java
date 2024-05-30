package Modelos.Entidades;

import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.Pocion;
import org.w3c.dom.ls.LSOutput;

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
        super(nombreP, saludP, 1);

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
    public ArrayList<String> getInventarioNombres() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Item item : inventario) {
            nombres.add(item.getNombre());
        }
        return nombres;
    }

    //Setters
    public void equiparArma(Arma NuevaArma) {
        this.arma = NuevaArma;
    }

    public void equiparArmadura(Armadura NuevaArmadura) {
        this.armadura = NuevaArmadura;
    }

    @SuppressWarnings("unused")
    public void setInventario(ArrayList<Item> inventarioP) {
        this.inventario = inventarioP;
    }

    //Metodos
    public void agregarItem(Item unItem) {
        inventario.add(unItem);
    }

    public void sacarItem(Item unItem) {
        inventario.remove(unItem);
    }

    public String equiparDesdeInventario(int eleccion) { //funcion que equipa/usa un item del inventario
        Item itemSeleccionado = null;

        if (eleccion > 0 && eleccion <= inventario.size()) {
            itemSeleccionado = inventario.get(eleccion - 1);
            sacarItem(itemSeleccionado); //saca del inventario el item seleccionado
            if (itemSeleccionado instanceof Pocion) {
                //((Pocion) ItemSeleccionado).usarPocion(); // No se hizo todavia || funcion de usar pocion
            } else if (itemSeleccionado instanceof Arma) {
                agregarItem(getArma()); //agrega el arma previamente equipada al inventario
                equiparArma((Arma) itemSeleccionado); // Equipa la nueva arma
            } else if (itemSeleccionado instanceof Armadura) {
                agregarItem(getArmadura()); //agrega la armadura previamente equipada al inventario
                equiparArmadura((Armadura) itemSeleccionado);//Equipa la nueva armadura
            }

        }
        return "" + itemSeleccionado;
    }


    //metodos de pelea

    public int ataqueJugador(Monstruo monstruo) { //el jugador usa su ataque basico

        int danioJugador = getArma().atacar();
        monstruo.recibirDanio(danioJugador);
        return danioJugador;

    }

    public int ataqueEspecialJugador(Monstruo monstruo) { //el jugador usa su ataque especial

        int danioJugador = getArma().ataqueEspecial();
        monstruo.recibirDanio(danioJugador);
        return danioJugador;

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

    @Override
    public String toString() {
        return "Personaje{" +
                "inventario=" + inventario +
                ", arma=" + arma +
                ", armadura=" + armadura +
                ", tipoDePersonaje=" + tipoDePersonaje +
                ", especialTEspera=" + especialTEspera +
                '}';
    }
}
