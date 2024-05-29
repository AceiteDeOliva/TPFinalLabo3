package Modelos.Items;
import java.io.Serializable;


public abstract class Item  {
    private final String nombre;
    private final String descripcion;

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


}
