package Modelos.Entidades;

public abstract class Entidad {
    private String nombre;
    private int salud;
    private int especialTEspera;

    //Constructor
    public Entidad() {
        this.nombre = "";
        this.salud = 0;
        this.especialTEspera = 2;
    }

    public Entidad(String nombreP, int saludP,int especialTEsperaP) {
        this.nombre = nombreP;
        this.salud = saludP;
        this.especialTEspera = especialTEsperaP;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public int getEspecialTEspera() {
        return especialTEspera;
    }

    //Setters
    public void setNombre(String nombreP) {
        this.nombre = nombreP;
    }

    public void setSalud(int saludP) {
        this.salud = saludP;
    }

    public void setEspecialTEspera(int especialTEspera) {
        this.especialTEspera = especialTEspera;
    }
    //Metodos

    abstract boolean estaVivo();

    abstract void recibirDanio(int danio);

    @Override
    public String toString() {
        return "Entidad" +
                "Nombre: '" + nombre + '\'' +
                ",Salud: " + salud;
    }
}
