package Modelos.Entidades;

import Modelos.Interfaces.Habilidades;

public class Monstruo extends Entidad implements Habilidades {

    private int danio;//valor que le resta a la vida del personaje.
    private int velocidad;
    private int armadura;//Reduccion de danio.

    //Constructores
    public Monstruo () {
        super();
        this.danio = 0;
        this.velocidad = 0;
        this.armadura = 0;
    }
    
    public Monstruo(String nombre, int salud, int danio, int velocidad, int armadura) {
        super(nombre, salud);
        this.danio = danio;
        this.velocidad = velocidad;
        this.armadura = armadura;
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

    //Setters
    public void setDanio(int danio) {
        this.danio = danio;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
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

    @Override
    public  boolean estaVivo() { //devuelve si el monstruo esta viva o no
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

}
