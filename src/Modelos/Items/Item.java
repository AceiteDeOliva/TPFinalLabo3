package Modelos.Items;

public abstract class Item {
    private String nombre;

    //Constructores
    public Item () {
        this.nombre = "";
    }

    public Item(String nombre) {
        this.nombre = nombre;
    }
}
