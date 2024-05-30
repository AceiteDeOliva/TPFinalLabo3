package Modelos.Entidades;

import Modelos.Interfaces.Habilidades;

public class Monstruo extends Entidad implements Habilidades {

    private int danio;//valor que le resta a la vida del personaje.
    private int velocidad;
    private int armadura;//Reduccion de danio.

    private TipoDeMonstruo tipoDeMonstruo;

    //Constructores
    public Monstruo () {
        super();
        this.danio = 0;
        this.velocidad = 0;
        this.armadura = 0;
    }
    
    public Monstruo(String nombreP, int saludP, int danioP, int velocidadP, int armaduraP) {
        super(nombreP, saludP);
        this.danio = danioP;
        this.velocidad = velocidadP;
        this.armadura = armaduraP;
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
    public void setDanio(int danioP) {
        this.danio = danioP;
    }

    public void setVelocidad(int velocidadP) {
        this.velocidad = velocidadP;
    }

    public void setArmadura(int armaduraP) {
        this.armadura = armaduraP;
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

    @Override
    public String toString() {
        return "Monstruo: " + super.toString()+
                "Danio=" + danio +
                ",Velocidad=" + velocidad +
                ",Armadura=" + armadura +
                ",TipoDeMonstruo: " + tipoDeMonstruo;
    }
}
