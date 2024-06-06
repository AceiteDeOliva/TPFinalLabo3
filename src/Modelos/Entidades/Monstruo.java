package Modelos.Entidades;

import Modelos.Interfaces.Habilidades;
import Modelos.Items.Item;

public class Monstruo extends Entidad implements Habilidades {

    private int danio;//valor que le resta a la vida del personaje.
    private int velocidad;
    private int armadura;//Reduccion de danio.
    private Item botin;
    private TipoDeMonstruo tipoDeMonstruo;

    //Constructores
    public Monstruo () {
        super();
        this.danio = 0;
        this.velocidad = 0;
        this.armadura = 0;
        this.botin = new Item();
    }
    
    public Monstruo(String nombreP, int saludP, int danioP, int velocidadP, int armaduraP, Item botinP) {
        super(nombreP, saludP,2);
        this.danio = danioP;
        this.velocidad = velocidadP;
        this.armadura = armaduraP;
        this.botin = botinP;
    }

    //Getters
    public int getDanio() {
        return danio;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getArmadura() {
        return armadura;
    }

    public Item getBotin() {
        return botin;
    }

    //Setters
    public void setDanio(int danioP) {
        this.danio = danioP;
    }

    public void setVelocidad(int velocidadP) {
        this.velocidad = velocidadP;
    }

    public void setArmadura(int armaduraP) {
        this.armadura = armaduraP;
    }

    public void setBotin(Item botin) {
        this.botin = botin;
    }

    //Metodos
    @Override
    public int atacar() {
        return danio;
    }

    @Override
    public int ataqueEspecial() {
        return danio * 2;
    }

    //metodos de pelea
    public int ataqueMonstruo(Personaje jugador) { //el monstruo usa su ataque basico
        int danio = -1;
        if (estaVivo()) {
            if (getEspecialTEspera() <= 0) { // chequea si puede usar el especial
                danio = ataqueEspecial();
                setEspecialTEspera(2);
            } else {
                danio = atacar();
                setEspecialTEspera(getEspecialTEspera() - 1);
            }
            jugador.recibirDanio(danio);
        }
        return danio;

    }
    @Override
    public  boolean estaVivo() { //devuelve si el monstruo esta vivo o no
        boolean vivo;
        vivo = getSalud() > 0;
        return vivo;
    }

    @Override
    public void recibirDanio(int danio){
        float reduccionDanio = (float) armadura / (armadura + 100);
        int danioEfectivo = (int) (danio * (1 - reduccionDanio));
        setSalud(getSalud() - danioEfectivo);
    }

    @Override
    public String toString() {
        return "Monstruo: " + super.toString() +
                "Danio=" + danio +
                ",Velocidad=" + velocidad +
                ",Armadura=" + armadura +
                ",TipoDeMonstruo: " + tipoDeMonstruo;
    }
}
