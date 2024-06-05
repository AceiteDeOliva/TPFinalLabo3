package Modelos.Items;

public class Item  {
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

    @Override
    public String toString() {
        return "Item: " +
                "Nombre: '" + nombre + '\'' +
                ",Descripcion: '" + descripcion + '\'';
    }
}
