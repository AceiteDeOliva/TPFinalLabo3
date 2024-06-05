package Modelos.Escenarios;
import Modelos.Entidades.Monstruo;
import Modelos.Entidades.Personaje;

import java.util.ArrayList;
import java.util.Random;

public class EscenarioMonstruo extends Escenario {
    private ArrayList<Monstruo> listaMonstruos;

    public EscenarioMonstruo() {
        super();
        this.listaMonstruos = null;
    }

    public EscenarioMonstruo(String nombre, int nivel, String descripcion, ArrayList<Monstruo> listaMonstruos) {
        super(nombre, nivel, descripcion);
        this.listaMonstruos = listaMonstruos;
    }

    public ArrayList<Monstruo> getListaMonstruos() {
        return listaMonstruos;
    }

    public void setListaMonstruos(ArrayList<Monstruo> listaMonstruos) {
        this.listaMonstruos = listaMonstruos;
    }

    public String interactuar (Personaje personaje, Monstruo monstruo)
    {
        return "¡" + personaje.getNombre() + " se encuentra con " + monstruo.toString() + "!";
    }

    public Monstruo elegirMounstruo ()
    {
        Monstruo monstruoElegido= new Monstruo();
        Random random = new Random();
        int indice = random.nextInt(listaMonstruos.size());
        monstruoElegido = listaMonstruos.get(indice);
        return monstruoElegido;
    }
}
