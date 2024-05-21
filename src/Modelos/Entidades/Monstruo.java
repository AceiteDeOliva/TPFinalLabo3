package Modelos.Entidades;

import Modelos.Interfaces.Habilidades;

public class Monstruo extends Entidad implements Habilidades {

    private int danio;
    private int velocidad;
    private int armadura;

    //constructor
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
    //metodos
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
        setSalud(getSalud() - danio);
    }

}
