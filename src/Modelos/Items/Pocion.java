package Modelos.Items;

public class Pocion extends Item{
    private int salud;
    private int velocidad;

    public Pocion() {
        super();
        this.salud = 0;
        this.velocidad = 0;
    }

    public Pocion(String nombreP, String descripcionP, int saludP, int velocidadP) {
        super(nombreP, descripcionP);
        this.salud = saludP;
        this.velocidad = velocidadP;
    }

    public int getSalud() {
        return salud;
    }

    public int getVelocidad() {
        return velocidad;
    }

    @Override
    public String toString() {
        return "Pocion: " + super.toString()+
                "Salud=" + salud +
                ",Velocidad=" + velocidad;
    }

    //metodos para aumentarle la velocidad y la salud al personaje
}
