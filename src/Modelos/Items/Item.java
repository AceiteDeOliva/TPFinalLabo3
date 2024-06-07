package Modelos.Items;

import java.io.Serializable;

public class Item implements Serializable {
    private  String nombre;
    private  String descripcion;

    //Constructores
    public Item() {
        this.nombre = "";
        this.descripcion = "";
    }

    public Item (String nombreP, String descripcionP) {
        this.nombre = nombreP;
        this.descripcion = descripcionP;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombreP) {
        this.nombre = nombreP;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return
                "\n  Nombre: '" + nombre + '\'' +
                "\n  Descripcion: '" + descripcion + '\'';
    }
}
