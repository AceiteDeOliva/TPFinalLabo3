package Modelos.Escenarios;
import Modelos.Entidades.Monstruo;

import java.util.HashSet;

public class EscenarioMonstruo extends Escenario {
    private HashSet<Monstruo> listaMonstruos;

    public EscenarioMonstruo(String nombre, int nivel, String descripcion, HashSet<Monstruo> listaMonstruos) {
        super(nombre, nivel, descripcion);
        this.listaMonstruos = listaMonstruos;
    }

    public HashSet<Monstruo> getListaMonstruos() {
        return listaMonstruos;
    }

    public void setListaMonstruos(HashSet<Monstruo> listaMonstruos) {
        this.listaMonstruos = listaMonstruos;
    }
}
